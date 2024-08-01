package com.example.price_tag.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun BottomNavigationBar(selectedIndex: Int, onChangeIndex: (Int) -> Unit) {
    // Remember the current selected index
//    var selectedIndex: Int by rememberSaveable { (mutableIntStateOf(0)) }

    BottomNavigation(
        modifier = Modifier.padding(0.dp),
        backgroundColor = Color.White,
    ) {
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
            label = { Text("Home") },
            selected = selectedIndex == 0,
            onClick = { onChangeIndex(0) }
        )
//        BottomNavigationItem(
//            icon = { Icon(Icons.Filled.Search, contentDescription = "Category") },
//            label = { Text("Search") },
//            selected = selectedIndex == 1,
//            onClick = { selectedIndex = 1 }
//        )
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.Person, contentDescription = "Profile") },
            label = { Text("Profile") },
            selected = selectedIndex == 1,
            onClick = { onChangeIndex(1) }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavigationBarPreview() {
//    BottomNavigationBar()
}
