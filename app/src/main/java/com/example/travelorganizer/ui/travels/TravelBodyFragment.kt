package com.example.travelorganizer.ui.travels

import android.app.AlertDialog
import android.content.DialogInterface
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.loader.app.LoaderManager
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.travelorganizer.MainActivity
import com.example.travelorganizer.R
import com.example.travelorganizer.databinding.FragmentTravelBodyBinding
import com.example.travelorganizer.databinding.FragmentTravelsBinding
import com.example.travelorganizer.db.ListItemsTable
import com.example.travelorganizer.db.ListTravelTable
import com.example.travelorganizer.db.TravelContentProvider
import com.example.travelorganizer.models.ListTravel
import com.example.travelorganizer.models.Travel
import com.example.travelorganizer.ui.travels.adapters.TravelAdapter

class TravelBodyFragment : Fragment() {
    private var _binding: FragmentTravelBodyBinding? = null

    private var travelRelation : ListTravel? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var travel : Travel? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(TravelsViewModel::class.java)

        _binding = FragmentTravelBodyBinding.inflate(inflater, container, false)
        val root: View = binding.root


        if(arguments != null){
            travel = TravelBodyFragmentArgs.fromBundle(arguments!!).travel

            binding.textViewName.text = travel!!.name
            binding.textViewBudgetValue.text = "${travel!!.budget}€"
            binding.textViewLocalValue.text = travel!!.local
            binding.textViewDateValue.text = travel!!.date

            val travelRelationCursor = requireActivity().contentResolver.query(
                TravelContentProvider.LIST_TRAVEL_URL,
                ListTravelTable.ALL_FIELDS,
                "${ListTravelTable.FIELD_TRAVEL_ID} = ?",
                arrayOf("${travel!!.id}"),
                null
            )

            while (travelRelationCursor!!.moveToNext()){

                if(travelRelationCursor.isFirst){
                    //só foi implementada uma lista para já usa-se este método para salvaguarda o primeiro resultado
                    travelRelation = ListTravel.fromCursor(travelRelationCursor)
                }else{
                    break
                }
            }

            binding.goList.setOnClickListener{
                val action = TravelBodyFragmentDirections.actionTravelBodyFragmentToNavigationListBodyFragment(travelRelation!!.listId, null)
                findNavController().navigate(action)
            }



        }


        val activity = activity as MainActivity

        activity.fragment = this
        activity.idMenuTop = R.menu.top_nav_travel_body


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun handlerOptionProcessed(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.deleteButton -> {
                deleteDialog()
                return true
            }
            else -> false
        }
    }

    private fun deleteDialog(){
        val dialogDelete = AlertDialog.Builder(requireContext())

        dialogDelete.setTitle(getString(R.string.travel_delete))
        dialogDelete.setMessage(getString(R.string.travel_delete_msg))
        dialogDelete.setNegativeButton(android.R.string.cancel, DialogInterface.OnClickListener{ dialog, wich -> })
        dialogDelete.setPositiveButton(R.string.delete, DialogInterface.OnClickListener{ dialog, wich -> deleteTravel() })
        dialogDelete.show()

    }

    //Função que elimina um item e a sua relação
    private fun deleteTravel(){
        val uriTravel = Uri.withAppendedPath(TravelContentProvider.TRAVEL_URL, "${travel?.id}")


        val deletedRelated = requireActivity().contentResolver.delete(
            TravelContentProvider.LIST_TRAVEL_URL,
            "${ListTravelTable.FIELD_TRAVEL_ID} = ? ",
            arrayOf("${travel?.id}")
        )


        if(deletedRelated == 1){

            val deletedItem = requireActivity().contentResolver.delete(
                uriTravel,
                null,null
            )
            if(deletedItem == 1 ){
                Toast.makeText(requireContext(), "Items deleted Successfully!", Toast.LENGTH_LONG).show()
                findNavController().navigate(TravelBodyFragmentDirections.actionTravelBodyFragmentToNavigationTravel())
            }else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.error_delete_item),
                    Toast.LENGTH_LONG
                ).show()
            }
        }else {
            Toast.makeText(
                requireContext(),
                uriTravel.toString(),
                Toast.LENGTH_LONG
            ).show()
        }
    }
}