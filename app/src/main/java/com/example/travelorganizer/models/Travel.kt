package com.example.travelorganizer.models

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import com.example.travelorganizer.db.ListTable
import com.example.travelorganizer.db.TravelsTable
import java.io.Serializable

data class Travel(
    var name: String? = null,
    var budget: Float? = null,
    var realBudget : Float? = null,
    var local : String? = null,
    var date : String? = null,
    var id: Long = -1) : Serializable {

    fun toContentValues(): ContentValues {
        val values = ContentValues()

        values.put(TravelsTable.FIELD_NAME, name)
        values.put(TravelsTable.FIELD_BUDGET, budget)
        values.put(TravelsTable.FIELD_REAL_BUDGET, realBudget)
        values.put(TravelsTable.FIELD_LOCAL, local)
        values.put(TravelsTable.FIELD_DATE, date)

        return values
    }

    companion object{
        fun fromCursor(cursor: Cursor) : Travel{
            val posID = cursor.getColumnIndexOrThrow(BaseColumns._ID)
            val posName = cursor.getColumnIndexOrThrow(TravelsTable.FIELD_NAME)
            val posBudget = cursor.getColumnIndexOrThrow(TravelsTable.FIELD_BUDGET)
            val posRealBudget = cursor.getColumnIndexOrThrow(TravelsTable.FIELD_REAL_BUDGET)
            val posLocal = cursor.getColumnIndexOrThrow(TravelsTable.FIELD_LOCAL)
            val posDate = cursor.getColumnIndexOrThrow(TravelsTable.FIELD_DATE)

            val id = cursor.getLong(posID)
            val name = cursor.getString(posName)
            val budget = cursor.getFloat(posBudget)
            val realBudget = cursor.getFloat(posRealBudget)
            val local = cursor.getString(posLocal)
            val date = cursor.getString(posDate)

            return  Travel(name, budget, realBudget, local, date, id)
        }
    }
}
