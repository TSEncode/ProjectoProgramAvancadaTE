package com.example.travellorganizer.ui.items

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ItemsViewModel : ViewModel() {

    //k
    private val _text = MutableLiveData<String>().apply {
        value = "This is Items Fragment"
    }
    val text: LiveData<String> = _text
}