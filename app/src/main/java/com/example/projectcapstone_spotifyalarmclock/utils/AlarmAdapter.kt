package com.example.projectcapstone_spotifyalarmclock.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projectcapstone_spotifyalarmclock.R
import com.example.projectcapstone_spotifyalarmclock.model.Alarm

// zorgt ervoor dat de alarms weer worden gegeven in de recyclerview.
class AlarmAdapter(alarms: MutableList<Alarm>) :RecyclerView.Adapter<AlarmAdapter.ViewHolder>() {
    var alarms: MutableList<Alarm> = alarms
        set(value) {
            field = value;
            notifyDataSetChanged()
        }
// checkt of de alarm uit of aan staat
    interface OnEnableAlarmListener {
        fun onEnableAlarm(alarms: Alarm, position: Int)
    }
// checkt of er wordt gelongclicked op een alarm
    interface OnLongClickAlarmListener {
        fun onLongClickAlarm(alarms: Alarm, position: Int)
    }

    var onEnableAlarmListener: OnEnableAlarmListener? = null
    var onLongClickAlarmListener: OnLongClickAlarmListener? = null

    // bindt de gegevens van de tijd aan de layout item_alarm, zodat de gegevens weer worden gegeven op het hoofdscherm
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val txtHour: TextView = itemView.findViewById(R.id.item_Time)
        val txtAmPm: TextView = itemView.findViewById(R.id.item_txt_am_pm)
        val switchOnOff: Switch = itemView.findViewById(R.id.item_switch)

        init {
            switchOnOff.setOnCheckedChangeListener {_, isChecked ->

                alarms[adapterPosition].alarmactive = isChecked

                onEnableAlarmListener?.onEnableAlarm(alarms[adapterPosition], adapterPosition)

            }

            itemView.setOnLongClickListener {_ ->

                onLongClickAlarmListener?.onLongClickAlarm(alarms[adapterPosition], adapterPosition)

                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val item = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_alarm, parent, false)

        return ViewHolder(item)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
// Listener is verwijderd, zodat het niet activeert wanneer de switch wordt overgehaald
        val listenerAux = onEnableAlarmListener;
        onEnableAlarmListener = null

        val alarm = alarms[position];

        holder.txtHour.text = alarm.hourFormat;
        holder.txtAmPm.text = alarm.amPm
        holder.switchOnOff.isChecked = alarm.alarmactive

        onEnableAlarmListener = listenerAux
    }

    override fun getItemCount(): Int {
        return alarms.size
    }
//voegt een alarm toe
    fun addAlarm(alarm: Alarm) {
        alarms.add(alarm)
        notifyItemInserted( alarms.size - 1 )
    }
//verwijderd een alarm
    fun deleteAlarm(alarm: Alarm) {
        val indice = alarms.indexOf(alarm)

        alarms.remove(alarm)

        notifyItemRemoved(indice)
    }
}