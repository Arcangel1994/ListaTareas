package com.example.listadetareas.fragments.add

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.example.listadetareas.R
import com.example.listadetareas.data.models.Prioridad
import com.example.listadetareas.data.models.Tarea
import com.example.listadetareas.databinding.FragmentAddBinding
import com.example.listadetareas.fragments.SharedViewModel
import com.example.listadetareas.viewmodel.TareasViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddFragment : Fragment(), MenuProvider, View.OnClickListener, TextWatcher {

    private val mToDoViewModel: TareasViewModel by viewModels()
    private val msharedViewModel: SharedViewModel by viewModels()

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val priorities = resources.getStringArray(R.array.priorities)
        val arrayAdapter = ArrayAdapter(requireContext(), com.google.android.material.R.layout.support_simple_spinner_dropdown_item,priorities)
        binding.prioritiesSpinner.setAdapter(arrayAdapter)

        //binding.prioritiesSpinner.onItemSelectedListener = msharedViewModel.listener

        binding.agregarMb.setOnClickListener(this)

        binding.titleEt.addTextChangedListener(this)
        binding.descriptionEt.addTextChangedListener(this)

    }

    override fun onClick(v: View?) {

        when (v?.id) {

            R.id.agregar_mb -> {

                insertDataToDb()

            }

        }

    }

    val listener: AdapterView.OnItemSelectedListener = object :
        AdapterView.OnItemSelectedListener {

        override fun onNothingSelected(parent: AdapterView<*>?) {}

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

            when(position){

                0 -> { (parent?.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(requireContext(), R.color.red)) }

                1 -> { (parent?.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(requireContext(), R.color.yellow)) }

                2 -> { (parent?.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(requireContext(), R.color.green)) }

            }

        }

    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.add_fragment_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {

            R.id.menu_add -> {

                insertDataToDb()

                true
            }

            else -> false
        }
    }

    private fun insertDataToDb() {

        val mTitle = binding.titleEt.text.toString()
        val mPriority = binding.prioritiesSpinner.text.toString()
        val mDescription = binding.descriptionEt.text.toString()

        val validation = msharedViewModel.verifyDataFromUser(mTitle, mDescription)

        if (validation){
            //Insert Data
            val newData: Tarea = Tarea(
                0,
                mTitle,
                /*parsePriority(mPriority), */ msharedViewModel.parsePriority(mPriority),
                mDescription
            )

            mToDoViewModel.insert(newData)
            Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_SHORT).show()

            //Navigation Back
            findNavController().popBackStack()

        }else{

            binding.titleIl.error = getString(R.string.error_title)
            binding.descriptionIl.error = getString(R.string.error_descripcion)

            Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_SHORT).show()

        }

    }

    fun verifyDataFromUser(title: String, description: String): Boolean{
        return !(title.isEmpty() || description.isEmpty())
    }

    fun parsePriority(priority: String): Prioridad {
        return when(priority){
            "Prioridad Alta" -> {
                Prioridad.ALTA
            }
            "Prioridad Media" -> {
                Prioridad.MEDIANA
            }
            "Prioridad Baja" -> {
                Prioridad.BAJA
            }
            else ->  Prioridad.BAJA
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

    override fun afterTextChanged(p0: Editable?) {}

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        if(binding.titleIl.isErrorEnabled){
            binding.titleIl.isErrorEnabled = false;
        }

        if(binding.descriptionIl.isErrorEnabled){
            binding.descriptionIl.isErrorEnabled = false;
        }


    }

}