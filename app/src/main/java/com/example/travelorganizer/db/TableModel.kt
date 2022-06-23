package com.example.travelorganizer.db

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase

abstract class TableModel(val db: SQLiteDatabase, val name: String) {
    abstract fun create()

    fun insert(values: ContentValues) =
        db.insert(name, null, values)

    fun update(values: ContentValues, whereClause: String, whereArgs: Array<String>) =
        db.update(name, values, whereClause, whereArgs)

    fun delete(whereClause: String, whereArgs: Array<String>) =
        db.delete(name, whereClause, whereArgs)

    open  fun query(
        columns: Array<String>?= null,
        selection: String? = null,
        selectionArgs: Array<String>? = null,
        groupBy: String? = null,
        having: String? = null,
        orderBy: String? = null,
        limit: String? = null) =
        db.query(name, columns, selection, selectionArgs, groupBy, having, orderBy, limit)

}