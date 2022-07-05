package com.example.travelorganizer.ui.lists

import android.database.Cursor
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.travelorganizer.MainActivity
import com.example.travelorganizer.R
import com.example.travelorganizer.databinding.FragmentListsBinding
import com.example.travelorganizer.db.CategoriesTable
import com.example.travelorganizer.db.ListTable
import com.example.travelorganizer.db.TravelContentProvider
import com.example.travelorganizer.models.Lists
import com.example.travelorganizer.ui.items.ItemsFragmentDirections
import com.example.travelorganizer.ui.lists.adapters.ListAdapter

class ListsFragment : Fragment(), LoaderManager.LoaderCallbacks<Cursor> {

    private var _binding: FragmentListsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    var checkedList : Lists? = null
        get() = field
        set(value) {
            if( value != field){
                field = value
                (requireActivity() as MainActivity).changeMenuOps(field != null)
            }
        }



    private var listAdapter : ListAdapter? = null
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

        LoaderManager.getInstance(this).initLoader(ID_LOADER_LIST, null, this)

        val recycler = binding.listRecylerView


        listAdapter = ListAdapter(this)

        recycler.adapter = listAdapter
        recycler.layoutManager = LinearLayoutManager(requireContext())

        val activity = activity as MainActivity

        activity.fragment = this
        activity.idMenuTop = R.menu.top_nav_list_menu

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun handlerOptionProcessed(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.addButton -> {
                val action = ListsFragmentDirections.actionNavigationListToCreateListFragment()
                findNavController().navigate(action)
                return true
            }
            else -> false
        }
    }


    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> =
        CursorLoader(
            requireContext(),
            TravelContentProvider.LIST_URL,
            ListTable.ALL_FIELDS,
            null,
            null,
            ListTable.FIELD_NAME
        )

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        listAdapter!!.cursor = data
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        listAdapter!!.cursor = null
    }

    companion object{
        const val ID_LOADER_LIST = 0
    }

}