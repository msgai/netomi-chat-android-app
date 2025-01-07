package com.netomi.sampleapplication.ui.activity

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.netomi.sampleapplication.R
import com.netomi.sampleapplication.constant.SharePreferenceConstant
import com.netomi.sampleapplication.model.Bot
import com.netomi.sampleapplication.model.BotListingResponse
import com.netomi.sampleapplication.ui.fragment.ChangeAiAgentFragment
import com.netomi.sampleapplication.ui.fragment.HomeFragment
import com.netomi.sampleapplication.utils.AppSharedPreferences
import com.netomi.sampleapplication.utils.State
import com.netomi.sampleapplication.utils.customView.DialogUtils
import com.netomi.sampleapplication.viewmodel.OnboardingViewModel

class HomeActivity : AppCompatActivity(), DialogUtils.DialogListener {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    private lateinit var menuIcon: ImageView
    private lateinit var usernameTextView: TextView
    private lateinit var llParent: LinearLayout
    private lateinit var tvTitle: TextView
    private lateinit var tvWelcome: TextView
    private lateinit var logout: AppCompatTextView
    private lateinit var preferences: AppSharedPreferences
    private val onboardingViewModel: OnboardingViewModel by viewModels()
    private lateinit var botList: MutableList<Bot>
    private lateinit var progressOverlay: FrameLayout
    private var name:String=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        botList = mutableListOf()

        initView()
        val email = preferences.getString(SharePreferenceConstant.EMAIL)
        if (isNetworkAvailable())
        onboardingViewModel.getBotListing(email)
        else
            Toast.makeText(this,
                getString(R.string.please_check_your_network_and_try_again), Toast.LENGTH_SHORT).show()

        observeChatMessages()
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HomeFragment())
                .commit()
            llParent.visibility = View.VISIBLE
            tvTitle.visibility = View.GONE
            usernameTextView.text = name
            tvWelcome.text = "Welcome"
        }


        menuIcon.setOnClickListener {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                drawerLayout.openDrawer(GravityCompat.START)
            }
        }

        name = preferences.getString(SharePreferenceConstant.NAME)
        usernameTextView.text = name


        // Handle logout action
        logout.setOnClickListener {
            drawerLayout.closeDrawers() // Close all open drawers
            //  logoutFun()
            preferences.clearSharedPreference()
            // Navigate to LoginActivity and clear activity stack
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }



        navView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.itemHome -> {
                    loadFragment(HomeFragment())
                  // Close all open drawers

                }

                R.id.itemAgent -> {
                    loadFragment(ChangeAiAgentFragment())
                }
            }
            // Close the drawer after an item is selected
            drawerLayout.closeDrawers()
            true
        }
    }


    private fun observeChatMessages() {
        onboardingViewModel.botListing.observe(this) { messages ->
            handleApiCallback(messages as State<Any>)
        }
    }

    private fun handleApiCallback(response: State<Any>) {
        when (response) {
            is State.Loading -> {
                showLoader(true)
            }

            is State.Success -> {
                val response= response.data as BotListingResponse
                onboardingViewModel.updateBotList(response.bots[0])
                botList.addAll(response.bots)
                preferences.saveSelectedBot(botList[0])
                preferences.put(SharePreferenceConstant.BOT_RESPONSE, response)
                showLoader(false)
            }

            is State.Error -> {
                showLoader(false)
                Toast.makeText(this,response.message,Toast.LENGTH_SHORT).show()
            }

            else -> {
                showLoader(false)
                //Toast.makeText(this,response.message,Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showLoader(show: Boolean) {
        progressOverlay.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun initView() {
        preferences = AppSharedPreferences(this)
        drawerLayout = findViewById(R.id.drawerLayout)
        navView = findViewById(R.id.navView)
        menuIcon = findViewById(R.id.toolbar_menu_icon)
        usernameTextView = findViewById(R.id.tv_user)
        logout = findViewById(R.id.logout)
        llParent = findViewById(R.id.ll_parent)
        tvTitle = findViewById(R.id.tv_ai)
        tvWelcome = findViewById(R.id.tv_welcome)
        progressOverlay = findViewById(R.id.progress_overlay)
        window.statusBarColor = ContextCompat.getColor(this, R.color.status_bar)
    }

    fun loadFragment(fragment: Fragment) {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
        if (currentFragment != null && currentFragment::class == fragment::class) {
            return
        }
        when (fragment) {
            is HomeFragment -> {
                llParent.visibility = View.VISIBLE
                tvTitle.visibility = View.GONE
                usernameTextView.text = name
                tvWelcome.text = "Welcome"
                drawerLayout.closeDrawers()
            }
            is ChangeAiAgentFragment -> {
                llParent.visibility = View.GONE
                tvTitle.visibility = View.VISIBLE
                drawerLayout.closeDrawers() //
            }
            else ->   tvTitle.visibility = View.VISIBLE
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    /**
     * Function to handle logout with a confirmation dialog
     */
    private fun logoutFun() {
        val dialogUtils = DialogUtils(this)
        dialogUtils.showCustomDialog(
            Dialog(this), DialogUtils.DialogModel(
                from = 1,
                title = getString(R.string.logout),
                desc = getString(R.string.logout_subtitle),
                btnPositive = getString(R.string.logout),
                btnNegative = getString(R.string.cancel),
                listener = this
            )
        )
    }


    /**
     * Dialog listener for cancel action
     */
    override fun onDialogCancel(action: Int) {
        // Handle dialog cancel action if needed
    }

    /**
     * Dialog listener for positive action (logout)
     */
    override fun onDialogAction(action: Int) {
        // Clear user data on logout
        preferences.clearSharedPreference()

        // Navigate to LoginActivity and clear activity stack
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finishAffinity()
    }


    fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
        return networkCapabilities != null &&
                (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                        networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET))
    }

}
