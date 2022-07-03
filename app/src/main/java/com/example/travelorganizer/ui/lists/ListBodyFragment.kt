package com.example.travelorganizer.ui.lists

import android.content.ContentResolver
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.travelorganizer.MainActivity
import com.example.travelorganizer.R

import com.example.travelorganizer.databinding.FragmentListBodyBinding
import com.example.travelorganizer.db.ItemsTable
import com.example.travelorganizer.db.ListItemsTable
import com.example.travelorganizer.db.ListTable
import com.example.travelorganizer.db.TravelContentProvider
import com.example.travelorganizer.models.Items
import com.example.travelorganizer.models.ListItems
import com.example.travelorganizer.models.Lists
import com.example.travelorganizer.ui.lists.adapters.ListBodyAdapter


/**
 * A simple [Fragment] subclass.
 * Use the [ListBodyFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListBodyFragment : Fragment(), LoaderManager.LoaderCallbacks<Cursor> {
    private var _binding: FragmentListBodyBinding? = null
    private var id : Long? = null
    private var checkboxCursor : Cursor? = null


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var listName : String? = null

    var listBodyAdapter : ListBodyAdapter? = null

    private var checkbox : CheckBox? = null

    //variável que guarda os ids a actualizar, usa-se um mutableset pois não podem exisitir id repetidos
    var ids : MutableSet<Long?>? = mutableSetOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val listsViewModel =
            ViewModelProvider(this).get(ListsViewModel::class.java)

        _binding = FragmentListBodyBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        LoaderManager.getInstance(this).initLoader(ID_LOADER_ITEMS, null, this)
        LoaderManager.getInstance(this).initLoader(ID_LOADER_ITEMS, null, this)
        checkbox  =  view.findViewById(R.id.itemCardCheckBox)
        LoaderManager.getInstance(this).getLoader<Cursor>(ID_LOADER_LIST_ITEMS)!!.deliverResult(checkboxCursor)
        var listItems = ListItems.fromCursor(checkboxCursor!!)
        while (checkboxCursor!!.moveToNext()){
                listItems = ListItems.fromCursor(checkboxCursor!!)
                if(listItems.status == 1 ){
                    checkbox!!.isChecked = true
                }
        }

        if(arguments != null){

            id = ListBodyFragmentArgs.fromBundle(arguments!!).listId
            listName = ListBodyFragmentArgs.fromBundle(arguments!!).listName

            val recycler = binding.bodyListRecycler

            listBodyAdapter = ListBodyAdapter(this)

            recycler.adapter = listBodyAdapter
            recycler.layoutManager = LinearLayoutManager(requireContext())

            val activity = activity as MainActivity

            activity.fragment = this
            activity.idMenuTop = R.menu.top_nav_body_list


        }

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun handlerOptionProcessed(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.addButton ->{
                val action = ListBodyFragmentDirections.actionNavigationListBodyFragmentToItemToListFragment(id!!)
                findNavController().navigate(action)
                return true
            }
            R.id.closeButton -> {
                findNavController().navigate(ListBodyFragmentDirections.actionNavigationListBodyFragmentToNavigationList(-1))
                return true
            }
            else -> false
        }
    }


    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        var cursor : CursorLoader? = null
        if(id == ID_LOADER_ITEMS ){
              cursor = CursorLoader(
                requireContext(),
                TravelContentProvider.ITEM_URL,
                ItemsTable.ALL_FIELDS,
                "${ListItemsTable.FIELD_LIST_ID} = ?",
                arrayOf("${ListBodyFragmentArgs.fromBundle(arguments!!).listId}"),
                null
            )

        }else if (id == ID_LOADER_LIST_ITEMS){
            cursor = CursorLoader(
                requireContext(),
                TravelContentProvider.LIST_ITEM_URL,
                ItemsTable.ALL_FIELDS,
                null,
                null,
                null
            )
        }

        return  cursor!!
    }


    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        listBodyAdapter!!.cursor = data
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
       listBodyAdapter!!.cursor = null
    }
    fun updateCheckedItem(item : Items?, status : Int){
        val uriItem = Uri.withAppendedPath(TravelContentProvider.LIST_ITEM_URL, item!!.listItem?.id.toString())

        val listItems = ListItems(
            item.listItem!!.itemId,
            item.listItem!!.listId,
            status
        )


        val updated = requireActivity().contentResolver.update(
            uriItem,
            listItems.toContentValues(),
            null,
            null
        )

        if(updated == 1){
            Toast.makeText(requireContext(), getString(R.string.item_update), Toast.LENGTH_LONG).show()
            //vai-se buscar o ultimo caminho do uri gerado, este é o id

        }else{
            Toast.makeText(requireContext(), getString(R.string.error_item), Toast.LENGTH_LONG).show()
        }

    }

    companion object{
        const val ID_LOADER_ITEMS = 0
        const val ID_LOADER_LIST_ITEMS = 1
    }


}