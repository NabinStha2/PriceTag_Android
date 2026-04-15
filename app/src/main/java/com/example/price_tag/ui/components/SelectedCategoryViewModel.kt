package com.example.price_tag.ui.components

import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


@HiltViewModel
class SelectedCategoryViewModel  @Inject constructor() : ViewModel() {
    val snackbarHostState = SnackbarHostState()
    private val _selectedCategoryName = MutableStateFlow<String?>(null)
    val selectedCategoryName: StateFlow<String?> = _selectedCategoryName

    fun setCategoryName(name: String) {
        _selectedCategoryName.value = name
    }
}