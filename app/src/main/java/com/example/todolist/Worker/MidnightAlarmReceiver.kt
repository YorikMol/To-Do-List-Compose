package com.example.todolist.Worker

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.todolist.RoomDatabase.AppDatabase
import com.example.todolist.RoomDatabase.MyDayEntity
import com.example.todolist.RoomDatabase.TaskEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.util.Calendar

class MidnightAlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val database = AppDatabase.getInstance(context.applicationContext)
        val todosFlow: Flow<List<MyDayEntity>> = database.MyDayDao().getAll()
        CoroutineScope(Dispatchers.IO).launch { todosFlow.collect { dataFromToDo ->
            for (item in dataFromToDo) {
                val newItemForTaskEntity = TaskEntity(todo = item.todo, isChecked = item.isChecked)
                database.MyDayDao().delete(item)
                database.taskDao().insertAll(newItemForTaskEntity)
            }
        } }

    }
}
fun scheduleMidnightAlarm(context: Context) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, MidnightAlarmReceiver::class.java)
    val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

    val midnightCalendar = Calendar.getInstance()
    midnightCalendar.timeInMillis = System.currentTimeMillis()
    midnightCalendar.set(Calendar.HOUR_OF_DAY, 0)
    midnightCalendar.set(Calendar.MINUTE, 0)
    midnightCalendar.set(Calendar.SECOND, 0)
    midnightCalendar.add(Calendar.DAY_OF_YEAR, 1) // Set to next day midnight

    alarmManager.setRepeating(
        AlarmManager.RTC_WAKEUP,
        midnightCalendar.timeInMillis,
        AlarmManager.INTERVAL_DAY,
        pendingIntent
    )
}