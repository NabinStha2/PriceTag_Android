package com.example.price_tag.ui.features.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun HomeView(padding: PaddingValues = PaddingValues(0.dp)) {
    Column(
        modifier = Modifier
            .padding(padding)
            .padding(horizontal = 12.dp),
    ) {
        Spacer(modifier = Modifier.padding(top = 10.dp))

        Row {
            OutlinedTextField(
                value = "",
                onValueChange = {},
                modifier = Modifier
                    .height(56.dp)
                    .weight(5f),
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        // Handle search action here
                        // e.g., perform a search, close the keyboard, etc.
                    }
                ),
                shape = RoundedCornerShape(12.dp),
                placeholder = { Text(text = "Search") },
                trailingIcon = {
                    IconButton(
                        onClick = { /*TODO*/ },
                        modifier = Modifier
                            .height(56.dp)
                            .clip(RoundedCornerShape(topEnd = 12.dp, bottomEnd = 12.dp)),
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Search Icon",
                            tint = Color.Black,
                        )
                    }
                },
            )

            Spacer(modifier = Modifier.width(10.dp))

            IconButton(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .height(56.dp)
                    .weight(1f)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.LightGray)
                    .border(
                        1.dp,
                        Color.White,
                        RoundedCornerShape(12.dp),
                    ),
            ) {
                Icon(
                    imageVector = Icons.Filled.ShoppingCart,
                    contentDescription = "Search Icon",
                    tint = Color.White,
                )
            }
        }

        Spacer(modifier = Modifier.padding(top = 10.dp))
    


    }
}

@Preview
@Composable
fun HomeViewWithSearch(padding: PaddingValues = PaddingValues(16.dp)) {
    Column(
        modifier = Modifier
            .padding(padding)
            .fillMaxSize()
            .background(color = Color.White)
    ) { HomeView(padding) }
}