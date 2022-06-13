package com.example.travellorganizer.db

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

//Classe que cria a tabela Lista herda a class TableModel
class CategoriesTable(db : SQLiteDatabase) : TableModel (db, NAME) {

    //função que cria a tabela
    override fun create(){
        db.execSQL("CREATE TABLE $name " +
                "(${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$FIELD_NAME TEXT NOT NULL," +
                "$FIELD_CATEGORY_ID INTEGER," +
                "FOREIGN KEY($FIELD_CATEGORY_ID) REFERENCES $name(${BaseColumns._ID}) " +
                ")")
    }
    //Nomes dos campos e da tabela
    companion object {
        const val NAME = "categories"
        const val FIELD_NAME = "name"
        const val FIELD_CATEGORY_ID = "category_id"
    }
}

