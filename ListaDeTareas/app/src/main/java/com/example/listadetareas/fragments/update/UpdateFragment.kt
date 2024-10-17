package com.example.listadetareas.fragments.update

import CustomDialog
import CustomDialog.negativeButton
import CustomDialog.positiveButton
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.listadetareas.R
import com.example.listadetareas.data.models.Tarea
import com.example.listadetareas.databinding.FragmentUpdateBinding
import com.example.listadetareas.fragments.SharedViewModel
import com.example.listadetareas.utils.alert
import com.example.listadetareas.utils.negativeButton
import com.example.listadetareas.utils.positiveButton
import com.example.listadetareas.viewmodel.TareasViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpdateFragment : Fragment(), MenuProvider {

    private val args by navArgs<UpdateFragmentArgs>()

    private val mToDoViewModel: TareasViewModel by viewModels()
    private val msharedViewModel: SharedViewModel by viewModels()

    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpdateBinding.inflate(inflater, container, false)

        (requireActivity() as AppCompatActivity).supportActionBar?.title = args.currentItem.title

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.currentTitleEt.setText(args.currentItem.title)
        binding.currentPrioritiesSpinner.setSelection(msharedViewModel.parsePriorityToInt(args.currentItem.priority))
        binding.currentDescriptionEt.setText(args.currentItem.description)

        binding.currentPrioritiesSpinner.onItemSelectedListener = msharedViewModel.listener

    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.update_fragment_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {

            R.id.menu_save -> {

                updateDataToDb()

                true
            }

            R.id.menu_delete -> {

                confirmDeleteDataToDb()

                true
            }

            else -> false
        }
    }

    private fun updateDataToDb() {

        val mcTitle = binding.currentTitleEt.text.toString()
        val mcPriority = binding.currentPrioritiesSpinner.selectedItem.toString()
        val mcDescription = binding.currentDescriptionEt.text.toString()

        val validation = msharedViewModel.verifyDataFromUser(mcTitle, mcDescription)

        if (validation){
            //Update Data
            val updateData: Tarea = Tarea(
                args.currentItem.id,
                mcTitle,
                msharedViewModel.parsePriority(mcPriority),
                mcDescription
            )

            mToDoViewModel.updateData(updateData)
            Toast.makeText(requireContext(), "Successfully updated!", Toast.LENGTH_SHORT).show()

            //Navigation Back
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)

        }else{

            Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_SHORT).show()

        }

    }

    //Show Dialog
    private fun confirmDeleteDataToDb(){

        /*val builder : AlertDialog.Builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Si"){_, _ ->

            mToDoViewModel.deleteData(args.currentItem)

            Toast.makeText(requireContext(), "Successfully Delete!: ${args.currentItem.title}", Toast.LENGTH_SHORT).show()

            //Navigation Back
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)

        }
        builder.setNegativeButton("No"){_, _ ->

        }
        builder.setTitle("Eliminar '${args.currentItem.title}'?")
        builder.setMessage("Estas de seguro de eliminar '${args.currentItem.title}'?")
        builder.create().show()*/

        /*CustomDialog.alertDialog(requireContext()){
            setTitle("Eliminar ${args.currentItem.title}?")
            setMessage("Estas de seguro de eliminar ${args.currentItem.title}?")
            positiveButton(){
                mToDoViewModel.deleteData(args.currentItem)

                Toast.makeText(requireContext(), "Successfully Delete!: ${args.currentItem.title}", Toast.LENGTH_SHORT).show()

                //Navigation Back
                findNavController().popBackStack()

            }
            negativeButton(){

            }
        }.show()*/

        requireContext().alert {
            setTitle("Eliminar ${args.currentItem.title}?")
            setMessage("Estas de seguro de eliminar ${args.currentItem.title}?")
            positiveButton {
                mToDoViewModel.deleteData(args.currentItem)

                Toast.makeText(requireContext(), "Successfully Delete!: ${args.currentItem.title}", Toast.LENGTH_SHORT).show()

                //Navigation Back
                findNavController().popBackStack()
            }
            negativeButton {  }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}