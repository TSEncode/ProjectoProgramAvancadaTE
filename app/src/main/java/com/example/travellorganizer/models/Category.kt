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

     fun insertCategory(name : String): Boolean{
        //usamos a nossa class Items para criar o contentValues
        val categories = Category(context,name)
        // instanciamos o helper para gerirmos a base de dados
        val helper = DbOpenHelper(context)
        //vamos buscar a base de dados no modo de escrita
        val db = helper.writableDatabase
        /**
         *  Experimenta-se se dá para inserir os valores na base de dados, se não der
         *  aciona-se uma depuração de erro a ser lançado num toast
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

    //Função que retorna todos os nomes das categorias
     fun getCategoriesNames(): ArrayList<String>{

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
    fun getCategoryId(name: String): Long{

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