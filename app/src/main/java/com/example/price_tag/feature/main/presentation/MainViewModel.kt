package com.example.price_tag.feature.main.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.price_tag.core.navigation.NavigationCoordinator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val navigationCoordinator: NavigationCoordinator
) : ViewModel() {

    private val _selectedMainBottomBarIndex = MutableStateFlow(0)
    val selectedMainBottomBarIndex: StateFlow<Int> = _selectedMainBottomBarIndex.asStateFlow()

    fun changeSelectedMainBottomBarIndex(index: Int) {
        Log.d("MainViewModel", "Tab changed to: $index")
        _selectedMainBottomBarIndex.value = index
    }
    
    suspend fun navigateBack() {
        navigationCoordinator.navigateBack()
    }
}
