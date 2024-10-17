package com.example.listadetareas.network.repository

import com.example.listadetareas.network.TheMovieDBAPI
import com.example.listadetareas.network.models.MostPopular
import retrofit2.Response
import javax.inject.Inject

class TheMovieRepository @Inject constructor(private val theMovieDBAPI: TheMovieDBAPI) {

    //Most Popular
    suspend fun getMostPopular(): Response<MostPopular?> {
        return theMovieDBAPI.getMostPopular()
    }

}