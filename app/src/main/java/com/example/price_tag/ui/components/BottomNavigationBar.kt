package com.example.price_tag.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun BottomNavigationBar(selectedIndex: Int, onChangeIndex: (Int) -> Unit) {
    // Remember the current selected index
//    var selectedIndex: Int by rememberSaveable { (mutableIntStateOf(0)) }

    NavigationBar(containerColor = Color.White) {
        NavigationBarItem(icon = {
            Icon(
                Icons.Filled.Home, contentDescription = "Home"
            )
        },
            label = { Text("Home") },
            colors = NavigationBarItemDefaults.colors(
                unselectedIconColor = Color.Gray,
                selectedIconColor = Color.Blue
            ),
            selected = selectedIndex == 0,
            onClick = { onChangeIndex(0) })

        NavigationBarItem(icon = {
            Icon(
                Icons.Filled.Category,
                contentDescription = "Category"
            )
        },
            label = { Text("Category") },
            colors = NavigationBarItemDefaults.colors(
                unselectedIconColor = Color.Gray,
                selectedIconColor = Color.Blue
            ),
            selected = selectedIndex == 1,
            onClick = { onChangeIndex(1) })

        NavigationBarItem(icon = {
            Icon(
                Icons.Filled.ShoppingCart,
                contentDescription = "Cart"
            )
        },
            label = { Text("Cart") },
            colors = NavigationBarItemDefaults.colors(
                unselectedIconColor = Color.Gray,
                selectedIconColor = Color.Blue
            ),
            selected = selectedIndex == 2,
            onClick = { onChangeIndex(2) })

        NavigationBarItem(icon = {
            Icon(
                Icons.Filled.Person, contentDescription = "Profile"
            )
        },
            label = { Text("Profile") },
            colors = NavigationBarItemDefaults.colors(
                unselectedIconColor = Color.Gray,
                selectedIconColor = Color.Blue
            ),
            selected = selectedIndex == 3,
            onClick = { onChangeIndex(3) })
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavigationBarPreview() {
//    BottomNavigationBar()
}
