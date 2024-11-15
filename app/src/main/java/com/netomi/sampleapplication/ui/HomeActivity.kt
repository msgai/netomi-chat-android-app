package com.netomi.sampleapplication.ui

import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.netomi.sampleapplication.R

class HomeActivity : AppCompatActivity() {

    // Declare variables for views
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    private lateinit var ivMenu: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)  // Set the layout for this activity

        // Initialize the views
        initializeViews()

        // Set up click listener for the menu icon to open the navigation drawer
        ivMenu.setOnClickListener {
            drawerLayout.openDrawer(navView)  // Open the navigation drawer when the menu icon is clicked
        }
    }

    // Initialize the views
    private fun initializeViews() {
        drawerLayout = findViewById(R.id.drawerLayout)  // Find DrawerLayout by ID
        ivMenu = findViewById(R.id.ivMenu)  // Find the ImageView for the menu icon
        navView = findViewById(R.id.navView)  // Find the NavigationView for the drawer content
    }
}
