package com.example.price_tag.ui.features.category

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun CategoryView(padding: PaddingValues) {

}


@Preview
@Composable
fun CategoryViewPreview(padding: PaddingValues = PaddingValues(16.dp)) {
    Column(
        modifier = Modifier
            .padding(padding)
            .fillMaxSize()
            .background(color = Color.White)
    ) { CategoryView(padding) }
}