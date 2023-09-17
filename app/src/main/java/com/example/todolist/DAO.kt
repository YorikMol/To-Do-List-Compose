package com.example.todolist

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface MyDayDao {

    @Query("SELECT * FROM mydayentity")
    fun getAll(): Flow<List<MyDayEntity>>
    @Insert
    suspend fun insertAll(users: MyDayEntity)
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
    suspend fun insertAll(items: TaskEntity)

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
    @Transaction
    @Query("SELECT * FROM ScreenNameEntity WHERE id = :screenNameId")
    fun getScreenNameWithNewLists(screenNameId: Int): List<ScreenNameWithNewLists>
    @Query("SELECT screenName FROM ScreenNameEntity WHERE id = :screenNameId")
    suspend fun getScreenNameById(screenNameId: Int): String


}
@Dao
interface NewListDAO {
    @Query("SELECT * FROM newlistentity")
    fun getAll(): Flow<List<NewListEntity>>
    @Insert
    suspend fun insertAll(items: NewListEntity)
    @Query("SELECT * FROM NewListEntity WHERE screenNameId = :screenNameId")
    fun getNewListItemsByScreenNameId(screenNameId: Int): Flow<List<NewListEntity>>
    @Delete
    suspend fun delete(item: NewListEntity)
    @Update
    suspend fun updateItem(item: NewListEntity)

}