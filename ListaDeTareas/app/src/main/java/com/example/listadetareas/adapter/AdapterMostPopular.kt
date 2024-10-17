package com.example.listadetareas.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.listadetareas.R
import com.example.listadetareas.databinding.MoviesLayoutBinding
import com.example.listadetareas.fragments.list.ListFragmentDirections
import com.example.listadetareas.fragments.web.WebFragmentDirections
import com.example.listadetareas.fragments.web.details.MovieDetailsFragmentDirections
import com.example.listadetareas.network.models.MostPopularResult
import com.example.listadetareas.utils.Contants

class AdapterMostPopular: RecyclerView.Adapter<AdapterMostPopular.MyViewHolder>() {

    var dataList = emptyList<MostPopularResult>()
        get() = field
        set(value) {
            val toDoDiffUtil = ToDoDiffUtilMovie(field, value)
            val toDoDiffResult = DiffUtil.calculateDiff(toDoDiffUtil)
            field = value
            toDoDiffResult.dispatchUpdatesTo(this)
            //notifyDataSetChanged()
        }

    fun setData(toDoData: List<MostPopularResult>){
        val toDoDiffUtil = ToDoDiffUtilMovie(dataList, toDoData)
        val toDoDiffResult = DiffUtil.calculateDiff(toDoDiffUtil)
        this.dataList = toDoData
        toDoDiffResult.dispatchUpdatesTo(this)
        //notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val binding = MoviesLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        with(holder){
            with(dataList[position]){

                binding.nombreTv.text = title
                binding.descripcionTv.text = overview

                Glide.with(binding.imageIv)
                    .load("${Contants.BASE_URL_IMAGES}${poster_path}")
                    .dontAnimate()
                    .error(R.drawable.baseline_image_24)
                    .centerCrop()
                    .into(binding.imageIv)

                holder.itemView.setOnClickListener { view ->
                    val action = WebFragmentDirections.actionWebFragmentToDetailsFragment(dataList[position])
                    view.findNavController().navigate(action)
                }

            }
        }

    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    class MyViewHolder(val binding: MoviesLayoutBinding) :RecyclerView.ViewHolder(binding.root)

}