package com.example.travelorganizer.models

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.provider.BaseColumns
import android.widget.Toast
import com.example.travelorganizer.db.CategoriesTable
import com.example.travelorganizer.db.DbOpenHelper
import com.example.travelorganizer.db.ItemsTable
import com.example.travelorganizer.db.ListTable
import java.lang.Exception

data class Items(
    var name : String? = null,
    var categoryId : Long?= null,
    var id: Long = -1){

    //Função que cria um set com os valores a inserir na tabela
    fun toContentValues(): ContentValues{
        val values = ContentValues()

        values.put(ItemsTable.FIELD_NAME, name)
        values.put(ItemsTable.FIELD_CATEGORY_ID, categoryId)

        return values
    }


    companion object{
        fun fromCursor(cursor: Cursor) : Items{
            val posID = cursor.getColumnIndexOrThrow(BaseColumns._ID)
            val posName = cursor.getColumnIndexOrThrow(ItemsTable.FIELD_NAME)
            val posCategoryId = cursor.getColumnIndexOrThrow(ItemsTable.FIELD_CATEGORY_ID)


            val id = cursor.getLong(posID)
            val name = cursor.getString(posName)
            val categoryId = cursor.getLong(posCategoryId)


            return  Items(name,categoryId,id)
        }
    }

}
