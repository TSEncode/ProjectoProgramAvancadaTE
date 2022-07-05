package com.example.travelorganizer.ui.home

import android.database.Cursor
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.travelorganizer.MainActivity
import com.example.travelorganizer.R
import com.example.travelorganizer.databinding.FragmentHomeBinding
import com.example.travelorganizer.db.TravelContentProvider
import com.example.travelorganizer.db.TravelsTable
import com.example.travelorganizer.ui.home.adapter.HomeAdapter

class HomeFragment : Fragment(), LoaderManager.LoaderCallbacks<Cursor> {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private  var homeAdapter : HomeAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        LoaderManager.getInstance(this).initLoader(HOME_LOADER_ID, null, this)

        val recycler = binding.latTravelsRecycler

        homeAdapter = HomeAdapter(this)

        recycler.adapter = homeAdapter
        recycler.layoutManager = LinearLayoutManager(requireContext())

        val activity = activity as MainActivity
        activity.fragment = this
        activity.idMenuTop = R.menu.no_menu

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> =
        CursorLoader(
            requireContext(),
            TravelContentProvider.TRAVEL_LIMIT_URL,
            TravelsTable.ALL_FIELDS,
            null,
            null,
            null
        )

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        homeAdapter!!.cursor = data
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        homeAdapter!!.cursor = null
    }

    companion object{
        const val HOME_LOADER_ID = 0
    }
}