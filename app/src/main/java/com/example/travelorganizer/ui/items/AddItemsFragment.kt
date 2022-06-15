package com.example.travelorganizer.ui.items

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.travelorganizer.R
import com.example.travelorganizer.databinding.FragmentAddItemBinding
import com.example.travelorganizer.models.Category
import com.example.travelorganizer.models.Items

/**
 * A simple [Fragment] subclass.
 * Use the [AddItemsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddItemsFragment() : Fragment() {
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

        val addButton = binding.addItemButton
        spinnerDefault()
        val debug = binding.addCategoryItemsTextView

        //no evento de click pasamos o valor do edit text para ser guardado, utiliza-se a função insertItems para inserir os valores
        addButton.setOnClickListener {
            //guardamos o conteudo do editText
            val itemNameText = binding.itemNameValue
            val itemName = itemNameText.text.toString()

            val categorySelected = binding.categoriesSpinner.selectedItem.toString()

            val idCategory = categoryIdFilter(categorySelected)

            val isInserted = Items(requireContext(),itemName, idCategory).insertItems()

            if(isInserted){
                Toast.makeText(context, getString(R.string.item_added), Toast.LENGTH_SHORT).show()

                itemNameText.setText("")
            }
        }

        val addCategories: ImageButton = binding.toAddCategoryButton

        addCategories.setOnClickListener {
            findNavController().navigate(R.id.navigation_addCategoryFragment)
        }

        return root
    }


    private fun spinnerDefault(){
        val spinner = binding.categoriesSpinner

        //cria-se a lista para passar para o spinner através do adapter
        val valuesArray = ArrayList<String>()
        //vamos buscar a lista com os nomes das categorias
        val categoriesNames = Category(requireContext()).getCategoriesNames()

        valuesArray.add(getString(R.string.no_categories_spinner))

        for ( name in categoriesNames){
                valuesArray.add(name)
        }

        //cria-se o adapter para o spinner, este spinner irá apresentar um dropdown com as categorias
        val arrayAdapter = ArrayAdapter(requireActivity(), R.layout.spinner_items, valuesArray)

        spinner.adapter = arrayAdapter

    }

    //Função que valida se o item tem categoria ou não

    private fun categoryIdFilter(name : String): Long? {
        //verifca-se se o valor escolhido é sem categoria, se for retorna-se nulo se não retorna-se o id
        if (name === getString(R.string.no_categories_spinner)) {
            return null
        }else{
            return Category(requireContext()).getCategoryId(name)
        }
    }

}