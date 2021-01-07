package com.example.projectcapstone_spotifyalarmclock.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.projectcapstone_spotifyalarmclock.model.Alarm
// Data Acces object, biedt de methoden die de rest van de app gebruikt om te communiceren met gegevens in de database.
@Dao
interface AlarmDAO {

    @Insert
    fun insertAlarm (alarm : Alarm): Long

    @Query("SELECT * FROM Alarmlist")
    fun getAllAlarms(): MutableList<Alarm>

    @Query("UPDATE alarmlist SET alarmactive = :alarmactive WHERE id = :idAlarm")
    fun updateActive(idAlarm: Long, alarmactive: Boolean)

    @Delete
     fun deleteAlarm(alarm: Alarm)
}