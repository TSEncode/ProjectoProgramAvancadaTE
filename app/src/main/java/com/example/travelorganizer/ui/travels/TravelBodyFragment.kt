package com.example.travelorganizer.ui.travels

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
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

    private  var travelAdapter : TravelAdapter? = null
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
            val travel = TravelBodyFragmentArgs.fromBundle(arguments!!).travel

            binding.textViewName.text = travel.name
            binding.textViewBudgetValue.text = "${travel.budget}€"
            binding.textViewLocalValue.text = travel.local
            binding.textViewDateValue.text = travel.date

            val travelRelationCursor = requireActivity().contentResolver.query(
                TravelContentProvider.LIST_TRAVEL_URL,
                ListTravelTable.ALL_FIELDS,
                "${ListTravelTable.FIELD_TRAVEL_ID} = ?",
                arrayOf("${travel.id}"),
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
        activity.idMenuTop = R.menu.top_nav_list_menu


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}