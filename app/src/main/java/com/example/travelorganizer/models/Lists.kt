package com.example.travelorganizer.models

import android.content.ContentValues
import android.content.Context
import android.widget.Toast
import com.example.travelorganizer.db.DbOpenHelper
import com.example.travelorganizer.db.ListTable
import java.lang.Exception

data class Lists(
    var context: Context,
    var name: String? = null,
    var descrip: String? = null,
    var status: Int = 1,
    var id: Long = -1){

    fun toContentValues(): ContentValues {
        val values = ContentValues()

        values.put(ListTable.FIELD_NAME, name)
        values.put(ListTable.FIELD_DESCRIPTION, descrip)
        values.put(ListTable.FIELD_STATUS, status)

        return values
    }

    fun insertList(): Boolean{
        //usamos a nossa class Items para criar o contentValues
        // instanciamos o helper para gerirmos a base de dados
        val helper = DbOpenHelper(context)
        //vamos buscar a base de dados no modo de escrita
        val db = helper.writableDatabase
        /**
         *  Experimenta-se se dá para inserir os valores na base de dados, se não der
         *  aciona-se uma depuração de erro a ser lançado num toast
         */
        try {
            ListTable(db).insert(toContentValues())
            db.close()
            return true
        }catch (e: Exception){
            Toast.makeText(context, e.localizedMessage, Toast.LENGTH_SHORT).show()
            return false
        }
    }

    fun getAll(): ArrayList<Lists>{

        val lists = ArrayList<Lists>()
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
            val result = ListTable(db).query()

            //interamos o resultado, cada vez que o cursor andar adicionamos o resultado do cursor
            while (result.moveToNext()){
                //adiciona ao arraylist o objecto devidamente instaciado
                lists.add(
                    Lists(
                        context,
                        result.getString(result.getColumnIndexOrThrow(ListTable.FIELD_NAME)),
                        result.getString(result.getColumnIndexOrThrow(ListTable.FIELD_DESCRIPTION)),
                        result.getInt(result.getColumnIndexOrThrow(ListTable.FIELD_STATUS)),
                        result.getLong(result.getColumnIndexOrThrow("_id"))
                    )
                )
            }

        }catch (e: Exception){
            Toast.makeText(context, e.localizedMessage, Toast.LENGTH_SHORT).show()
        }

        return lists
    }
}
