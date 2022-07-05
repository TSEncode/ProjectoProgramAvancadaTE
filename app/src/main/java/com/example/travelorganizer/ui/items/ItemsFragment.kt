package com.example.travelorganizer.ui.items

import android.app.AlertDialog
import android.content.DialogInterface
import android.database.Cursor
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.travelorganizer.MainActivity
import com.example.travelorganizer.R
import com.example.travelorganizer.databinding.FragmentItemsBinding
import com.example.travelorganizer.db.ItemsTable
import com.example.travelorganizer.db.ListItemsTable
import com.example.travelorganizer.db.TravelContentProvider
import com.example.travelorganizer.models.Items

import com.example.travelorganizer.ui.items.adapters.ItemsAdapter



class ItemsFragment : Fragment(), LoaderManager.LoaderCallbacks<Cursor> {

    private var _binding: FragmentItemsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private  var itemAdapter : ItemsAdapter? = null

    var items : Items? = null
        get() = field
        set(value) {
            if (value != field) {
                field = value
                (requireActivity() as MainActivity).changeMenuOps(field != null)
            }
        }

    var ids : ArrayList<Long?> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val itemsViewModel =
            ViewModelProvider(this).get(ItemsViewModel::class.java)

        _binding = FragmentItemsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        LoaderManager.getInstance(this).initLoader(ID_LOADER_ITEMS, null, this)

        val recycler = binding.itemsReciclerView

        itemAdapter = ItemsAdapter(this)

        recycler.adapter = itemAdapter
        recycler.layoutManager = LinearLayoutManager(requireContext())

        val activity = activity as MainActivity
        activity.fragment = this
        activity.idMenuTop = R.menu.top_nav_list_menu

        if(ids.count() > 0){
            activity.changeMenuOps(true)
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //Cria-se as rotas para os butoes do menu do topo
    // gere-se através do id do item que é clicado
    fun handlerOptionProcessed(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.addButton -> {
                val action =ItemsFragmentDirections.actionNavigationItemToNavigationAddItemsFragment()
                findNavController().navigate(action)
                return true
            }
            R.id.editButton -> {
                val action = ItemsFragmentDirections.actionNavigationItemToEditItemFragment(items!!.id)
                findNavController().navigate(action)
                return  true
            }
            R.id.deleteButton -> {
                deleteDialog()
                return true
            }
            else -> false
        }
    }

    companion object{
        const val ID_LOADER_ITEMS = 0
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> =
        //Vai-se buscar todos os items através do content provider e lemos o cursor com a função CursorLoader
        CursorLoader(
            requireContext(),
            TravelContentProvider.ITEM_URL,
            ItemsTable.ALL_FIELDS,
            null,
            null,
           ItemsTable.FIELD_NAME
        )

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        itemAdapter!!.cursor = data
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        itemAdapter!!.cursor = null
    }

    private fun deleteDialog(){
        val dialogDelete = AlertDialog.Builder(requireContext())

        dialogDelete.setTitle(getString(R.string.delete_item))
        dialogDelete.setMessage(getString(R.string.delte_item_msg))
        dialogDelete.setNegativeButton(android.R.string.cancel, DialogInterface.OnClickListener{ dialog, wich -> })
        dialogDelete.setPositiveButton(R.string.delete, DialogInterface.OnClickListener{ dialog, wich -> deleteItem() })
        dialogDelete.show()

    }

    //Função que elimina um item e a sua relação
    private fun deleteItem(){
        val uriItem = Uri.withAppendedPath(TravelContentProvider.ITEM_URL, "${items?.id}")

        val deletedRelated = requireActivity().contentResolver.delete(
            TravelContentProvider.LIST_ITEM_URL,
            "${ListItemsTable.FIELD_ITEMS_ID} = ? ",
            arrayOf("${items?.id}")
        )

        val deletedItem = requireActivity().contentResolver.delete(
            uriItem,
            null,null
        )

        if(deletedRelated == 1){

            if(deletedItem == 1 ){
                Toast.makeText(requireContext(), "Items deleted Successfully!", Toast.LENGTH_LONG).show()

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
                uriItem.toString(),
                Toast.LENGTH_LONG
            ).show()
        }
    }
}