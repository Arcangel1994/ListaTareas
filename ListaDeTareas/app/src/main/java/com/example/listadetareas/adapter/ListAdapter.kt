package com.example.listadetareas.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.listadetareas.R
import com.example.listadetareas.data.models.Prioridad
import com.example.listadetareas.data.models.Tarea
import com.example.listadetareas.databinding.RowLayoutBinding
import com.example.listadetareas.fragments.list.ListFragmentDirections

class ListAdapter: RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    var dataList = emptyList<Tarea>()
        get() = field
        set(value) {
            val toDoDiffUtil = ToDoDiffUtil(field, value)
            val toDoDiffResult = DiffUtil.calculateDiff(toDoDiffUtil)
            field = value
            toDoDiffResult.dispatchUpdatesTo(this)
            //notifyDataSetChanged()
        }

    fun setData(toDoData: List<Tarea>){
        val toDoDiffUtil = ToDoDiffUtil(dataList, toDoData)
        val toDoDiffResult = DiffUtil.calculateDiff(toDoDiffUtil)
        this.dataList = toDoData
        toDoDiffResult.dispatchUpdatesTo(this)
        //notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = RowLayoutBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        with(holder){
            with(dataList[position]){
                binding.titleTxt.text = title
                binding.descriptionTxt.text = description
                when(priority){
                    Prioridad.ALTA -> {
                        binding.priorityIndicator.setCardBackgroundColor(ContextCompat.getColor(binding.priorityIndicator.context, R.color.red))
                    }
                    Prioridad.MEDIANA -> {
                        binding.priorityIndicator.setCardBackgroundColor(ContextCompat.getColor(binding.priorityIndicator.context, R.color.yellow))
                    }
                    Prioridad.BAJA -> {
                        binding.priorityIndicator.setCardBackgroundColor(ContextCompat.getColor(binding.priorityIndicator.context, R.color.green))
                    }
                }

                holder.itemView.setOnClickListener { view ->
                    val action = ListFragmentDirections.actionListFragmentToUpdateFragment(dataList[position])
                    view.findNavController().navigate(action)
                }

            }
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    class MyViewHolder(val binding: RowLayoutBinding) :RecyclerView.ViewHolder(binding.root)

}