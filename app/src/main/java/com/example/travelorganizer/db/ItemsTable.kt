package com.example.travelorganizer.db

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteQueryBuilder
import android.provider.BaseColumns

//Classe que cria a tabela Lista herda a class TableModel
class ItemsTable(db : SQLiteDatabase) : TableModel (db, NAME){

    //função que cria a tabela
    override fun create(){
        db.execSQL("CREATE TABLE $name " +
                "(${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$FIELD_NAME TEXT NOT NULL," +
                "$FIELD_CATEGORY_ID INTEGER," +
                "FOREIGN KEY($FIELD_CATEGORY_ID) REFERENCES $TABLE_REFERENCE(${BaseColumns._ID}) ON DELETE RESTRICT " +
                ")")
    }

    //query de relação
      fun queryItemList(
        columns: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        groupBy: String?,
        having: String?,
        orderBy: String?,
        limit: String? )
    : Cursor {
        val queryBuilder = SQLiteQueryBuilder()
        queryBuilder.tables =  "$NAME LEFT JOIN ${ListItemsTable.NAME} ON ${ListItemsTable.FIELD_ITEMS_ID} = $FIELD_ID "

        return queryBuilder.query(db, columns, selection, selectionArgs, groupBy, having, orderBy,limit)
    }


    //Nomes dos campos e da tabela
    companion object {
        const val NAME = "items"
        const val FIELD_ID = "$NAME.${BaseColumns._ID}"
        const val FIELD_NAME = "items_name"
        const val FIELD_CATEGORY_ID = "items_category_id"
        const val TABLE_REFERENCE = "categories"
        val LIST = ListItemsTable

        val ALL_FIELDS = arrayOf(
            FIELD_ID,
            FIELD_NAME,
            FIELD_CATEGORY_ID,
        )


        //campos a ir buscar quando é necessário relação
        val ALL_FIELDS_RELATED = arrayOf(
            FIELD_ID,
            FIELD_NAME,
            FIELD_CATEGORY_ID,
            LIST.FIELD_ID,
            LIST.FIELD_LIST_ID,
            LIST.FIELD_ITEMS_ID,
            LIST.FIELD_STATUS,
            LIST.FIELD_QUANTITY
        )

    }
}