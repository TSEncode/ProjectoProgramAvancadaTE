package com.example.travelorganizer.models

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.provider.BaseColumns
import android.widget.Toast
import com.example.travelorganizer.db.*
import java.lang.Exception

data class Items(
    var name : String? = null,
    var categoryId : Long?= null,
    var listItem : ListItems? = null,
    var id: Long = -1){

    //Função que cria um set com os valores a inserir na tabela
    fun toContentValues(): ContentValues{
        val values = ContentValues()

        values.put(ItemsTable.FIELD_NAME, name)
        values.put(ItemsTable.FIELD_CATEGORY_ID, categoryId)

        return values
    }


    companion object{

        fun fromCursor(cursor: Cursor) : Items {
            val posID = cursor.getColumnIndexOrThrow(ItemsTable.FIELD_ID)
            val posName = cursor.getColumnIndexOrThrow(ItemsTable.FIELD_NAME)
            val posCategoryId = cursor.getColumnIndexOrThrow(ItemsTable.FIELD_CATEGORY_ID)

            val id = cursor.getLong(posID)
            val name = cursor.getString(posName)
            val categoryId = cursor.getLong(posCategoryId)


            return Items(name, categoryId, null, id)
        }

        fun fromCursorRelated(cursor: Cursor) : Items{
            val posID = cursor.getColumnIndexOrThrow(ItemsTable.FIELD_ID)
            val posName = cursor.getColumnIndexOrThrow(ItemsTable.FIELD_NAME)
            val posCategoryId = cursor.getColumnIndexOrThrow(ItemsTable.FIELD_CATEGORY_ID)
            val posListItemId = cursor.getColumnIndexOrThrow(ListItemsTable.FIELD_ID)
            val posListItem = cursor.getColumnIndexOrThrow(ListItemsTable.FIELD_ITEMS_ID)
            val posListItemList = cursor.getColumnIndexOrThrow(ListItemsTable.FIELD_LIST_ID)
            val posListItemStatus = cursor.getColumnIndexOrThrow(ListItemsTable.FIELD_STATUS)
            val posListItemQuantity = cursor.getColumnIndexOrThrow(ListItemsTable.FIELD_QUANTITY)


            val id = cursor.getLong(posID)
            val name = cursor.getString(posName)
            val categoryId = cursor.getLong(posCategoryId)

            val listItem = ListItems(
                cursor.getLong(posListItemList),
                cursor.getLong(posListItem),
                cursor.getInt(posListItemStatus),
                cursor.getInt(posListItemQuantity),
                cursor.getLong(posListItemId),

            )

            return  Items(name,categoryId,listItem, id)
        }
    }

}
