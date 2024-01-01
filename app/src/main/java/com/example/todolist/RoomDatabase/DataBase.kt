package com.example.todolist.RoomDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MyDayEntity::class, TaskEntity::class, NewListEntity::class, ScreenNameEntity::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun MyDayDao(): MyDayDao
    abstract fun taskDao(): TaskDao
    abstract fun NewListDAO(): NewListDAO
    abstract fun ScreenNameDao(): ScreenNameDao
    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, "todo_database.db")
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}