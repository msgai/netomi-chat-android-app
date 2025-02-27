package com.netomi.sampleapplication.ui.activity

import android.Manifest
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
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
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.navigation.NavigationView
import com.google.firebase.messaging.FirebaseMessaging
import com.netomi.chat.ui.init.NCWChatSdk
import com.netomi.sampleapplication.R
import com.netomi.sampleapplication.constant.SharePreferenceConstant
import com.netomi.sampleapplication.model.Bot
import com.netomi.sampleapplication.model.BotListingResponse
import com.netomi.sampleapplication.ui.fragment.ChangeAiAgentFragment
import com.netomi.sampleapplication.ui.fragment.HomeFragment
import com.netomi.sampleapplication.utils.AppSharedPreferences
import com.netomi.sampleapplication.utils.NetworkUtils
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
    private val notificationPermission = 100
    private var permissions = arrayOf(Manifest.permission.POST_NOTIFICATIONS)
    private var name:String=""

    private lateinit var usernameProfile: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        botList = mutableListOf()
        checkPermission()
        initView()

        // Get the data from intent
        val botRefId = intent.getStringExtra("botRefId")
        val env = intent.getStringExtra("env")

        Log.d("FCM_DATA", "BotRefId: $botRefId, Env: $env")
        val email = preferences.getString(SharePreferenceConstant.EMAIL)
        if (NetworkUtils.isNetworkAvailable(this))
        onboardingViewModel.getBotListing(email)
        else
            Toast.makeText(this,
                getString(R.string.please_check_your_network_and_try_again), Toast.LENGTH_SHORT).show()

        observeChatMessages()
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HomeFragment())
                .commit()

            updateUIForFragment(HomeFragment())
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
        usernameProfile.text = name


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

        supportFragmentManager.addOnBackStackChangedListener {
            val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
            currentFragment?.let {
                updateUIForFragment(it)
            }
        }

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("FCM Token", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result
            NCWChatSdk.setDeviceToken(token)

            // Log and toast
            //val msg = getString("Token", token)
            Log.d("FCM Token", token)
            //Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
        })
    }

    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            // Requesting the permission
        } else {
            ActivityCompat.requestPermissions(
                this,
                permissions,
                notificationPermission
            )
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
                try {
                    onboardingViewModel.updateBotList(response.bots[0])
                    botList.addAll(response.bots)
                    preferences.saveSelectedBot(botList[0])
                    preferences.put(SharePreferenceConstant.BOT_RESPONSE, response)
                    showLoader(false)
                }catch (e:Exception){
                    e.printStackTrace()
                    showLoader(false)
                }
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
        val headerView = navView.getHeaderView(0)
        usernameProfile= headerView.findViewById(R.id.userName)
        window.statusBarColor = ContextCompat.getColor(this, R.color.status_bar)
    }

    fun loadFragment(fragment: Fragment) {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
        if (currentFragment != null && currentFragment::class == fragment::class) {
            return
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()

        updateUIForFragment(fragment)
    }


    private fun updateUIForFragment(fragmentClass: Fragment) {
        when (fragmentClass) {
            is HomeFragment -> {
                llParent.visibility = View.VISIBLE
                tvTitle.visibility = View.GONE
                usernameTextView.text = name
                usernameProfile.text = name
                tvWelcome.text = "Welcome"

            }
            is ChangeAiAgentFragment  -> {
                llParent.visibility = View.GONE
                tvTitle.visibility = View.VISIBLE
            }
            else -> {
                tvTitle.visibility = View.VISIBLE
            }
        }
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




}
