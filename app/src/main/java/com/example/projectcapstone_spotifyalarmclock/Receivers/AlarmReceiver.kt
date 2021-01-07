package com.example.projectcapstone_spotifyalarmclock.Receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.projectcapstone_spotifyalarmclock.utils.AlarmUtils
import com.example.projectcapstone_spotifyalarmclock.utils.NotificationUtils

// zorgt ervoor dat er een melding weer wordt gegeven op het scherm als er een alarm af gaat.
class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        Log.v("TESTING", "!!!!!!!!!!!!!!!!!!! ALARM !!!!!!!!!!!!!!!!!!!!!")

        NotificationUtils.launchNotifications(
            context,
            title = "!!!!!!!!!!!!!!! Alarm !!!!!!!!!!!!!!!!!!!"
        )

    }
}
