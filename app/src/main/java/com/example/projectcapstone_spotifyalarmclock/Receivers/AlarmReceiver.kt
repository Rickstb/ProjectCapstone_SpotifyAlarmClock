package com.example.projectcapstone_spotifyalarmclock.Receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.projectcapstone_spotifyalarmclock.utils.AlarmUtils
import com.example.projectcapstone_spotifyalarmclock.utils.NotificationUtils


class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        Log.v("TESTING", "!!!!!!!!!!!!!!!!!!! ALARM !!!!!!!!!!!!!!!!!!!!!")

        NotificationUtils.launchNotifications(
            context,
            title = "¡¡¡Alarm de ${ intent.extras?.getString(AlarmUtils.Extras.HOUR) } !!!"
        )

    }
}
