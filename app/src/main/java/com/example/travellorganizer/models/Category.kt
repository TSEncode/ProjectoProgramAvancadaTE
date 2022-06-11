package com.example.travellorganizer.models

import android.content.ContentValues
import android.content.Context
import android.widget.Toast
import com.example.travellorganizer.db.CategoriesTable
import com.example.travellorganizer.db.DbOpenHelper
import java.lang.Exception


data class Category(val context: Context, var name: String? = null, var categoryId : Long?= null,  var id: Long = -1){

    fun toContentValues(): ContentValues {
        val values = ContentValues()

        values.put(CategoriesTable.FIELD_NAME, name)

        return values
    }

    fun getAll(): ArrayList<Category>{

        val categoryList = ArrayList<Category>()
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
            val result = CategoriesTable(db).query()

            //interamos o resultado, cada vez que o cursor andar adicionamos o resultado do cursor
            while (result.moveToNext()){
                //adiciona ao arraylist o objecto devidamente instaciado
                categoryList.add(
                    Category(
                        context,
                        result.getString(result.getColumnIndexOrThrow(CategoriesTable.FIELD_NAME)),
                        result.getLong(result.getColumnIndexOrThrow(CategoriesTable.FIELD_CATEGORY_ID)),
                        result.getLong(result.getColumnIndexOrThrow("_id"))
                    )
                )
            }
        }catch (e: Exception){
            Toast.makeText(context, e.localizedMessage, Toast.LENGTH_SHORT).show()
        }

        return categoryList
    }
}