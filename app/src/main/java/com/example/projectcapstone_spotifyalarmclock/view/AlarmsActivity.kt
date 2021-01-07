package com.example.projectcapstone_spotifyalarmclock.view

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.projectcapstone_spotifyalarmclock.R
import com.example.projectcapstone_spotifyalarmclock.data.AlarmDatabase
import com.example.projectcapstone_spotifyalarmclock.model.Alarm
import com.example.projectcapstone_spotifyalarmclock.utils.AlarmAdapter
import com.example.projectcapstone_spotifyalarmclock.utils.AlarmUtils
import kotlinx.android.synthetic.main.activity_alarms.*
import java.text.SimpleDateFormat
import java.util.*


class AlarmsActivity : AppCompatActivity(), AlarmAdapter.OnEnableAlarmListener,
    AlarmAdapter.OnLongClickAlarmListener {

    var recyclerAlarms: RecyclerView? = null

    var alarmAdapter: AlarmAdapter? = null

    var db: AlarmDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarms)
        initview()
        linkXML();

        db = Room.databaseBuilder(
            applicationContext,
            AlarmDatabase::class.java,
            getString(R.string.db_name)
        ).build()

        Thread {
            val alarms = db?.alarmDAO()?.getAllAlarms() ?: mutableListOf()

            runOnUiThread {
                alarmAdapter?.alarms = alarms
            }

        }.start()
        val today: Calendar = Calendar.getInstance()
        println(SimpleDateFormat("EEEE, d MMMM").format((today.time)))
        TextDate.text = SimpleDateFormat("EEEE, d MMMM").format((today.time))

    }

    private fun linkXML() {
        recyclerAlarms = findViewById(R.id.rvAlarms)

        val lmAlarms = LinearLayoutManager(this)
        recyclerAlarms?.layoutManager = lmAlarms

        alarmAdapter = AlarmAdapter(mutableListOf())
        alarmAdapter?.onEnableAlarmListener = this
        alarmAdapter?.onLongClickAlarmListener = this
        recyclerAlarms?.adapter = alarmAdapter

    }

    private fun initview() {
        fabAddAlarm.setOnClickListener {
            val intent = Intent(this, SetAlarmActivity::class.java)
            startActivityForResult(intent, 1)

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {


            val hour = data?.getIntExtra(SetAlarmActivity.Args.HOUR, 0) ?: 0
            val minutes = data?.getIntExtra(SetAlarmActivity.Args.MINUTES, 0) ?: 0

            val alarm = Alarm(hour, minutes, alarmactive = false)

            Thread {

                val id = db?.alarmDAO()?.insertAlarm(alarm) ?: 0
                Log.v("TESTING", "Id inserted: &${id}")

                alarm.id = id

                runOnUiThread {

                    alarmAdapter?.addAlarm(alarm)

                }

            }.start()
        }
    }

// Checkt of het alarm aanstaat (als de switch wordt overgehaald)
    override fun onEnableAlarm(alarm: Alarm, position: Int) {

        Thread {

            db?.alarmDAO()?.updateActive(alarm.id, alarm.alarmactive)

            runOnUiThread {

                if (alarm.alarmactive) {
                    // Log.v("TESTING", "Id inserted:")
                    val calendar = Calendar.getInstance()
                    calendar.set(Calendar.HOUR_OF_DAY, alarm.hour)
                    calendar.set(Calendar.MINUTE, alarm.minutes)
                    calendar.set(Calendar.SECOND, 0)

                    AlarmUtils.enableAlarm(this, calendar, alarm.hourFormat, alarm.id.toInt())

                } else {
                    AlarmUtils.disabledAlarm(this, alarm.id.toInt())
                }
            }
        }.start()
    }
// checkt op Longclick en geeft popup, verwijderd een alarm
    override fun onLongClickAlarm(alarm: Alarm, position: Int) {

        val dialog = AlertDialog.Builder(this)
            .setTitle("Are you sure want to remove this alarm?")
            .setPositiveButton("YES") { _, _ ->

                Thread {

                    db?.alarmDAO()?.deleteAlarm(alarm)

                    runOnUiThread {

                        alarmAdapter?.deleteAlarm(alarm)

                    }
                }.start()

            }
            .setNegativeButton("NO", null)
            .create()

        if (!isFinishing)
            dialog.show()
    }

}