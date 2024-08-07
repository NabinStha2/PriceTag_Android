package com.example.price_tag.ui.features.category

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.price_tag.R
import com.example.price_tag.navigation.NavigationItem
import com.example.price_tag.ui.features.main.MainView

@Composable
fun CategoryView(
    navController: NavController = rememberNavController(),
    padding: PaddingValues
) {
    val configuration = LocalConfiguration.current
    var selectedItem by remember { mutableIntStateOf(0) }
    val listItem = listOf(
        "New Arrival",
        "Clothing",
        "Accessories",
    )
    val icons = listOf(
        Icons.Filled.Home,
        Icons.Filled.Search,
        Icons.Filled.Settings,
    )



    Row {
        NavigationRail(
            modifier = Modifier
                .padding(padding)
                .width(configuration.screenWidthDp.dp * 0.23f)
                .fillMaxHeight()
                .weight(1f)
                .verticalScroll(rememberScrollState()),
            containerColor = Color.White,
            contentColor = Color.Black,
        ) {
            listItem.forEachIndexed { index, item ->
                NavigationRailItem(modifier = Modifier.height(60.dp),
                    icon = {
                        Icon(
                            icons[index], contentDescription = item, modifier = Modifier.size(24.dp)
                        )
                    },
                    label = { Text(item) },
                    selected = selectedItem == index,
                    onClick = { selectedItem = index })
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        Spacer(modifier = Modifier.width(10.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .padding(padding)
                .weight(3f),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(count = 20) { index ->
                Box(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .clickable {
                            navController.navigate(route = NavigationItem.ProductView.route)
                        },
                ) {
                    Column {
//                        AsyncImage(
//                            model = "https://buffer.com/library/content/images/size/w1200/2023/10/free-images.jpg",
//                            contentDescription = "SubCategory Image",
//                           modifier = Modifier
//                                .height(120.dp)
//                                .clip(RoundedCornerShape(12.dp))
//                        )
                        Image(
                            painter = painterResource(id = R.drawable.carousel1),
                            contentDescription = "Product Image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .height(120.dp)
                                .clip(RoundedCornerShape(12.dp))
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "SubCategory $index",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.W500,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .align(alignment = Alignment.CenterHorizontally)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }

    }


}


@Preview
@Composable
fun CategoryViewPreview() {
    MainView()
}