package com.example.price_tag.ui.components

import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class SharedViewModel  @Inject constructor() : ViewModel() {
    val snackbarHostState = SnackbarHostState()
}