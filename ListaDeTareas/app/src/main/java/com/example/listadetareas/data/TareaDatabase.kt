package com.example.listadetareas.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import com.example.listadetareas.data.dao.TareaDao
import com.example.listadetareas.data.models.Tarea
import com.example.listadetareas.utils.Contants


@Database(entities = [Tarea::class], exportSchema = false, version = 1)
abstract class TareaDatabase : RoomDatabase() {

    abstract fun tareaDao(): TareaDao

    companion object{
        @Volatile private var instance: TareaDatabase? = null

        fun getInstance(context: Context): TareaDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDB(context).also { instance = it }
            }
        }

        private fun buildDB(context: Context): TareaDatabase {
            return databaseBuilder(
                context,
                TareaDatabase::class.java,
                Contants.TAREA_DATABASE_NAME
            ).fallbackToDestructiveMigration().build()
        }
    }

}