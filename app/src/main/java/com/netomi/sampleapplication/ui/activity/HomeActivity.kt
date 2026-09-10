package com.netomi.sampleapplication.ui.activity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.netomi.sampleapplication.R
import com.netomi.sampleapplication.ui.fragment.HomeFragment

class HomeActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var progressOverlay: FrameLayout
    private val notificationPermission = 100
    private var permissions = arrayOf(Manifest.permission.POST_NOTIFICATIONS)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        checkPermission()
        initView()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HomeFragment())
                .commit()
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
        drawerLayout = findViewById(R.id.drawerLayout)
        progressOverlay = findViewById(R.id.progress_overlay)
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
    }

}
