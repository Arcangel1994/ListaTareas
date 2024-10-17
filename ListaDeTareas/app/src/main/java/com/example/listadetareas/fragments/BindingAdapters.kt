package com.example.listadetareas.fragments

import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import com.example.listadetareas.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class BindingAdapters {

    companion object{

        @BindingAdapter("android:navigateToAddFragment")
        @JvmStatic
        fun navigateToAddFragment(view: FloatingActionButton, navigate: Boolean){
            view.setOnClickListener {
                if(navigate){
                    view.findNavController().navigate(R.id.action_listFragment_to_addFragment)
                }
            }
        }

        @BindingAdapter("android:navigateToLoginFragment")
        @JvmStatic
        fun navigateToLoginFragment(view: FloatingActionButton, navigate: Boolean){
            view.setOnClickListener {
                if(navigate){
                    view.findNavController().navigate(R.id.action_listFragment_to_loginActivity)
                }
            }
        }

    }

}