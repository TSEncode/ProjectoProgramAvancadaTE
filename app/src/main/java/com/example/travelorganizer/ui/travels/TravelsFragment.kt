package com.example.travelorganizer.ui.travels

import android.database.Cursor
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.travelorganizer.MainActivity
import com.example.travelorganizer.R
import com.example.travelorganizer.databinding.FragmentTravelsBinding
import com.example.travelorganizer.db.TravelContentProvider
import com.example.travelorganizer.db.TravelsTable
import com.example.travelorganizer.ui.lists.ListBodyFragment
import com.example.travelorganizer.ui.lists.adapters.ListAdapter
import com.example.travelorganizer.ui.travels.adapters.TravelAdapter

class TravelsFragment : Fragment() , LoaderManager.LoaderCallbacks<Cursor>  {

    private var _binding: FragmentTravelsBinding? = null

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

        LoaderManager.getInstance(this).initLoader(ID_LOADER_TRAVEL, null, this)

        _binding = FragmentTravelsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        travelAdapter = TravelAdapter(this)

        val  travelRecycler = binding.travelRecycler
        travelRecycler.adapter = travelAdapter
        travelRecycler.layoutManager = LinearLayoutManager(requireContext())

        val activity = activity as MainActivity

        activity.fragment = this
        activity.idMenuTop = R.menu.top_nav_list_menu


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun handlerOptionProcessed(item: MenuItem): Boolean {
        return when( item.itemId){
            R.id.addButton -> {
                findNavController().navigate(TravelsFragmentDirections.actionNavigationTravelToAddTravelFragment())
                return  true
            }
            else -> false
        }

    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> =
        CursorLoader(
            requireContext(),
            TravelContentProvider.TRAVEL_URL,
            TravelsTable.ALL_FIELDS,
            null,
            null,
            null,
        )


    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
       travelAdapter!!.cursor = data
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        travelAdapter!!.cursor = null
    }

    companion object{
        const val ID_LOADER_TRAVEL = 0
    }
}