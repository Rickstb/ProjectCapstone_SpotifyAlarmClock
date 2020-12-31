package com.example.projectcapstone_spotifyalarmclock.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.projectcapstone_spotifyalarmclock.model.Alarm

@Database(entities = arrayOf(Alarm::class), version = 1)
abstract class AlarmDatabase : RoomDatabase(){
    abstract fun alarmDAO(): AlarmDAO
}