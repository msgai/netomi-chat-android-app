package com.netomi.sampleapplication.ui

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.netomi.chat.ui.init.NCWChatSdk
import com.netomi.sampleapplication.R
import com.netomi.sampleapplication.utils.AppSharedPreferences

class HomeActivity : AppCompatActivity() {

    // Declare variables for views
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    private lateinit var ivMenu: ImageView
    private lateinit var logout: AppCompatTextView
    private lateinit var imgChat: AppCompatImageButton
    private lateinit var preferences :AppSharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)  // Set the layout for this activity

        // Initialize the views
        initializeViews()

        // Set up click listener for the menu icon to open the navigation drawer
        ivMenu.setOnClickListener {
            drawerLayout.openDrawer(navView)  // Open the navigation drawer when the menu icon is clicked
        }
        logout.setOnClickListener {
            preferences.clearSharedPreference()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }

        imgChat.setOnClickListener{
            NCWChatSdk.launch(this,"b23963e4-56c5-4d8f-929e-2b0f1155b1f8")
        }

    }

    // Initialize the views
    private fun initializeViews() {
        preferences = AppSharedPreferences(this)
        drawerLayout = findViewById(R.id.drawerLayout)  // Find DrawerLayout by ID
        ivMenu = findViewById(R.id.ivMenu)  // Find the ImageView for the menu icon
        navView = findViewById(R.id.navView)  // Find the NavigationView for the drawer content
        logout = findViewById(R.id.logout)  // Find the NavigationView for the drawer content
        imgChat=findViewById(R.id.img_chat)
    }
}
