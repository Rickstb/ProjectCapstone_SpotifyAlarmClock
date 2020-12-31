package com.example.projectcapstone_spotifyalarmclock.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.projectcapstone_spotifyalarmclock.model.Alarm

@Dao
interface AlarmDAO {
    @Query("SELECT * FROM Alarmlist")
    suspend fun getAllAlarms(): List<Alarm>

    @Insert
    suspend fun insertAlarm (alarm : Alarm)

    @Delete
    suspend fun deleteAlarm(alarm: Alarm)

    @Query("UPDATE alarmlist SET alarmactive = :alarmactive WHERE id = :idAlarm")
    fun updateActive(idAlarm: Long, alarmactive: Boolean)
}