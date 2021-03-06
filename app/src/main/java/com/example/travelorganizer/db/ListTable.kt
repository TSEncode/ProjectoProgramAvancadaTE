package com.example.travelorganizer.db

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteQueryBuilder
import android.provider.BaseColumns

//Classe que cria a tabela Lista herda a class TableModel
class ListTable(db: SQLiteDatabase) : TableModel(db, NAME) {

    //função que cria a tabela
    override fun create() {
        db.execSQL("CREATE TABLE $name " +
                "(${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$FIELD_NAME TEXT NOT NULL," +
                "$FIELD_DESCRIPTION TEXT," +
                "$FIELD_STATUS BOOL NOT NULL" +
                ")")
    }




    //Nomes dos campos e da tabela
    companion object {
        const val NAME = "list"
        const val FIELD_ID = "$NAME.${BaseColumns._ID}"
        const val FIELD_NAME = "list_name"
        const val FIELD_DESCRIPTION = "list_description"
        const val FIELD_STATUS = "list_status"

        val ALL_FIELDS = arrayOf(
            FIELD_ID,
            FIELD_NAME,
            FIELD_DESCRIPTION,
            FIELD_STATUS
        )
    }
}