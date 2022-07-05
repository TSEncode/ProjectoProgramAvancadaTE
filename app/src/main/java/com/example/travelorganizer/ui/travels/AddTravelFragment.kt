package com.example.travelorganizer.ui.travels

import android.database.Cursor
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.travelorganizer.MainActivity
import com.example.travelorganizer.R
import com.example.travelorganizer.databinding.FragmentListsBinding
import com.example.travelorganizer.db.TravelContentProvider
import com.example.travelorganizer.db.TravelsTable
import com.example.travelorganizer.models.Lists
import com.example.travelorganizer.ui.lists.ListsFragment
import com.example.travelorganizer.ui.lists.ListsViewModel
import com.example.travelorganizer.ui.lists.adapters.ListAdapter
import com.example.travelorganizer.ui.travels.adapters.TravelAdapter

class AddTravelFragment : Fragment(), LoaderManager.LoaderCallbacks<Cursor> {
    private var _binding: FragmentListsBinding? = null

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
            ViewModelProvider(this).get(ListsViewModel::class.java)

        _binding = FragmentListsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        LoaderManager.getInstance(this).initLoader(ListsFragment.ID_LOADER_LIST, null, this)

        val recycler = binding.listRecylerView


        //travelsAdapter = TravelAdapter(this)

        recycler.adapter = travelsAdapter
        recycler.layoutManager = LinearLayoutManager(requireContext())

        val activity = activity as MainActivity

        activity.fragment = this
        activity.idMenuTop = R.menu.top_nav_list_menu

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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

    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        //TODO("Not yet implemented")
    }
}