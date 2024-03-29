package com.example.todolist.RoomDatabase

import androidx.room.Embedded
import androidx.room.Relation

data class ScreenNameWithNewLists(
    @Embedded val screenNameEntity: ScreenNameEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "screenNameId"
    )
    val newLists: List<NewListEntity>
)
