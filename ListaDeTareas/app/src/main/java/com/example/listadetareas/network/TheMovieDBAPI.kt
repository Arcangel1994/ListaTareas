package com.example.listadetareas.network

import com.example.listadetareas.network.models.MostPopular
import com.example.listadetareas.utils.Contants
import retrofit2.Response
import retrofit2.http.GET

interface TheMovieDBAPI {

    @GET("popular?api_key=${Contants.API_KEY}&language=es-MX&page=1")
    suspend fun getMostPopular(): Response<MostPopular?>

}