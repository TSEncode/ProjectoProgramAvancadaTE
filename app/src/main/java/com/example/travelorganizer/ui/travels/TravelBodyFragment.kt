package com.example.travelorganizer.ui.travels

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.loader.app.LoaderManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.travelorganizer.MainActivity
import com.example.travelorganizer.R
import com.example.travelorganizer.databinding.FragmentTravelBodyBinding
import com.example.travelorganizer.databinding.FragmentTravelsBinding
import com.example.travelorganizer.ui.travels.adapters.TravelAdapter

class TravelBodyFragment : Fragment() {
    private var _binding: FragmentTravelBodyBinding? = null

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