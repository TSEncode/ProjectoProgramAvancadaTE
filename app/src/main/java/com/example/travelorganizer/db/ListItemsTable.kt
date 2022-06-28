package com.example.travelorganizer.db

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class ListItemsTable(db: SQLiteDatabase) : TableModel(db, NAME) {

    //função que cria a tabela
    override fun create() {
        db.execSQL("CREATE TABLE $name " +
                "(${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$FIELD_LIST_ID INTEGER NOT NULL," +
                "$FIELD_ITEMS_ID INTEGER NOT NULL," +
                "$FIELD_QUANTITY INTEGER," +
                "$FIELD_STATUS BOOL NOT NULL," +
                "FOREIGN KEY($FIELD_LIST_ID) REFERENCES $TABLE_LIST_REFERENCE(${BaseColumns._ID}) " +
                "FOREIGN KEY($FIELD_ITEMS_ID) REFERENCES $TABLE_ITEMS_REFERENCE(${BaseColumns._ID}) " +
                ")")
    }

    //Nomes dos campos e da tabela
    companion object{
        const val NAME = "list_items"
        const val FIELD_ID = "$NAME.${BaseColumns._ID}"
        const val FIELD_LIST_ID = "list_id"
        const val FIELD_ITEMS_ID = "items_id"
        const val FIELD_QUANTITY = "quantity"
        const val FIELD_STATUS = "status"
        const val TABLE_LIST_REFERENCE = "list"
        const val TABLE_ITEMS_REFERENCE = "items"

        val ALL_FIELDS = arrayOf(
            FIELD_ID,
            FIELD_LIST_ID,
            FIELD_ITEMS_ID,
            FIELD_QUANTITY
        )
    }
}