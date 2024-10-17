package com.example.listadetareas.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.listadetareas.data.TareaDatabase
import com.example.listadetareas.data.dao.TareaDao
import com.example.listadetareas.data.models.Tarea
import com.example.listadetareas.data.repository.TareasReposotory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

//la clase se le pone contruirtor cuando es inyectada
@HiltViewModel
class TareasViewModel @Inject constructor(private val repository: TareasReposotory): ViewModel() {

    //ya no se se crea la base de datos con singleton
    //private val tareaDao: TareaDao = TareaDatabase.getDatabase(application).tareaDao()

    //Ya no se le manda al repositorio el dao para que ajecute las tareas
    //private val repository: TareasReposotory = TareasReposotory(tareaDao)

    val getAllData: LiveData<List<Tarea>> = repository.getAllData

    val sortByHighPriority: LiveData<List<Tarea>> = repository.sortByHighPriority()
    val sortByLowPriority: LiveData<List<Tarea>> = repository.sortByLowPriority()

    fun insert(toDoData: Tarea){
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertData(toDoData)
        }
    }

    fun updateData(toDoData: Tarea){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateData(toDoData)
        }
    }

    fun deleteData(toDoData: Tarea){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteData(toDoData)
        }
    }

    fun deleteAllData(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllData()
        }
    }

    fun searchDatabase(searchQuery: String): LiveData<List<Tarea>> {
        return repository.searchDatabase(searchQuery)
    }

}