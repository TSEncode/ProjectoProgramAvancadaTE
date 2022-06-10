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
        val debug = binding.addCategoryItemsTextView

        //no evento de click pasamos o valor do edit text para ser guardado, utiliza-se a função insertItems para inserir os valores
        addButton.setOnClickListener {
            //guardamos o conteudo do editText
            val itemNameText = binding.itemNameValue
            val itemName = itemNameText.text.toString()

            val categorySelected = binding.categoriesSpinner.selectedItem.toString()

            val idCategory = categoryIdFilter(categorySelected)

           val isInserted = insertItems(itemName, idCategory)

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

        //cria-se a lista para passar para o spinner através do adapter
        val valuesArray = ArrayList<String>()
        //vamos buscar a lista com os nomes das categorias
        val categoriesNames = getCategoriesNames()

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
                return getCategoryId(name)
            }
        }

    //Função que retorna todos os nomes das categorias
    private fun getCategoriesNames(): ArrayList<String>{

        val categoriesNameList =  ArrayList<String>()
        // instanciamos o helper para gerirmos a base de dados
        val helper = DbOpenHelper(context)
        //vamos buscar a base de dados no modo de escrita
        val db = helper.readableDatabase
        /**
         *  Experimenta-se se dá para inserir os valores na base de dados, se não der
         *  aciona-se uma depuração de erro no terminal
         */
        try {
            //passamos o nome da coluna para um array, neste caso queremos a coluna nome
            val columns = arrayOf(CategoriesTable.FIELD_NAME)
            //vamos buscar todos os registos da coluna, ele vai ser desenvolvido num objecto do tipo Cursor
            val result = CategoriesTable(db).query(columns)

            //interamos o resultado, cada vez que o cursor andar adicionamos o resultado do cursor
            while (result.moveToNext()){
                   categoriesNameList.add(result.getString(result.getColumnIndexOrThrow(CategoriesTable.FIELD_NAME)))
               }
        }catch (e: Exception){
            Toast.makeText(context, e.localizedMessage, Toast.LENGTH_SHORT).show()
        }

        return categoriesNameList
    }

    //Função que devolve o id da categoria escolhida
    private fun getCategoryId(name: String): Long{

        // instanciamos o helper para gerirmos a base de dados
        val helper = DbOpenHelper(context)
        //vamos buscar a base de dados no modo de escrita
        val db = helper.readableDatabase
        /**
         *  Experimenta-se se dá para inserir os valores na base de dados, se não der
         *  aciona-se uma depuração de erro no terminal
         */
        try {
            //passamos o nome da coluna que se pretende ir buscar
            val columns = arrayOf("_id")
            val selectionArgs = arrayOf(name)
            val result = CategoriesTable(db).query(columns, "${CategoriesTable.FIELD_NAME} LIKE ?",selectionArgs )

            //retorna-se o valor do id
            while (result.moveToNext()){
                return result.getLong(result.getColumnIndexOrThrow("_id"))
            }

        }catch (e: Exception){
            Toast.makeText(context, e.localizedMessage, Toast.LENGTH_SHORT).show()
        }

        return 0
    }


}