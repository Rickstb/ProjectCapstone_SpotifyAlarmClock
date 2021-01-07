package com.example.projectcapstone_spotifyalarmclock.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TimePicker
import com.example.projectcapstone_spotifyalarmclock.R

class SetAlarmActivity : AppCompatActivity() {

    class Args {
        companion object {
            const val HOUR = "hour"
            const val MINUTES = "minutes"
        }
    }
    var timePicker: TimePicker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_alarm)

        timePicker = findViewById(R.id.timepicker)
    }
//de add alarm button, neemt de gegevens mee van de timepicker
    fun saveAlarm(btn: View) {
        var hour: Int?
        var minutes: Int?

        if ( android.os.Build.VERSION.SDK_INT >= 23 ) {

            hour = timePicker?.hour
            minutes = timePicker?.minute

        } else {

            hour = timePicker?.currentHour
            minutes = timePicker?.currentMinute
        }

        val intent = Intent().apply {
            putExtra(Args.HOUR, hour)
            putExtra(Args.MINUTES, minutes)
        }

        setResult(Activity.RESULT_OK, intent)
        finish()
    }

}