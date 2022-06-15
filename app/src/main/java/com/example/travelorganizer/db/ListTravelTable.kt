package com.example.travelorganizer.db

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class ListTravelTable (db: SQLiteDatabase) : TableModel(db, NAME){

    override fun create() {
        db.execSQL("CREATE TABLE $name " +
                "(${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$FIELD_LIST_ID INTEGER NOT NULL," +
                "$FIELD_TRAVEL_ID INTEGER NOT NULL," +
                "FOREIGN KEY($FIELD_LIST_ID) REFERENCES $TABLE_LIST_REFERENCE(${BaseColumns._ID}) " +
                "FOREIGN KEY($FIELD_TRAVEL_ID) REFERENCES $TABLE_TRAVEL_REFERENCE(${BaseColumns._ID}) " +
                ")")
    }

    companion object{
        const val NAME = "list_travel"
        const val FIELD_LIST_ID = "list_id"
        const val FIELD_TRAVEL_ID = "travel_id"
        const val TABLE_LIST_REFERENCE = "list"
        const val TABLE_TRAVEL_REFERENCE = "travel"
    }

}