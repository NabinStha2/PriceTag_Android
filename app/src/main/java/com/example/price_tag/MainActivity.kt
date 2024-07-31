package com.example.price_tag

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.price_tag.ui.features.onboarding.OnboardingScreen
import com.example.price_tag.ui.theme.Price_tagTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OnboardingScreen()
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PriceTagPreview() {
    Price_tagTheme {
        Scaffold(modifier = Modifier.fillMaxSize(), containerColor = Color.Gray) { innerPadding ->
            val configuration = LocalConfiguration.current
            val screenHeightDp = configuration.screenHeightDp
            // Calculate half the screen height in dp
            val halfScreenHeightDp = (screenHeightDp / 1.5).dp
//            Log.d(
//                "halfScreenHeightDp",
//                "halfScreenHeightDp => ${halfScreenHeightDp} :: max height => ${screenHeightDp} :: remaining height => ${screenHeightDp.dp - halfScreenHeightDp}"
//            )

            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                AsyncImage(
                    model = "https://images.pexels.com/photos/1485548/pexels-photo-1485548.jpeg?cs=srgb&dl=pexels-fotios-photos-1485548.jpg&fm=jpg",
                    contentDescription = "Onboarding Image",
                    modifier = Modifier.fillMaxHeight(),
                    contentScale = ContentScale.Crop,

                    )
                Surface(
                    color = Color.White,
                    modifier = Modifier
                        .absoluteOffset(x = 0.dp, y = halfScreenHeightDp)
                        .fillMaxWidth()
                        .height(screenHeightDp.dp - halfScreenHeightDp)
                        .clip(shape = RoundedCornerShape(28.dp, 28.dp)),
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Spacer(modifier = Modifier.height(20.dp))
                        Row {

                            Box(
                                modifier = Modifier
                                    .height(10.dp)
                                    .width(10.dp)
                                    .border(1.dp, Color.Gray, shape = CircleShape),
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Box(
                                modifier = Modifier
                                    .height(10.dp)
                                    .width(10.dp)
                                    .border(1.dp, Color.Gray, shape = CircleShape),
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Box(
                                modifier = Modifier
                                    .height(10.dp)
                                    .width(10.dp)
                                    .border(1.dp, Color.Gray, shape = CircleShape),
                            )

                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "Look Good, Feel Good",
                            modifier = Modifier.padding(16.dp),
                            style = MaterialTheme.typography.titleLarge,
                            textAlign = TextAlign.Center,
                        )
                        Text(
                            text = "Hating the clothes in your wardrobe? Explore hundreds of outfit ideas",
                            modifier = Modifier.padding(16.dp),
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center,
                        )
                        Spacer(modifier = Modifier.height(30.dp))
                        IconButton(
                            onClick = { /* TODO: Navigate to the onboarding screen */ },
                            modifier = Modifier
                                .size(32.dp)
                                .background(color = Color.Black, shape = CircleShape),
//                            content = {
//                                Icon(imageVector = Icons.Default.ArrowForward, contentDescription = "Arrow forward", tint = Color.White)
//                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowForward,
                                contentDescription = "Arrow forward",
                                tint = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}