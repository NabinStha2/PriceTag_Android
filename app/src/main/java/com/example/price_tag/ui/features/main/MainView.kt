package com.example.price_tag.ui.features.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.price_tag.ui.components.BottomNavigationBar
import com.example.price_tag.ui.components.SelectedCategoryViewModel
import com.example.price_tag.ui.features.category.CategoryView
import com.example.price_tag.ui.features.category.CategoryViewModel
import com.example.price_tag.ui.features.home.HomeView
import com.example.price_tag.ui.features.profile.ProfileView
import com.example.price_tag.ui.features.wishlist.WishlistView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainView(
    navController: NavHostController,
    viewModel: MainViewModel,
    categoryViewModel: CategoryViewModel,
    selectedCategoryViewModel: SelectedCategoryViewModel,
) {
    val selectedIndex by viewModel.selectedMainBottomBarIndex.collectAsStateWithLifecycle()

//    LaunchedEffect(Unit) {
//        categoryViewModel.getAllCategories()
//    }

    Scaffold(
        modifier = Modifier
            .background(color = Color.White)
            .fillMaxSize(),
        containerColor = Color.Gray.copy(alpha = 0.08f),
        bottomBar = {
            BottomNavigationBar(selectedIndex) { index ->
                viewModel.changeSelectedMainBottomBarIndex(index)
            }
        }
    ) { paddingValues ->
        when (selectedIndex) {
            0 -> HomeView(
                navController = navController,
                padding = paddingValues
            )

            1 -> CategoryView(
//                navController = navController,
                padding = paddingValues,
                categoryViewModel = categoryViewModel,
            )

            2 -> WishlistView(paddingValues)

            3 -> ProfileView(
                padding = paddingValues,
                navController = navController
            )
        }
    }
}
