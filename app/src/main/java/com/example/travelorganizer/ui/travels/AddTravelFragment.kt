package com.example.travelorganizer.ui.travels

import android.database.Cursor
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.SimpleCursorAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.travelorganizer.MainActivity
import com.example.travelorganizer.R
import com.example.travelorganizer.databinding.FragmentAddTravelBinding
import com.example.travelorganizer.databinding.FragmentListsBinding
import com.example.travelorganizer.databinding.FragmentTravelsBinding
import com.example.travelorganizer.db.CategoriesTable
import com.example.travelorganizer.db.ListTable
import com.example.travelorganizer.db.TravelContentProvider
import com.example.travelorganizer.db.TravelsTable
import com.example.travelorganizer.models.ListTravel
import com.example.travelorganizer.models.Lists
import com.example.travelorganizer.models.Travel
import com.example.travelorganizer.ui.lists.ListsFragment
import com.example.travelorganizer.ui.lists.ListsViewModel
import com.example.travelorganizer.ui.lists.adapters.ListAdapter
import com.example.travelorganizer.ui.travels.adapters.TravelAdapter

class AddTravelFragment : Fragment(), LoaderManager.LoaderCallbacks<Cursor> {
    private var _binding: FragmentAddTravelBinding? = null

    var checkedList : Lists? = null
        get() = field
        set(value) {
            if( value != field){
                field = value
                (requireActivity() as MainActivity).changeMenuOps(field != null)
            }
        }
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    private var travelsAdapter : TravelAdapter?= null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(TravelsViewModel::class.java)

        _binding = FragmentAddTravelBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        LoaderManager.getInstance(this).initLoader(ID_LOADER_ADD_TRAVEL, null, this)


        val activity = activity as MainActivity

        activity.fragment = this
        activity.idMenuTop = R.menu.top_nav_save

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> =
        CursorLoader(
            requireContext(),
            TravelContentProvider.LIST_URL,
            ListTable.ALL_FIELDS,
            null,
            null,
            null
        )

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        val listAdapter = SimpleCursorAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            data,
            arrayOf(ListTable.FIELD_NAME),
            intArrayOf(android.R.id.text1),
            0
        )

        binding.travelSpinner.adapter = listAdapter
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        binding.travelSpinner.adapter = null
    }


    //função que adiciona a viagem

    fun insertTravel(){

        val activity = activity as MainActivity

        val name = binding.editTextName.text.toString()
        val budget = binding.editTextBudget.text.toString()
        val local = binding.editTextLocal.text.toString()
        val date  = binding.editTextDate.text.toString()
        val listId = binding.travelSpinner.selectedItemId

        val nameValidated = activity.validateFields(name, binding.editTextName, "Fill name field!")
        val budgetValidated =  activity.validateFields(budget, binding.editTextName, "Fill budget field!")
        val localValidated = activity.validateFields(local, binding.editTextLocal, "Fill local field!")
        val dateValidated = activity.validateFields(date, binding.editTextDate, "Fill date field!")

        val listIdValidated = activity.validateFields(
            isSpinner = true,
            msg = "Choose List",
            spinner = listId,
            text = binding.textViewList
        )

        val travel = Travel(
            name,
            budget.toFloat(),
            null,
            local,
            date
        )



        if(
            nameValidated &&
            budgetValidated &&
            localValidated &&
            dateValidated &&
            listIdValidated
        ){
            val url = requireActivity().contentResolver.insert(TravelContentProvider.TRAVEL_URL, travel.toContentValues())



            if(url != null){


                val travelRelation = ListTravel(
                    url.lastPathSegment!!.toLong(),
                    listId
                )

                val urlRelate = requireActivity().contentResolver.insert(TravelContentProvider.LIST_TRAVEL_URL, travelRelation.toContentValues() )
                if(urlRelate != null ){
                    Toast.makeText(requireContext(), "Travel added successfully", Toast.LENGTH_LONG).show()
                    returnToTravels()
                }else{
                    Toast.makeText(requireContext(), "Erro: Travel don't added", Toast.LENGTH_LONG).show()
                }
            }else{
                Toast.makeText(requireContext(), "Erro: Travel don't added", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun  returnToTravels(){
        val action = AddTravelFragmentDirections.actionAddTravelFragmentToNavigationTravel()
        findNavController().navigate(action)
    }

    fun handlerOptionProcessed(item: MenuItem): Boolean {
            return  when(item.itemId){
                R.id.saveButton -> {
                    insertTravel()
                    return true
                }
                R.id.closeButton -> {
                    returnToTravels()
                    return true
                }
                else -> false
            }
    }

    companion object{
        const val ID_LOADER_ADD_TRAVEL = 0
    }

}