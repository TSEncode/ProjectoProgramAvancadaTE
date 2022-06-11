package com.example.travellorganizer.models

import android.content.ContentValues
import android.content.Context
import android.widget.Toast
import com.example.travellorganizer.db.CategoriesTable
import com.example.travellorganizer.db.DbOpenHelper
import com.example.travellorganizer.db.ItemsTable
import java.lang.Exception

data class Items(var context: Context, var name : String? = null, var categoryId : Long?= null, var id: Long = -1){

    //Função que cria um set com os valores a inserir na tabela
    fun toContentValues(): ContentValues{
        val values = ContentValues()

        values.put(ItemsTable.FIELD_NAME, name)
        values.put(ItemsTable.FIELD_CATEGORY_ID, categoryId)

        return values
    }

    //função que insere o novo item na bd
    fun insertItems(): Boolean{
        
        // instanciamos o helper para gerirmos a base de dados
        val helper = DbOpenHelper(context)
        //vamos buscar a base de dados no modo de escrita
        val db = helper.writableDatabase

        /**
         *  Experimenta-se se dá para inserir os valores na base de dados, se não der
         *  aciona-se uma depuração de erro no terminal
         */
        try {
            ItemsTable(db).insert(toContentValues())
            db.close()
            return true
        }catch (e: Exception){
            Toast.makeText(context, e.localizedMessage, Toast.LENGTH_SHORT).show()
            return false
        }

    }

    //função que devolve todos os resultados em formato objecto é retornado um arraylist
    fun getAll(): ArrayList<Items>{

         val itemsList = ArrayList<Items>()
        // instanciamos o helper para gerirmos a base de dados
        val helper = DbOpenHelper(context)
        //vamos buscar a base de dados no modo de escrita
        val db = helper.readableDatabase

        /**
         *  Experimenta-se se dá para irmos buscar os valores na base de dados, se não der
         *  aciona-se uma depuração de erro no terminal
         */
        try {

            //vamos buscar todos os registos da coluna, ele vai ser desenvolvido num objecto do tipo Cursor
            val result = ItemsTable(db).query()

            //interamos o resultado, cada vez que o cursor andar adicionamos o resultado do cursor
            while (result.moveToNext()){
                //adiciona ao arraylist o objecto devidamente instaciado
                itemsList.add(
                    Items(
                        context,
                        result.getString(result.getColumnIndexOrThrow(ItemsTable.FIELD_NAME)),
                        result.getLong(result.getColumnIndexOrThrow(ItemsTable.FIELD_CATEGORY_ID)),
                        result.getLong(result.getColumnIndexOrThrow("_id"))
                    )
                )
            }
        }catch (e: Exception){
            Toast.makeText(context, e.localizedMessage, Toast.LENGTH_SHORT).show()
        }

        return itemsList
    }

}
