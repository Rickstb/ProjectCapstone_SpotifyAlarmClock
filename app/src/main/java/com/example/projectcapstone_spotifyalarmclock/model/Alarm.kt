package com.example.projectcapstone_spotifyalarmclock.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Alarmlist")
class Alarm(
    var hour: Int,
    var minutes: Int,
    var alarmactive: Boolean
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    var amPm: String = "AM"
    var hourFormat: String = ""

    init {
        reformatedHour(hour, minutes)
    }

    private fun reformatedHour(hourInt: Int, minutesInt: Int) {
        var newMinutes: String = minutesInt.toString()

        if (minutesInt < 10)  newMinutes = "0" + minutesInt

        if (hourInt > 12) {
            val hour12 = hourInt - 12

            var newHour: String = hour12.toString()

            if (hour12 < 10)
                newHour = "0" + hour12
            hourFormat = "${newHour}:${newMinutes}"
            amPm = "PM"
        } else {
            var newHour: String = hourInt.toString()

            if (hourInt < 10)
                newHour = "0" + hourInt
            hourFormat = "${newHour}:${newMinutes}"
            amPm = "AM"
        }

    }
}