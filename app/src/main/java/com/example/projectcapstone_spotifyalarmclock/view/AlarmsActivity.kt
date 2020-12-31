package com.example.projectcapstone_spotifyalarmclock.view

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import java.util.*

const val COD_CREATE_ALARM = 354

class AlarmsActivity : AppCompatActivity(),AlarmAdapter.OnEnableAlarmListener,
     AlarmAdapter.OnLongClickAlarmListener {

    //var contNoAlarms: LinearLayout? = null
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

        Thread { // Access to the database must be done in another thread
            val alarms = db?.alarmDAO()?.getAllAlarms() ?: mutableListOf()

            runOnUiThread {
                alarmAdapter?.alarms = alarms
               // toggleVisibilityNoAlarms()
            }

        }.start()
    }
    private fun linkXML() {
       // contNoAlarms = findViewById(R.id.cont_no_hay_alarmas)
        recyclerAlarms = findViewById(R.id.rvAlarms)

        val lmAlarms = LinearLayoutManager(this)
        recyclerAlarms?.layoutManager = lmAlarms

        alarmAdapter = AlarmAdapter(mutableListOf())
        alarmAdapter?.onEnableAlarmListener = this
        alarmAdapter?.onLongClickAlarmListener = this
        recyclerAlarms?.adapter = alarmAdapter

    }

//    private fun toggleVisibilityNoAlarms() {
//        alarmAdapter?.let {
//            contNoAlarms?.visibility = if ( it.itemCount > 0 ) View.GONE else View.VISIBLE
//        }
//    }

    private fun initview() {
        fabAddAlarm.setOnClickListener{
            val intent = Intent(this, SetAlarmActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {

            when (requestCode) {
                COD_CREATE_ALARM ->{

                    val hour = data?.getIntExtra(SetAlarmActivity.Args.HOUR, 0 ) ?: 0
                    val minutes = data?.getIntExtra(SetAlarmActivity.Args.MINUTES, 0 ) ?: 0

                    val alarm = Alarm(hour, minutes, alarmactive = false)

                    Thread {// Access to the database must be done in another thread

                        val id = db?.alarmDAO()?.insertAlarm(alarm) ?: 0
                        Log.v("TESTING", "Id inserted: &${ id }")

                        alarm.id = id

                        runOnUiThread {

                            alarmAdapter?.addAlarm(alarm)

                           // toggleVisibilityNoAlarms()
                        }

                    }.start()
                }
            }

        }
    }

    override fun onEnableAlarm(alarm: Alarm, position: Int) {

        Thread {

            db?.alarmDAO()?.updateActive(alarm.id, alarm.alarmactive)

            runOnUiThread {

                if (alarm.alarmactive) {

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

    override fun onLongClickAlarm(alarm: Alarm, position: Int) {

        val dialog = AlertDialog.Builder(this)
            .setTitle("¿\n" +"You sure want to remove the alarm from ${ alarm.hourFormat }${ alarm.amPm }?")
            .setPositiveButton("YES") {_, _ ->

                Thread {

                    db?.alarmDAO()?.deleteAlarm(alarm)

                    runOnUiThread {

                        alarmAdapter?.deleteAlarm(alarm)

                        //toggleVisibilityNoAlarms()

                    }
                }.start()

            }
            .setNegativeButton("NO", null)
            .create()

        if ( !isFinishing )
            dialog.show()
    }

}

       // val today: Calendar = Calendar.getInstance()
        // println(SimpleDateFormat("EEEE,d MMMM").format((today.time)))
        // DateFormat dateFormat = new SimpleDateFormat("EEEE,d MMMM");

        // val pattern = "EEEE,d MMMM"
        //   val simpleDateFormat = SimpleDateFormat(pattern)
        //val date = simpleDateFormat.format(Date())



