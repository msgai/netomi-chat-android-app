package com.netomi.sampleapplication.ui

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.netomi.sampleapplication.R
import com.netomi.sampleapplication.constant.SharePreferenceConstant
import com.netomi.sampleapplication.utils.AppSharedPreferences
import com.netomi.sampleapplication.utils.customView.DialogUtils

class HomeActivity : AppCompatActivity(), DialogUtils.DialogListener {

    // Variables for UI components
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    private lateinit var ivMenu: ImageView
    private lateinit var logout: AppCompatTextView
    private lateinit var tvHeaderOne: AppCompatTextView
    private lateinit var userName: AppCompatTextView

    // Shared Preferences for storing user data
    private lateinit var preferences: AppSharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home) // Set the layout for HomeActivity

        // Initialize views and preferences
        initializeViews()

        // Set header name and username based on stored preferences
        val name = preferences.getString(SharePreferenceConstant.NAME)
        tvHeaderOne.text = name
        userName.text = name

        // Open drawer on menu icon click
        ivMenu.setOnClickListener {
            drawerLayout.openDrawer(navView)
        }

        // Handle logout action
        logout.setOnClickListener {
            drawerLayout.closeDrawers() // Close all open drawers
            logoutFun()
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
     * Initialize and bind all views
     */
    private fun initializeViews() {
        // Initialize shared preferences
        preferences = AppSharedPreferences(this)

        // Bind main layout views
        drawerLayout = findViewById(R.id.drawerLayout)
        navView = findViewById(R.id.navView)
        ivMenu = findViewById(R.id.ivMenu)
        logout = findViewById(R.id.logout)
        tvHeaderOne = findViewById(R.id.tvHeaderOne)

        // Bind navigation header views
        val headerView = navView.getHeaderView(0) // Access the first header view of the NavigationView
        userName = headerView.findViewById(R.id.userName)
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
