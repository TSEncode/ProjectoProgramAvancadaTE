package com.example.travelorganizer.ui.items

import android.database.Cursor
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.travelorganizer.MainActivity
import com.example.travelorganizer.R
import com.example.travelorganizer.databinding.FragmentItemsBinding
import com.example.travelorganizer.db.CategoriesTable
import com.example.travelorganizer.db.ItemsTable
import com.example.travelorganizer.db.TravelContentProvider
import com.example.travelorganizer.models.Items
import com.example.travelorganizer.ui.items.adapters.ItemsAdapter


class ItemsFragment : Fragment(), LoaderManager.LoaderCallbacks<Cursor> {

    private var _binding: FragmentItemsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

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

        val activity = activity as MainActivity
        activity.fragment = this
        activity.idMenuTop = R.menu.top_nav_list_menu

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
        TODO("Not yet implemented")
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        TODO("Not yet implemented")
    }
}