package com.example.travelorganizer.ui.lists

import android.app.AlertDialog
import android.content.ContentResolver
import android.content.DialogInterface
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
import com.example.travelorganizer.db.*
import com.example.travelorganizer.models.Items
import com.example.travelorganizer.models.ListItems
import com.example.travelorganizer.models.Lists
import com.example.travelorganizer.ui.lists.adapters.ListBodyAdapter
import com.example.travelorganizer.ui.travels.TravelBodyFragmentDirections


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


        if(arguments != null){

            id = ListBodyFragmentArgs.fromBundle(arguments!!).listId
            listName = ListBodyFragmentArgs.fromBundle(arguments!!).listName

        }

        LoaderManager.getInstance(this).initLoader(ID_LOADER_ITEMS, null, this)

        val recycler = binding.bodyListRecycler

        listBodyAdapter = ListBodyAdapter(this)

        recycler.adapter = listBodyAdapter
        recycler.layoutManager = LinearLayoutManager(requireContext())

        val activity = activity as MainActivity

        activity.fragment = this
        activity.idMenuTop = R.menu.top_nav_body_list
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

            R.id.deleteButton -> {
                deleteDialog()
                return true
            }
            else -> false
        }
    }


    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> =
        CursorLoader(
            requireContext(),
            TravelContentProvider.LIST_GET_URL,
            ItemsTable.ALL_FIELDS_RELATED,
            "${ListItemsTable.FIELD_LIST_ID} = ?",
            arrayOf("${this.id}"),
            null
        )


    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        listBodyAdapter!!.cursor = data

    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
       listBodyAdapter!!.cursor = null
    }
    //função que altera o estado do item na lista, se ja foi escolhido ou não
    fun updateCheckedItem(listItem : ListItems?, status : Int){
        val uriItem = Uri.withAppendedPath(TravelContentProvider.LIST_ITEM_URL, listItem?.id.toString())

        val listItems = ListItems(
            listItem!!.listId,
            listItem.itemId,
            status,
            null,
            listItem.id
        )

        val updated = requireActivity().contentResolver.update(
            uriItem,
            listItems.toContentValues(),
            null,
            null
        )

        if(updated == 1){
            Toast.makeText(requireContext(), R.string.item_update, Toast.LENGTH_LONG).show()
            //vai-se buscar o ultimo caminho do uri gerado, este é o id

        }else{
            Toast.makeText(requireContext(), getString(R.string.error_item), Toast.LENGTH_LONG).show()
        }

    }

    private fun deleteDialog(){
        val dialogDelete = AlertDialog.Builder(requireContext())

        dialogDelete.setTitle(getString(R.string.list_Delete))
        dialogDelete.setMessage(getString(R.string.list_delete_ms))
        dialogDelete.setNegativeButton(android.R.string.cancel, DialogInterface.OnClickListener{ dialog, wich -> })
        dialogDelete.setPositiveButton(R.string.delete, DialogInterface.OnClickListener{ dialog, wich -> deleteList() })
        dialogDelete.show()

    }

    //Função que elimina uma lista e a sua relação
    private fun deleteList() {
        val uriList = Uri.withAppendedPath(TravelContentProvider.TRAVEL_URL, "$id")


        val deletedRelatedListTravel = requireActivity().contentResolver.delete(
            TravelContentProvider.LIST_TRAVEL_URL,
            "${ListTravelTable.FIELD_LIST_ID} = ? ",
            arrayOf("$id")
        )


        val deletedRelatedListItem = requireActivity().contentResolver.delete(
            TravelContentProvider.LIST_ITEM_URL,
            "${ListItemsTable.FIELD_LIST_ID} = ? ",
            arrayOf("$id")
        )
        if (deletedRelatedListTravel == 1 && deletedRelatedListItem == 1) {

            val deletedItem = requireActivity().contentResolver.delete(
                uriList,
                null, null
            )
            if (deletedItem == 1) {
                Toast.makeText(requireContext(), "Items deleted Successfully!", Toast.LENGTH_LONG)
                    .show()
                findNavController().navigate(ListBodyFragmentDirections.actionNavigationListBodyFragmentToNavigationList(-1))
            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.error_delete_item),
                    Toast.LENGTH_LONG
                ).show()
            }
        } else {
            Toast.makeText(
                requireContext(),
                deletedRelatedListItem.toString(),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    companion object{
        const val ID_LOADER_ITEMS = 0

    }


}