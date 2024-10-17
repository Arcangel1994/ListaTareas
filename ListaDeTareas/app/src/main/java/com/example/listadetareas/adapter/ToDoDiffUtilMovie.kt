package com.example.listadetareas.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.listadetareas.network.models.MostPopularResult

class ToDoDiffUtilMovie(
    private val oldList: List<MostPopularResult>,
    private val newList: List<MostPopularResult>
): DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] === newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

}