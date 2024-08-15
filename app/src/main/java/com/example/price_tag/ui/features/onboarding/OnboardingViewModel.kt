package com.example.price_tag.ui.features.onboarding

import androidx.lifecycle.ViewModel
import com.example.price_tag.utils.SharedPreferencesUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val sharedPreferencesUtils: SharedPreferencesUtils
) : ViewModel() {
    val spUtils: SharedPreferencesUtils = sharedPreferencesUtils
}