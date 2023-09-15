package com.example.todolist

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface MyDayDao {

    @Query("SELECT * FROM mydayentity")
    fun getAll(): Flow<List<MyDayEntity>>
    @Insert
    suspend fun insertAll(vararg users: MyDayEntity)
    @Delete
    suspend fun delete(user: MyDayEntity)
    @Update
    suspend fun updateItem(item: MyDayEntity)
    @Query("SELECT * FROM MyDayEntity ORDER BY isChecked ASC")
    fun getAllUsersSortedByCheckedFlow(): Flow<List<MyDayEntity>>

}

@Dao
interface TaskDao {
    @Query("SELECT * FROM taskentity")
    fun getAll(): Flow<List<TaskEntity>>
    @Insert
    suspend fun insertAll(vararg items: TaskEntity)

    @Delete
    suspend fun delete(item: TaskEntity)
    @Update
    suspend fun updateItem(item: TaskEntity)
}

@Dao
interface ScreenNameDao {
    @Query("SELECT * FROM screennameentity")
    fun getAll(): Flow<List<ScreenNameEntity>>

    @Insert
    suspend fun insert(screen: ScreenNameEntity)

}
@Dao
interface NewListDAO {
    @Query("SELECT * FROM newlistentity")
    fun getAll(): Flow<List<NewListEntity>>
    @Insert
    suspend fun insertAll(vararg items: NewListEntity)

    @Delete
    suspend fun delete(item: NewListEntity)

}