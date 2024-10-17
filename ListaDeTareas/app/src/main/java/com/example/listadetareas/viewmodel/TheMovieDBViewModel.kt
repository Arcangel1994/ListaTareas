package com.example.listadetareas.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.listadetareas.network.models.MostPopularResult
import com.example.listadetareas.network.repository.TheMovieRepository
import com.example.listadetareas.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TheMovieDBViewModel  @Inject constructor(private val repository: TheMovieRepository): ViewModel(){

    var mostPopular: MutableLiveData<NetworkResult<List<MostPopularResult>?>> = MutableLiveData()

    fun getMostPopular() = viewModelScope.launch {
        mostPopular.value = NetworkResult.Loading()
        try {

            val response = repository.getMostPopular()

            if(response.isSuccessful){

                mostPopular.value = NetworkResult.Success(response.body()!!.results)

            }else{

                mostPopular.value = NetworkResult.Error("Error en la consulta.")

            }

        }catch (e: Exception){

            mostPopular.value = NetworkResult.Error("Error en la consulta.")

        }

    }

}