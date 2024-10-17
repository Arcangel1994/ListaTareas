package com.example.listadetareas.data

import androidx.room.TypeConverter
import com.example.listadetareas.data.models.Prioridad

class Converter {

    @TypeConverter
    fun fromPriority(priority: Prioridad): String{
        return priority.name
    }

    @TypeConverter
    fun toPriority(priority: String): Prioridad {
        return Prioridad.valueOf(priority)
    }

}