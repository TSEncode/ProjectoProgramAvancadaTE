package com.example.travellorganizer.ui.items

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.travellorganizer.MainActivity
import com.example.travellorganizer.R
import com.example.travellorganizer.databinding.FragmentAddItemBinding
import com.example.travellorganizer.db.CategoriesTable
import com.example.travellorganizer.db.DbOpenHelper
import com.example.travellorganizer.db.ItemsTable
import com.example.travellorganizer.models.Category
import com.example.travellorganizer.models.Items
import java.lang.Exception

/**
 * A simple [Fragment] subclass.
 * Use the [AddItemsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddItemsFragment : Fragment() {
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

        //no evento de click pasamos o valor do edit text para ser guardado, utiliza-se a função insertItems para inserir os valores
        addButton.setOnClickListener {
            //guardamos o conteudo do editText
            val itemNameText = binding.itemNameValue
            val itemName = itemNameText.text.toString()
            //inserimos os valores na bd
            val isInserted = insertItems(itemName, null)

            if(isInserted){
                itemNameText.setText("")
            }
        }

        val addCategories: ImageButton = binding.toAddCategoryButton

        addCategories.setOnClickListener {
            findNavController().navigate(R.id.navigation_addCategoryFragment)
        }

        return root
    }
    //função que insere o novo item na bd
    private fun insertItems(name : String, categoryId: Long?): Boolean{

        //usamos a nossa class Items para criar o contentValues
        val items = Items(name, categoryId)
        // instanciamos o helper para gerirmos a base de dados
        val helper = DbOpenHelper(context)
        //vamos buscar a base de dados no modo de escrita
        val db = helper.writableDatabase
        /**
         *  Experimenta-se se dá para inserir os valores na base de dados, se não der
         *  aciona-se uma depuração de erro no terminal
         */
        try {
            ItemsTable(db).insert(items.toContentValues())
            db.close()
            return true
        }catch (e: Exception){
            Toast.makeText(context, e.localizedMessage, Toast.LENGTH_SHORT).show()
            return false
        }

    }

    private fun spinnerDefault(){
        val spinner = binding.categoriesSpinner

        val valuesArray = ArrayList<String>()
        val categoriesNames = getCategoriesNames()


        if(valuesArray.count() == 0){
            valuesArray.add(getString(R.string.no_categories_spinner))
        }

        val arrayAdapter = ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_item, valuesArray)

        spinner.adapter = arrayAdapter

    }

    //Função que retorna todos os nomes das categorias
    private fun getCategoriesNames(): Cursor?{
        var result : Cursor?
        // instanciamos o helper para gerirmos a base de dados
        val helper = DbOpenHelper(context)
        //vamos buscar a base de dados no modo de escrita
        val db = helper.readableDatabase
        /**
         *  Experimenta-se se dá para inserir os valores na base de dados, se não der
         *  aciona-se uma depuração de erro no terminal
         */
        try {

            val columns = arrayOf(CategoriesTable.NAME)


            result = CategoriesTable(db).query(columns)

        }catch (e: Exception){
            Toast.makeText(context, e.localizedMessage, Toast.LENGTH_SHORT).show()
            result = null
        }

        return result
    }


}