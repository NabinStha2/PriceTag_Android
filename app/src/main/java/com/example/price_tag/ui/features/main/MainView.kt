package com.example.price_tag.ui.features.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.price_tag.ui.components.BottomNavigationBar
import com.example.price_tag.ui.features.category.CategoryView
import com.example.price_tag.ui.features.home.HomeView
import com.example.price_tag.ui.features.profile.ProfileView
import com.example.price_tag.ui.features.wishlist.WishlistView

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun MainView(
    navController: NavController = rememberNavController(),
    viewModel: MainViewModel = viewModel<MainViewModel>()
) {
    val selectedIndex by viewModel.selectedMainBottomBarIndex.collectAsState()
    Scaffold(
        modifier = Modifier
            .padding(WindowInsets.systemBars.asPaddingValues())
            .background(color = Color.White)
            .fillMaxSize(),
        containerColor = Color.Gray.copy(
            alpha = 0.08f
        ),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Price Tag",
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                        fontWeight = FontWeight.W600,
                        textAlign = TextAlign.Center,
                        color = Color.Black,
                    )
                },
                modifier = Modifier
                    .height(64.dp)
                    .background(color = Color.Red)
                    .wrapContentHeight(align = Alignment.CenterVertically),
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Gray.copy(
                        alpha = 0.08f
                    ),
                )
            )
        },
        bottomBar = {
            BottomNavigationBar(selectedIndex) {
                viewModel.changeSelectedMainBottomBarIndex(it)
            }
        }
    ) { padding ->
        when (selectedIndex) {
            0 -> {
                CategoryView(padding)
            }

            1 -> {
                HomeView(padding)
            }

            2 -> {
                WishlistView(padding)
            }

            3 -> {
                ProfileView(padding)
            }
        }

    }
}