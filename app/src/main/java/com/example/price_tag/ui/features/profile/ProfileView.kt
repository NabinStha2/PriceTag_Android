package com.example.price_tag.ui.features.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ProfileView(padding: PaddingValues) {
    Column(
        modifier = Modifier
            .padding(padding)
    ) {
        Text(text = "Profile View")
    }
}