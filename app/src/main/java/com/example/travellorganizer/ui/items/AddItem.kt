package com.example.travellorganizer.ui.items

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.travellorganizer.R
import com.example.travellorganizer.db.DbOpenHelper
import com.example.travellorganizer.db.ItemsTable
import com.example.travellorganizer.models.Items
import java.lang.Exception

class AddItem : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)


    }

    private fun insertItems(name : String, quantity: Int, categoryId: Long?){
        val items = Items(name,quantity, categoryId)

        val helper = DbOpenHelper(this)
        val db = helper.writableDatabase

        try {
            ItemsTable(db).insert(items.toContentValues())
        }catch (e: Exception){
                Toast.makeText(this, e.localizedMessage, Toast.LENGTH_SHORT).show()
        }

        db.close()
    }
}