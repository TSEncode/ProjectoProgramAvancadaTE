package com.example.travelorganizer.ui.items

import android.database.Cursor
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.navigation.fragment.findNavController
import com.example.travelorganizer.MainActivity
import com.example.travelorganizer.R
import com.example.travelorganizer.databinding.FragmentAddItemBinding
import com.example.travelorganizer.db.CategoriesTable
import com.example.travelorganizer.db.TravelContentProvider
import com.example.travelorganizer.models.Category
import com.example.travelorganizer.models.Items
import com.example.travelorganizer.ui.lists.ListsFragment

/**
 * A simple [Fragment] subclass.
 * Use the [AddItemsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddItemsFragment() : Fragment(), LoaderManager.LoaderCallbacks<Cursor> {
    private var _binding: FragmentAddItemBinding? = null

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

        _binding = FragmentAddItemBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        LoaderManager.getInstance(this).initLoader(ID_LOADER_ITEMS, null, this)
        //vai-se buscar a Main Activity para alterar o menu do topo
        val activity = activity as MainActivity

        activity.fragment = this
        activity.idMenuTop = R.menu.top_nav_save

        val addCategories: ImageButton = binding.toAddCategoryButton
        addCategories.setOnClickListener {
            findNavController().navigate(R.id.navigation_addCategoryFragment)
        }
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> =
        CursorLoader(
            requireContext(),
            TravelContentProvider.CATEGORY_URL,
            CategoriesTable.ALL_FIELDS,
            null,
            null,
            CategoriesTable.FIELD_NAME
        )


    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        val categoriesAdapter = SimpleCursorAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            data,
            arrayOf(CategoriesTable.FIELD_NAME),
            intArrayOf(android.R.id.text1),
            0
        )

        binding.categoriesSpinner.adapter = categoriesAdapter
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        binding.categoriesSpinner.adapter = null
    }

    //Cria-se as rotas para os butoes do menu do topo
    // gere-se atrav??s do id do item que ?? clicado
    fun handlerOptionProcessed(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.saveButton -> {
                inserItem()
                return true
            }
            R.id.closeButton -> {
                returnToItems()
                return  true
            }
            else -> false
        }

    }

    fun returnToItems(){
        findNavController().navigate(AddItemsFragmentDirections.actionNavigationAddItemsFragmentToNavigationItem())
    }

    fun inserItem(){
        val activity = activity as MainActivity

        val name = binding.itemNameValue.text.toString()
        val categoryId = binding.categoriesSpinner.selectedItemId

        val nameValidated = activity.validateFields( name,binding.itemNameValue, getString(R.string.fill_the_name))
        val categorIdvalidated = activity.validateFields(
            isSpinner = true,
            msg = getString(R.string.choose_category),
            spinner = categoryId,
            text = binding.addCategoryItemsTextView
        )

        val item = Items(
            name,
            categoryId
        )

        if(nameValidated && categorIdvalidated){
            val url = requireActivity().contentResolver.insert(TravelContentProvider.ITEM_URL, item.toContentValues())

            if(url != null){
                Toast.makeText(requireContext(), getString(R.string.item_added), Toast.LENGTH_LONG).show()
                returnToItems()
            }else{
                Toast.makeText(requireContext(), getString(R.string.error_item), Toast.LENGTH_LONG).show()
            }
        }

    }

    companion object{
        const val ID_LOADER_ITEMS = 0;
    }
}
