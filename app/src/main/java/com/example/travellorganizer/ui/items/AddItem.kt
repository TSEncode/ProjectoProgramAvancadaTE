package com.example.travellorganizer.ui.items

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.travellorganizer.R
import com.example.travellorganizer.db.DbOpenHelper
import com.example.travellorganizer.db.ItemsTable
import com.example.travellorganizer.models.Items
import org.w3c.dom.Text
import java.lang.Exception

class AddItem : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)

        //guardamos o botão que adiciona o item
        val addButton = findViewById<Button>(R.id.addItem)
        //no evente de click pasamos o valor do edit text para ser guardado, utiliza-se a função insertItems para inserir os valores
        addButton.setOnClickListener {
            //guardamos o conteudo do editText
            val itemName = findViewById<EditText>(R.id.itemEditText).text.toString()
            //inserimos os valores na bd
            insertItems(itemName, null)

        }

    }
    //função que inser o novo item na bd
    private fun insertItems(name : String, categoryId: Long?){

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
        }catch (e: Exception){
                Toast.makeText(this, e.localizedMessage, Toast.LENGTH_SHORT).show()
        }

        db.close()
    }
}