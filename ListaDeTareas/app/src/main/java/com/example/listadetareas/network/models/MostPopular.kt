package com.example.listadetareas.network.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
class MostPopular(

    @SerializedName("results")
    var results: List<MostPopularResult>? = ArrayList()

): Parcelable