package com.example.price_tag.ui.features.onboarding

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.price_tag.core.datastore.DataStoreManager
import com.example.price_tag.core.navigation.NavigationCoordinator
import com.example.price_tag.core.navigation.NavigationItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val navigationCoordinator: NavigationCoordinator,
) : ViewModel() {
    var TAG = "OnboardingViewModel"

    fun onGetStartedClick() {
        viewModelScope.launch {
            Log.d(TAG,
                  "onGetStartedClick: ${dataStoreManager.firstTimeInApp.first()}")

            dataStoreManager.setFirstTime(isFirstTime = false)

            navigationCoordinator.navigateAndClear(NavigationItem.AuthGraph)
        }
    }

}