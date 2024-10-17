package com.example.listadetareas.di

import android.content.Context
import androidx.room.Room
import com.example.listadetareas.data.TareaDatabase
import com.example.listadetareas.data.dao.TareaDao
import com.example.listadetareas.data.repository.TareasReposotory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDBModule {

    @Provides
    @Singleton
    fun provideTareaDatabase(@ApplicationContext context: Context): TareaDatabase {
        return TareaDatabase.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideTareaDao(appDatabase: TareaDatabase): TareaDao {
        return appDatabase.tareaDao()
    }

    /*@Provides
    @Singleton
    fun provideTareasReposotory(tareaDao: TareaDao): TareasReposotory {
        return TareasReposotory(tareaDao)
    }*/

}