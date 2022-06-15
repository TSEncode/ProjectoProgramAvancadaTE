package com.example.travelorganizer.db

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

//Classe que cria a tabela Lista herda a class TableModel
class TravelsTable(db: SQLiteDatabase) : TableModel(db, NAME) {

    //função que cria a tabela
    override fun create() {
        db.execSQL("CREATE TABLE $name " +
                "(${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$FIELD_NAME TEXT NOT NULL," +
                "$FIELD_BUDGET FLOAT NOT NULL," +
                "$FIELD_REAL_BUDGET FLOAT," +
                "$FIELD_LOCAL TEXT NOT NULL," +
                "$FIELD_DATE DATE NOT NULL" +
                ")")
    }

    //Nomes dos campos e da tabela
    companion object{
        const val NAME = "travel"
        const val FIELD_NAME = "name"
        const val FIELD_BUDGET = "budget"
        const val FIELD_REAL_BUDGET = "real_budget"
        const val FIELD_LOCAL = "local"
        const val FIELD_DATE = "travel_date"

    }
}