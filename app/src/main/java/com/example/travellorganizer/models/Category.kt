package com.example.travellorganizer.models

import android.content.ContentValues
import com.example.travellorganizer.db.CategoriesTable


data class Category(var name: String,  var id: Long = -1){

    fun toContentValues(): ContentValues {
        val values = ContentValues()

        values.put(CategoriesTable.FIELD_NAME, name)

        return values
    }
}