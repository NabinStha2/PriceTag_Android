package com.example.price_tag.ui.features.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun MainView(navController: NavController) {
    Scaffold(
        topBar = {
            Text(text = "Main Screen")
        },
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            Text(text = "Main View")
        }
    }
}