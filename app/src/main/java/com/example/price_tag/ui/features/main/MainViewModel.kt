package com.example.price_tag.ui.features.main

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel : ViewModel() {

    private val _selectedMainBottomBarIndex = MutableStateFlow<Int>(0)
    val selectedMainBottomBarIndex: StateFlow<Int> get() = _selectedMainBottomBarIndex

    fun changeSelectedMainBottomBarIndex(index: Int) {
        Log.d("changeSelectedMainBottomBarIndex", index.toString())
        _selectedMainBottomBarIndex.value = index
    }


}