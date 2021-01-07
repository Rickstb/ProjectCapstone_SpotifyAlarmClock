package com.example.projectcapstone_spotifyalarmclock.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
//zorgt ervoor dat er een geluid wordt afgespeelt als een alarm afgaat.
const val CHANNEL_ID = "c_alarm"
const val CHANNEL_NAME= "Alarm notifications"
const val CHANNEL_DESCRIPTION = "Alarm app notifications"

class NotificationUtils {

    companion object {
// Launcht de notification
        fun launchNotifications(context: Context, title: String) {

            createNotificationChannel(context)

            val sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

            val builder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
                .setContentTitle(title)
                .setSound(sound)
                .setPriority(NotificationCompat.PRIORITY_HIGH)

            val notiManager = NotificationManagerCompat.from(context)

            notiManager.notify(222, builder.build())
        }

      //  creeert een meldingskanaal
        private fun createNotificationChannel(context: Context) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                val descriptionText = CHANNEL_DESCRIPTION
                val importance = NotificationManager.IMPORTANCE_HIGH

                val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance).apply {
                    description = descriptionText
                }

                val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(channel)
            }
        }
    }

}