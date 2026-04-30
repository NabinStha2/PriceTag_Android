package com.example.price_tag.ui.features.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.price_tag.core.datastore.DataStoreManager
import com.example.price_tag.core.navigation.NavigationCoordinator
import com.example.price_tag.core.navigation.NavigationItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val navigationCoordinator: NavigationCoordinator,
) : ViewModel() {

    fun logout() {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreManager.clearAuthTokens()
            navigationCoordinator.navigateAndClear(NavigationItem.LoginView)
        }
    }
}