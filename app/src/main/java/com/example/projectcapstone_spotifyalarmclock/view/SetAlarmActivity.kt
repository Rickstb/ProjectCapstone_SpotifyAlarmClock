package com.example.projectcapstone_spotifyalarmclock.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.projectcapstone_spotifyalarmclock.R

class SetAlarmActivity : AppCompatActivity() {

    class Args {
        companion object {
            const val HOUR = "hour"
            const val MINUTES = "minutes"
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_alarm)
    }
}