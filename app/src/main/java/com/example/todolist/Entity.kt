package com.example.todolist

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MyDayEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var todo: String,
    var isChecked:Boolean=false,
)
@Entity
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var todo: String,
    var isChecked:Boolean=false,
)
@Entity
data class NewListEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var todo: String,
    var isChecked: Boolean = false,
)
@Entity
data class ScreenNameEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val screenName: String
)