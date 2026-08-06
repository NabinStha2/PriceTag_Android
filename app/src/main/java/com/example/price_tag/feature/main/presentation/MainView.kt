package com.example.price_tag.feature.main.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.price_tag.CustomSnackbarVisuals
import com.example.price_tag.core.theme.PrimaryColor
import com.example.price_tag.core.ui.components.BottomNavigationBar
import com.example.price_tag.core.ui.viewmodel.SelectedCategoryViewModel
import com.example.price_tag.feature.cart.presentation.CartContract
import com.example.price_tag.feature.cart.presentation.CartView
import com.example.price_tag.feature.cart.presentation.CartViewModel
import com.example.price_tag.feature.category.presentation.CategoryView
import com.example.price_tag.feature.category.presentation.CategoryViewModel
import com.example.price_tag.feature.home.presentation.HomeView
import com.example.price_tag.feature.profile.presentation.ProfileView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainView(
    navController: NavHostController,
    viewModel: MainViewModel,
    categoryViewModel: CategoryViewModel,
    cartViewModel: CartViewModel,
    selectedCategoryViewModel: SelectedCategoryViewModel,
) {
    val selectedIndex by viewModel.selectedMainBottomBarIndex.collectAsStateWithLifecycle()
    val snackbarHostState: SnackbarHostState = selectedCategoryViewModel.snackbarHostState

    LaunchedEffect(Unit) {
        cartViewModel.effect.collect { effect ->
            when (effect) {
                is CartContract.CartEffect.ShowSnackBar -> {
                    snackbarHostState.showSnackbar(
                        CustomSnackbarVisuals(
                            message = effect.message,
                            containerColor = PrimaryColor,
                            contentColor = Color.White
                        )
                    )
                }
                else -> {}
            }
        }
    }

    Scaffold(
        modifier = Modifier
            .background(color = Color.White)
            .fillMaxSize(),
        containerColor = Color.Gray.copy(alpha = 0.08f),
        bottomBar = {
            BottomNavigationBar(selectedIndex) { index ->
                viewModel.changeSelectedMainBottomBarIndex(index)
            }
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                snackbar = { snackbarData ->
                    val customVisuals = snackbarData.visuals as? CustomSnackbarVisuals
                    if (customVisuals != null) {
                        Snackbar(
                            snackbarData = snackbarData,
                            contentColor = customVisuals.contentColor,
                            containerColor = customVisuals.containerColor,
                        )
                    } else {
                        Snackbar(snackbarData = snackbarData)
                    }
                }
            )
        },
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when (selectedIndex) {
                0 -> HomeView(
                    navController = navController,
                    cartViewModel = cartViewModel,
                    selectedCategoryViewModel = selectedCategoryViewModel,
                )

                1 -> CategoryView(
                    categoryViewModel = categoryViewModel,
                    cartViewModel = cartViewModel,
                    selectedCategoryViewModel = selectedCategoryViewModel,
                )

                2 -> CartView(
                    navController = navController,
                    cartViewModel = cartViewModel,
                    selectedCategoryViewModel = selectedCategoryViewModel,
                )

                3 -> ProfileView(
                    padding = PaddingValues(),
                )
            }
        }
    }
}
