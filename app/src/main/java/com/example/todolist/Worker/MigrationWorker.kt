package com.example.todolist.Worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.todolist.RoomDatabase.AppDatabase
import com.example.todolist.RoomDatabase.MyDayEntity
import com.example.todolist.RoomDatabase.TaskEntity
import kotlinx.coroutines.flow.Flow


class MigrationWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        return try {
            val database = AppDatabase.getInstance(applicationContext)
            val todosFlow: Flow<List<MyDayEntity>> = database.MyDayDao().getAll()
            todosFlow.collect { dataFromToDo ->
                for (item in dataFromToDo) {
                    val newItemForTaskEntity = TaskEntity(todo = item.todo, isChecked = item.isChecked)
                    database.MyDayDao().delete(item)
                    database.taskDao().insertAll(newItemForTaskEntity)
                }
            }
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }
}
