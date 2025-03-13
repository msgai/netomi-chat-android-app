package com.netomi.sampleapplication.ui.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
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
import com.netomi.sampleapplication.R
import com.netomi.sampleapplication.constant.SharePreferenceConstant
import com.netomi.sampleapplication.ui.fragment.HomeFragment
import com.netomi.sampleapplication.utils.AppSharedPreferences
import com.netomi.sampleapplication.utils.customView.DialogUtils

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
    private lateinit var progressOverlay: FrameLayout
    private val notificationPermission = 100
    private var permissions = arrayOf(Manifest.permission.POST_NOTIFICATIONS)
    private var name:String=""

    private lateinit var usernameProfile: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        checkPermission()
        initView()

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
//        val headerView = navView.getHeaderView(0)
       usernameProfile= findViewById(R.id.userName)
        window.statusBarColor = ContextCompat.getColor(this, R.color.status_bar)

        val tvHome=findViewById<TextView>(R.id.tvHome)

        tvHome.setOnClickListener {
            loadFragment(HomeFragment())
            drawerLayout.closeDrawers()
        }

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
            else -> {
                tvTitle.visibility = View.VISIBLE
            }
        }
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
