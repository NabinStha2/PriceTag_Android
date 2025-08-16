package com.example.price_tag.ui.features.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.price_tag.ui.components.BottomNavigationBar
import com.example.price_tag.ui.components.SharedViewModel
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
    sharedViewModel: SharedViewModel,
) {
    val TAG = "MainView"
    val selectedIndex by viewModel.selectedMainBottomBarIndex.collectAsState()

    LaunchedEffect(key1 = true) {
        categoryViewModel.getAllCategories()
    }

    Scaffold(
        modifier = Modifier
//            .padding(WindowInsets.systemBars.asPaddingValues())
            .background(color = Color.White)
            .fillMaxSize(),
        containerColor = Color.Gray.copy(
            alpha = 0.08f
        ),
//        topBar = {
//            CenterAlignedTopAppBar(
//                title = {
//                    Text(
//                        text = if (selectedIndex == 0) "Price Tag" else if (selectedIndex == 1) "Category" else if (selectedIndex == 2) "Wishlist" else "Profile",
//                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
//                        fontWeight = FontWeight.W600,
//                        textAlign = TextAlign.Center,
//                        color = Color.Black,
//                    )
//                },
//                modifier = Modifier
//                    .background(color = Color.White),
//                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
//                    containerColor = Color.Gray.copy(
//                        alpha = 0.08f
//                    ),
//                )
//            )
//        },
        bottomBar = {
            BottomNavigationBar(selectedIndex) {
                viewModel.changeSelectedMainBottomBarIndex(it)
            }
        }
    ) { padding ->
        when (selectedIndex) {
            0 -> {
                HomeView(navController = navController, padding = padding)
            }

            1 -> {
                CategoryView(
                    navController = navController,
                    padding = padding,
                    categoryViewModel = categoryViewModel,
                    sharedViewModel = sharedViewModel
                )
            }

            2 -> {
                WishlistView(padding)
            }

            3 -> {
                ProfileView(padding, navController = navController)
            }
        }

    }
}