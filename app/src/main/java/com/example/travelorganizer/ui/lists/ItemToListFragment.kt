package com.example.travelorganizer.ui.lists

import android.database.Cursor
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.travelorganizer.MainActivity
import com.example.travelorganizer.R
import com.example.travelorganizer.databinding.FragmentItemsBinding
import com.example.travelorganizer.databinding.FragmentListsBinding
import com.example.travelorganizer.db.ItemsTable
import com.example.travelorganizer.db.ListTable
import com.example.travelorganizer.db.TravelContentProvider
import com.example.travelorganizer.models.Lists
import com.example.travelorganizer.ui.items.ItemsFragmentDirections
import com.example.travelorganizer.ui.items.ItemsViewModel
import com.example.travelorganizer.ui.items.adapters.ItemsAdapter
import com.example.travelorganizer.ui.lists.adapters.ItemToListAdapter
import com.example.travelorganizer.ui.lists.adapters.ListAdapter


// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [ItemToListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ItemToListFragment : Fragment(), LoaderManager.LoaderCallbacks<Cursor> {
    private var _binding: FragmentItemsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private  var itemToListAdapter : ItemToListAdapter? = null

    //variavél que guarda os ids selecionados
    var ids : ArrayList<Long?>? = ArrayList()

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

        itemToListAdapter = ItemToListAdapter(this)

        recycler.adapter = itemToListAdapter
        recycler.layoutManager = LinearLayoutManager(requireContext())

        val activity = activity as MainActivity
        activity.fragment = this
        activity.idMenuTop = R.menu.top_nav_save

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //Cria-se as rotas para os butoes do menu do topo
    // gere-se através do id do item que é clicado
    fun handlerOptionProcessed(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.saveButton -> {
                Toast.makeText(requireContext(), ids.toString(), Toast.LENGTH_LONG).show()
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
            null
        )


    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        itemToListAdapter!!.cursor = data
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        itemToListAdapter!!.cursor = null
    }
}