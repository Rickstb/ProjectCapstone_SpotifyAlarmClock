package com.example.projectcapstone_spotifyalarmclock.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.projectcapstone_spotifyalarmclock.R
import kotlinx.android.synthetic.main.activity_alarms.*

class AlarmsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarms)
        initview()

       // val today: Calendar = Calendar.getInstance()
        // println(SimpleDateFormat("EEEE,d MMMM").format((today.time)))
        // DateFormat dateFormat = new SimpleDateFormat("EEEE,d MMMM");

        // val pattern = "EEEE,d MMMM"
        //   val simpleDateFormat = SimpleDateFormat(pattern)
        //val date = simpleDateFormat.format(Date())
    }

    private fun initview() {
        fabAddAlarm.setOnClickListener{
            val intent = Intent(this, SetAlarmActivity::class.java)
            startActivity(intent)
        }
    }
}