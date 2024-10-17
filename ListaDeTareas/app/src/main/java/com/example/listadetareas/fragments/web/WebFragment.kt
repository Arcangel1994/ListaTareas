package com.example.listadetareas.fragments.web

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.listadetareas.R
import com.example.listadetareas.adapter.AdapterMostPopular
import com.example.listadetareas.databinding.FragmentWebBinding
import com.example.listadetareas.utils.NetworkResult
import com.example.listadetareas.utils.alertLoading
import com.example.listadetareas.viewmodel.TheMovieDBViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WebFragment : Fragment() {

    private var _binding: FragmentWebBinding? = null
    private val binding get() = _binding!!

    private val theMovieDBViewModel: TheMovieDBViewModel by viewModels()

    private val adapter: AdapterMostPopular  by lazy { AdapterMostPopular() }

    lateinit var alertDialogLoading: AlertDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentWebBinding.inflate(inflater, container, false)
        alertDialogLoading = requireContext().alertLoading()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerViewMostPopular.adapter = adapter
        binding.recyclerViewMostPopular.layoutManager = LinearLayoutManager(requireContext())

        theMovieDBViewModel.getMostPopular()

        theMovieDBViewModel.mostPopular.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    response.data?.let{
                        if(it.isNotEmpty()){
                            adapter.dataList = it
                        }else{
                            adapter.dataList = emptyList()
                            Toast.makeText(requireContext(), getString(R.string.movie_empty), Toast.LENGTH_LONG).show()
                        }
                    }
                    alertDialogLoading.dismiss()
                }
                is NetworkResult.Error -> {
                    Toast.makeText(requireContext(), response.message, Toast.LENGTH_LONG).show()
                    alertDialogLoading.dismiss()
                }
                is NetworkResult.Loading -> alertDialogLoading.show()
            }
        }

    }

}