package com.example.price_tag.ui.features.profile

import androidx.lifecycle.ViewModel
import com.example.price_tag.core.utils.SharedPreferencesUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val sharedPreferencesUtils: SharedPreferencesUtils,
) : ViewModel() {
    val spUtils: SharedPreferencesUtils = sharedPreferencesUtils
}