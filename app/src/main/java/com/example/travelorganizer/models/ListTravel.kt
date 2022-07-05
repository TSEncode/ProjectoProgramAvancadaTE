package com.example.travelorganizer.models

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import com.example.travelorganizer.db.ListItemsTable
import com.example.travelorganizer.db.ListTravelTable

data class ListTravel(
    var travelId : Long,
    var listId : Long,

    var id : Long = -1

){
    fun toContentValues(): ContentValues {
        val values = ContentValues()

        values.put(ListTravelTable.FIELD_LIST_ID, listId)
        values.put(ListTravelTable.FIELD_TRAVEL_ID, travelId)

        return values
    }

    companion object{
        fun fromCursor(cursor: Cursor) : ListTravel{
            val posID = cursor.getColumnIndexOrThrow(ListTravelTable.FIELD_ID)
            val posTravelId = cursor.getColumnIndexOrThrow(ListTravelTable.FIELD_TRAVEL_ID)
            val posListId = cursor.getColumnIndexOrThrow(ListTravelTable.FIELD_LIST_ID)

            val id = cursor.getLong(posID)
            val travelId = cursor.getLong(posTravelId)
            val listId = cursor.getLong(posListId)

            return  ListTravel(travelId, listId, id)
        }
    }
}
