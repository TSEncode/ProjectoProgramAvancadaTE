package com.example.travelorganizer.models

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import com.example.travelorganizer.db.ItemsTable
import com.example.travelorganizer.db.ListItemsTable

data class ListItems(
    var listId : Long,
    var itemId : Long,
    var status : Int = 0,
    var quantity : Int? = null,
    var id : Long = -1
    ){

    //Função que cria um set com os valores a inserir na tabela
    fun toContentValues(): ContentValues {
        val values = ContentValues()

        values.put(ListItemsTable.FIELD_LIST_ID, listId)
        values.put(ListItemsTable.FIELD_ITEMS_ID, itemId)
        values.put(ListItemsTable.FIELD_QUANTITY, quantity)
        values.put(ListItemsTable.FIELD_STATUS, status)

        return values
    }


    companion object{
        fun fromCursor(cursor: Cursor) : ListItems{
            val posID = cursor.getColumnIndexOrThrow(ListItemsTable.FIELD_ID)
            val posListId = cursor.getColumnIndexOrThrow(ListItemsTable.FIELD_LIST_ID)
            val posItemId = cursor.getColumnIndexOrThrow(ListItemsTable.FIELD_ITEMS_ID)
            val posQuantity = cursor.getColumnIndexOrThrow(ListItemsTable.FIELD_QUANTITY)
            val posStatus = cursor.getColumnIndexOrThrow(ListItemsTable.FIELD_STATUS)


            val id = cursor.getLong(posID)
            val listId = cursor.getLong(posListId)
            val itemId = cursor.getLong(posItemId)
            val quantity = cursor.getInt(posQuantity)
            val status  = cursor.getInt(posStatus)


            return  ListItems(listId,itemId,quantity, status, id)
        }
    }
}
