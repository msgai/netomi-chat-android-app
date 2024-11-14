package com.netomi.sampleapplication

import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class HomeActivity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    private lateinit var ivMenu: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


        drawerLayout = findViewById(R.id.drawerLayout)
        ivMenu = findViewById(R.id.ivMenu)
        navView = findViewById(R.id.navView)

        ivMenu.setOnClickListener {
            drawerLayout.openDrawer(navView)
        }


    }
}