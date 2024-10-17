package com.example.listadetareas.fragments.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.listadetareas.R
import com.example.listadetareas.adapter.ListAdapter
import com.example.listadetareas.data.models.Prioridad
import com.example.listadetareas.data.models.Tarea
import com.example.listadetareas.databinding.FragmentListBinding
import com.example.listadetareas.viewmodel.TareasViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment : Fragment(), View.OnClickListener, MenuProvider {

    private val mToDoViewModel: TareasViewModel by viewModels()

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private val adapter: ListAdapter by lazy { ListAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        mToDoViewModel.getAllData.observe(viewLifecycleOwner, Observer { data ->

            if(data.isNullOrEmpty()) {

                binding.noDataImageView.visibility = View.VISIBLE
                binding.noDataTextView.visibility = View.VISIBLE

                adapter.dataList = emptyList()

            }else{

                binding.noDataImageView.visibility = View.GONE
                binding.noDataTextView.visibility = View.GONE

                adapter.dataList = data

            }

            //binding.recyclerView.scheduleLayoutAnimation()

        })

        //insertTarea()
    }


    private fun insertTarea() {


        val newData: Tarea = Tarea(
            0,
            "Hola",
            Prioridad.ALTA,
            "Hola"
        )

        mToDoViewModel.insert(newData)

    }

    override fun onClick(v: View?) {

        when (v?.id) {

            /*R.id.floatingActionButton -> {

                findNavController().navigate(R.id.action_listFragment_to_addFragment)

            }*/

        }

    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.list_fragment_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {

            R.id.menu_web -> {

                findNavController().navigate(R.id.action_listFragment_to_webFragment)

                true
            }

            else -> false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}