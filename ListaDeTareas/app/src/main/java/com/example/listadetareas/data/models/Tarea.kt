package com.example.listadetareas.data.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.listadetareas.utils.Contants
import kotlinx.parcelize.Parcelize

//Parciar la data class para que pueda ser identificada como un objecto al pasarlo entre vistas
@Entity(tableName = Contants.TAREA_TABLE)
@Parcelize
data class Tarea(

    @PrimaryKey(autoGenerate = true)
    var id: Int,

    var title: String,

    var priority: Prioridad,

    var description: String

): Parcelable