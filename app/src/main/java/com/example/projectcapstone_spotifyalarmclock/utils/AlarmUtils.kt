package com.example.projectcapstone_spotifyalarmclock.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.projectcapstone_spotifyalarmclock.Receivers.AlarmReceiver
import com.example.projectcapstone_spotifyalarmclock.view.SetAlarmActivity
import java.util.*

class AlarmUtils {

    class Extras {
        companion object {
            const val HOUR = "hour"
        }
    }

    companion object {
//RTC_WAKEUP- Maakt het apparaat wakker om de in behandeling zijnde intentie op het opgegeven tijdstip te activeren.
        // pendingIntent wacht af tot het alarm geactiveert mag worden
        fun enableAlarm(context: Context, calendar: Calendar, hour: String, codAlarm: Int) {
            val alarmManager: AlarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

            val intent = Intent(context, AlarmReceiver::class.java).apply {
                putExtra(SetAlarmActivity.Args.HOUR, hour)
            }

            val pendingIntent = PendingIntent.getBroadcast(context, codAlarm, intent, 0)

            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)

        }
//Zet het alarm uit
        fun disabledAlarm(context: Context, codAlarm: Int) {
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, AlarmReceiver::class.java)

            val pendingIntent = PendingIntent.getBroadcast(context, codAlarm, intent, 0)

            alarmManager.cancel(pendingIntent)
        }
    }

}