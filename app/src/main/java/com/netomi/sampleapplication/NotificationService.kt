package com.netomi.sampleapplication
import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.text.Spanned
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.text.HtmlCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.netomi.sampleapplication.ui.activity.HomeActivity


const val TAG = "NotificationService"


class NotificationService : FirebaseMessagingService() {

    companion object {
        private const val TAG = "PushNotificationService"
        private const val CHANNEL_ID = "PUSH_NOTIFICATION_CHANNEL"
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        // Extract title & body from the notification payload
       // val title = remoteMessage.notification?.title ?: "New Notification"
        //val htmlBody = remoteMessage.notification?.body ?: "No content"

        val title = remoteMessage.data["title"]
        val htmlBody = remoteMessage.data["body"]
        // Extract data from notification payload
        val botRefId = remoteMessage.data["botRefId"] ?: "No Bot Ref ID"
        val env = remoteMessage.data["env"] ?: "No Env"


        val intent = Intent(this, HomeActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP) // Ensures proper navigation
            putExtra("botRefId", botRefId)
            putExtra("env", env)
        }

        // Convert HTML body to formatted text
        val formattedText: Spanned = HtmlCompat.fromHtml(htmlBody?:"", HtmlCompat.FROM_HTML_MODE_LEGACY)

        Log.d(TAG, "Received Notification: Title=$title, Body=$htmlBody")

        // Show the notification
        if (title != null && !SampleApplication.appLifecycleObserver.isAppInForeground) {
            showNotification(title, formattedText,intent)
        }
    }

    private fun showNotification(title: String, body: Spanned,intent: Intent) {
        // Create notification channel (for Android 8+)
        createNotificationChannel()

        // Intent to open MainActivity when user taps on notification
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Build the notification
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_logo) // Add a valid drawable in res/drawable
            .setContentTitle(title)
            .setContentText(body)
            .setStyle(NotificationCompat.BigTextStyle().bigText(body)) // Expanded notification
            .setContentIntent(pendingIntent) // Open app on click
            .setAutoCancel(true) // Remove notification when clicked
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        // Show the notification
        with(NotificationManagerCompat.from(this)) {
            if (ActivityCompat.checkSelfPermission(
                    applicationContext,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            notify(1, notification.build())
        }
    }

    private fun createNotificationChannel() {
        val name = "Push Notifications"
        val descriptionText = "Firebase Push Notifications"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}