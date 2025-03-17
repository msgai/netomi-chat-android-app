package com.netomi.sampleapplication.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.netomi.sampleapplication.R
import com.netomi.sampleapplication.constant.SharePreferenceConstant
import com.netomi.sampleapplication.utils.AppSharedPreferences

class SplashActivity : AppCompatActivity() {


    private lateinit var preferences :AppSharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        preferences = AppSharedPreferences(this)
        setContentView(R.layout.activity_splash)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        Handler(Looper.getMainLooper()).postDelayed({
           if (preferences.getBoolean(SharePreferenceConstant.LOGIN)){
               val intent = Intent(this, HomeActivity::class.java)
               startActivity(intent)
               finish() // Finish splash activity to remove it from the back stack
           }else {
               val intent = Intent(this, LoginActivity::class.java)
               startActivity(intent)
               finish() // Finish splash activity to remove it from the back stack
           }
            // Start the next activity
        }, 2000) // 2000 milliseconds = 2 seconds
    }
}