package com.example.travellorganizer.ui.items

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.travellorganizer.R
import com.example.travellorganizer.db.DbOpenHelper
import com.example.travellorganizer.db.ItemsTable
import com.example.travellorganizer.models.Items
import java.lang.Exception

class AddItemActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)

        //guardamos o botão que adiciona o item
        val addButton = findViewById<Button>(R.id.addItemButton)
        spinnerDefault()

        //no evente de click pasamos o valor do edit text para ser guardado, utiliza-se a função insertItems para inserir os valores
        addButton.setOnClickListener {
            //guardamos o conteudo do editText
            val itemNameText = findViewById<EditText>(R.id.categoryNameValue)
            val itemName = itemNameText.text.toString()
            //inserimos os valores na bd
            val isInserted = insertItems(itemName, null)

            if(isInserted){
                itemNameText.setText("")
            }
        }

        val addCategories: ImageButton = findViewById(R.id.toAddCategoryButton)

        addCategories.setOnClickListener {
            val intent = Intent(this, AddCategoryActivity::class.java)
            startActivity(intent)
        }

    }
    //função que insere o novo item na bd
    private fun insertItems(name : String, categoryId: Long?): Boolean{

        //usamos a nossa class Items para criar o contentValues
        val items = Items(name, categoryId)
        // instanciamos o helper para gerirmos a base de dados
        val helper = DbOpenHelper(this)
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
                Toast.makeText(this, e.localizedMessage, Toast.LENGTH_SHORT).show()
            return false
        }

    }

    private fun spinnerDefault(){
        val spinner = findViewById<Spinner>(R.id.categoriesSpinner)

        val valuesArray = ArrayList<String>()

        if(valuesArray.count() == 0){
            valuesArray.add(getString(R.string.no_categories_spinner))
        }


        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, valuesArray)

        spinner.adapter = arrayAdapter

    }
}