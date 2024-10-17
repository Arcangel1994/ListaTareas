package com.example.listadetareas.data.repository

import androidx.lifecycle.LiveData
import com.example.listadetareas.data.dao.TareaDao
import com.example.listadetareas.data.models.Tarea
import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton



/*repositoria mandando dao desde la implementacion de viewmodel
creando la instancia de la base de datos desde viewmodel:
class TareasReposotory(private val tareaDao: TareaDao) {*/
@ViewModelScoped
class TareasReposotory @Inject constructor(private val tareaDao: TareaDao) {

    val getAllTasks: Flow<List<Tarea>> = tareaDao.getAllTasks()

    val getAllData: LiveData<List<Tarea>> = tareaDao.getAllData()

    suspend fun insertData(tarea: Tarea) {
        tareaDao.insertData(tarea)
    }

    suspend fun updateData(tarea: Tarea) {
        tareaDao.updateData(tarea)
    }

    suspend fun deleteData(tarea: Tarea) {
        tareaDao.deleteData(tarea)
    }

    suspend fun deleteAllData() {
        tareaDao.deleteAllData()
    }

    fun searchDatabase(searchQuery: String) : LiveData<List<Tarea>> {
        return tareaDao.searchDatabase(searchQuery)
    }

    fun sortByHighPriority() : LiveData<List<Tarea>> {
        return tareaDao.sortByHighPriority()
    }

    fun sortByLowPriority() : LiveData<List<Tarea>> {
        return tareaDao.sortByLowPriority()
    }

}