package com.example.travelorganizer.models

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.provider.BaseColumns
import android.widget.Toast
import com.example.travelorganizer.db.CategoriesTable
import com.example.travelorganizer.db.DbOpenHelper
import com.example.travelorganizer.db.ListTable
import java.lang.Exception

data class Lists(
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

    companion object{
        fun fromCursor(cursor: Cursor) : Lists{
            val posID = cursor.getColumnIndexOrThrow(BaseColumns._ID)
            val posName = cursor.getColumnIndexOrThrow(ListTable.FIELD_NAME)
            val posDescrip = cursor.getColumnIndexOrThrow(ListTable.FIELD_DESCRIPTION)
            val posStatus = cursor.getColumnIndexOrThrow(ListTable.FIELD_STATUS)

            val id = cursor.getLong(posID)
            val name = cursor.getString(posName)
            val description = cursor.getString(posDescrip)
            val status = cursor.getInt(posStatus)

            return  Lists(name,description,status,id)
        }
    }

}
