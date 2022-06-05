package com.example.travellorganizer.models

import android.content.ContentValues
import com.example.travellorganizer.db.ItemsTable

data class Items(var name : String, var quantity : Int, var categoryId : Long?, var id: Long = -1){
    //Função que cria um set com os valores a inserir na tabela
    fun toContentValues(): ContentValues{
        val values = ContentValues()

        values.put(ItemsTable.FIELD_NAME, name)
        values.put(ItemsTable.FIELD_CATEGORY_ID, categoryId)

        return values
    }

}
