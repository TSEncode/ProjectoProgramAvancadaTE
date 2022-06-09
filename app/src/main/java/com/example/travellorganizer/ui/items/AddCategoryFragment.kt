package com.example.travellorganizer.ui.items

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.travellorganizer.R
import com.example.travellorganizer.databinding.FragmentAddCategoryBinding
import com.example.travellorganizer.db.CategoriesTable
import com.example.travellorganizer.db.DbOpenHelper
import com.example.travellorganizer.models.Category
import java.lang.Exception
import java.util.*

/**
 * A simple [Fragment] subclass.
 * Use the [AddCategoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddCategoryFragment : Fragment() {
    private var _binding: FragmentAddCategoryBinding? = null

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

        _binding = FragmentAddCategoryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val addCategory =  binding.addCategoryButton

        addCategory.setOnClickListener{
            val categoryNameEditText = binding.categoryNameValue

            val categoryName = categoryNameEditText.text.toString()

            insertCategory(categoryName)

            categoryNameEditText.setText("")
        }

        return root
    }

    private fun insertCategory(name : String): Boolean{
        //usamos a nossa class Items para criar o contentValues
        val categories = Category(name)
        // instanciamos o helper para gerirmos a base de dados
        val helper = DbOpenHelper(context)
        //vamos buscar a base de dados no modo de escrita
        val db = helper.writableDatabase
        /**
         *  Experimenta-se se dá para inserir os valores na base de dados, se não der
         *  aciona-se uma depuração de erro no terminal
         */
        try {
            CategoriesTable(db).insert(categories.toContentValues())
            db.close()
            return true
        }catch (e: Exception){
            Toast.makeText(context, e.localizedMessage, Toast.LENGTH_SHORT).show()
            return false
        }
    }
}