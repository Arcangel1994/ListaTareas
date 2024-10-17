package com.example.listadetareas.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.listadetareas.data.models.Tarea
import kotlinx.coroutines.flow.Flow

@Dao
interface TareaDao {

    @Query("SELECT * FROM tarea_table ORDER BY id ASC")
    fun getAllTasks(): Flow<List<Tarea>>

    @Query("SELECT * FROM tarea_table ORDER BY id ASC")
    fun getAllData(): LiveData<List<Tarea>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertData(toDoData: Tarea)

    @Update
    suspend fun updateData(toDoData: Tarea)

    @Delete
    suspend fun deleteData(toDoData: Tarea)

    @Query("DELETE FROM tarea_table")
    suspend fun deleteAllData()

    @Query("SELECT * FROM tarea_table WHERE title LIKE :searchQuery")
    fun searchDatabase(searchQuery: String): LiveData<List<Tarea>>

    @Query("SELECT * FROM tarea_table ORDER BY CASE WHEN priority LIKE 'H%' THEN 1 WHEN priority LIKE 'M%' THEN 2 WHEN priority LIKE 'L%' THEN 3 END")
    fun sortByHighPriority(): LiveData<List<Tarea>>

    @Query("SELECT * FROM tarea_table ORDER BY CASE WHEN priority LIKE 'L%' THEN 1 WHEN priority LIKE 'M%' THEN 2 WHEN priority LIKE 'H%' THEN 3 END")
    fun sortByLowPriority(): LiveData<List<Tarea>>

}