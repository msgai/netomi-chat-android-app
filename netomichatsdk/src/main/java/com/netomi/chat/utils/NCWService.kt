package com.netomi.chat.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.netomi.chat.R

class NCWService : Service() {
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForeground(1, createNotification()) // Required for Android 14+
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private fun createNotification(): Notification {
        val channelId = "mqtt_channel"
        val channel = NotificationChannel(channelId, "Netomi", NotificationManager.IMPORTANCE_LOW)
        getSystemService(NotificationManager::class.java).createNotificationChannel(channel)

        return Notification.Builder(this, channelId)
            .setContentTitle("Netomi")
            .setContentText("Running in the background")
            .setSmallIcon(R.drawable.ic_bot_profile)
            .build()
    }

    override fun onDestroy() {
        super.onDestroy()
        stopForeground(true) // Remove the foreground notification
        stopSelf() // Stop the service
    }

}
