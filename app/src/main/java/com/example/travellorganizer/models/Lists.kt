package com.example.travellorganizer.models

import android.content.ContentValues
import android.content.Context
import android.widget.Toast
import com.example.travellorganizer.db.CategoriesTable
import com.example.travellorganizer.db.DbOpenHelper
import com.example.travellorganizer.db.ListTable
import java.lang.Exception

data class Lists(var context: Context,
                 var name: String? = null,
                 var descrip: String? = null,
                 var status : Boolean = true,
                 var id: Long = -1){

    fun toContentValues(): ContentValues {
        val values = ContentValues()

        values.put(CategoriesTable.FIELD_NAME, name)

        return values
    }

    fun insertList(): Boolean{
        //usamos a nossa class Items para criar o contentValues
        val lists = Lists(context,name, descrip, status)
        // instanciamos o helper para gerirmos a base de dados
        val helper = DbOpenHelper(context)
        //vamos buscar a base de dados no modo de escrita
        val db = helper.writableDatabase
        /**
         *  Experimenta-se se dá para inserir os valores na base de dados, se não der
         *  aciona-se uma depuração de erro a ser lançado num toast
         */
        try {
            ListTable(db).insert(lists.toContentValues())
            db.close()
            return true
        }catch (e: Exception){
            Toast.makeText(context, e.localizedMessage, Toast.LENGTH_SHORT).show()
            return false
        }
    }
}
