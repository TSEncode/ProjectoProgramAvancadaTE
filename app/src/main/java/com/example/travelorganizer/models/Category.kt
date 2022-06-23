package com.example.travelorganizer.models

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.provider.BaseColumns
import android.widget.Toast
import com.example.travelorganizer.db.CategoriesTable
import com.example.travelorganizer.db.DbOpenHelper
import java.lang.Exception


data class Category(
    var name: String? = null,
    var categoryId : Long?= null,
    var id: Long = -1
){

    fun toContentValues(): ContentValues {
        val values = ContentValues()

        values.put(CategoriesTable.FIELD_NAME, name)

        return values
    }


    companion object{
        fun fromCursor(cursor: Cursor) : Category{
            val posID = cursor.getColumnIndexOrThrow(BaseColumns._ID)
            val posName = cursor.getColumnIndexOrThrow(CategoriesTable.FIELD_NAME)
            val posCategoryId = cursor.getColumnIndexOrThrow(CategoriesTable.FIELD_CATEGORY_ID)

            val id = cursor.getLong(posID)
            val name = cursor.getString(posName)
            val categoryId = cursor.getLong(posCategoryId)

            return  Category(name,categoryId,id)
        }
    }
}