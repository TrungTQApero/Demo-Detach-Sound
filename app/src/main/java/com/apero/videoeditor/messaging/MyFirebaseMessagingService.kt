package com.apero.videoeditor.messaging

import android.app.NotificationManager
import android.media.RingtoneManager
import androidx.core.app.NotificationCompat
import com.apero.videoeditor.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        // Check if message contains a notification payload.
        if (remoteMessage.notification != null){
            var notificationBuilder = NotificationCompat.Builder(this, "channel_id")
                .setContentTitle(remoteMessage.notification!!.title)
                .setContentText(remoteMessage.notification!!.body)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setSmallIcon(R.mipmap.ic_logo)
                .setAutoCancel(true)

            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

            notificationManager.notify(0, notificationBuilder.build())
        }
    }
}