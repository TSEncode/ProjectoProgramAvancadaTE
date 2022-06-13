package com.example.travellorganizer.ui.lists

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ListsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }

    private val _id = MutableLiveData<Long>()


    val text: LiveData<String> = _text

    val id : LiveData<Long> = _id

    fun selectId(idP : Long){
        _id.value = idP
    }
}