package com.example.travellorganizer.ui.items

import android.database.Cursor
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.travellorganizer.db.CategoriesTable
import com.example.travellorganizer.db.DbOpenHelper
import com.example.travellorganizer.models.Category
import java.lang.Exception

class ItemsViewModel : ViewModel() {


    private val _text = MutableLiveData<String>().apply {
        value = "This is Items Fragment"
    }
    val text: LiveData<String> = _text


}

