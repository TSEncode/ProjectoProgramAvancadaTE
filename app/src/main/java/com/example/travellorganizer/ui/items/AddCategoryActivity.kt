package com.example.travellorganizer.ui.items

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import com.example.travellorganizer.R
import com.example.travellorganizer.db.CategoriesTable
import com.example.travellorganizer.db.DbOpenHelper
import com.example.travellorganizer.db.ItemsTable
import com.example.travellorganizer.models.Category
import com.example.travellorganizer.models.Items
import java.lang.Exception

class AddCategoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_category)

        //buscamos o botão para adicionar uma nova categoria
        val addCategory = findViewById<Button>(R.id.addCategoryButton)

        addCategory.setOnClickListener{
            val categoryNameEditText = findViewById<EditText>(R.id.categoryNameValue)

            val categoryName = categoryNameEditText.text.toString()

            insertCategory(categoryName)

            categoryNameEditText.setText("")
        }
    }

    private fun insertCategory(name : String): Boolean{
        //usamos a nossa class Items para criar o contentValues
        val categories = Category(name)
        // instanciamos o helper para gerirmos a base de dados
        val helper = DbOpenHelper(this)
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
            Toast.makeText(this, e.localizedMessage, Toast.LENGTH_SHORT).show()
            return false
        }
    }

}