package com.example.price_tag.ui.features.onboarding

import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import kotlinx.coroutines.launch


@Preview(showBackground = true)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingView() {
    Scaffold(modifier = Modifier.fillMaxSize(), containerColor = Color.Gray) { padding ->
        val pagerState = rememberPagerState(pageCount = { 3 })
        val coroutineScope = rememberCoroutineScope()
        val configuration = LocalConfiguration.current
        val screenHeightDp = configuration.screenHeightDp
        // Calculate half the screen height in dp
        val halfScreenHeightDp = (screenHeightDp / 1.5).dp


        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            HorizontalPager(state = pagerState, modifier = Modifier.fillMaxSize()) { page ->
                AsyncImage(
                    model = if (page == 0) "https://www.designhill.com/resize_img.php?w=767&h=640&atyp=st_page_file&pth=stdio_ad_ca||4219||custom_banner_new1_mobile_background_img&flp=1708689455-45420308665d8882f25e093-53314313.png" else if (page == 1) "https://media.very.co.uk/i/sd/ctmt-210-womens-dept-1?%24poi%24=&aspect=1%3A1&w=900&qlt=default&fmt=jpg&fmt.jpeg.interlaced=true" else "https://oldnavyprod.a.bigcontent.io/v1/static/240501_25-M1202_G_DP_Sale_0501",
                    contentDescription = "Onboarding Image",
                    modifier = Modifier.fillMaxHeight(),
                    contentScale = ContentScale.Crop,
                )
            }
            Surface(
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
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
                                .border(1.dp, Color.Gray, shape = CircleShape)
                                .clip(shape = CircleShape)
                                .background(color = if (pagerState.currentPage == 0) Color.Blue else Color.White),
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Box(
                            modifier = Modifier
                                .height(10.dp)
                                .width(10.dp)
                                .border(1.dp, Color.Gray, shape = CircleShape)
                                .clip(shape = CircleShape)
                                .background(color = if (pagerState.currentPage == 1) Color.Blue else Color.White),
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Box(
                            modifier = Modifier
                                .height(10.dp)
                                .width(10.dp)
                                .border(1.dp, Color.Gray, shape = CircleShape)
                                .clip(shape = CircleShape)
                                .background(color = if (pagerState.currentPage == 2) Color.Blue else Color.White),
                        )

                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = if (pagerState.currentPage == 0) "Look Good, Feel Good" else if (pagerState.currentPage == 1) "Find Your Outfits" else "Your Style, Your Way",
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center,
                    )
                    Text(
                        text = if (pagerState.currentPage == 0) "Hating the clothes in your wardrobe? Explore hundreds of outfit ideas" else if (pagerState.currentPage == 1) "Confused about your outfit? Don't worry! Find the best outfit here!" else "Create your individual & unique style and look amazing everyday",
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    if (pagerState.currentPage == 0 || pagerState.currentPage == 1) IconButton(
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(
                                    pagerState.currentPage + 1,
                                    animationSpec = tween(
                                        durationMillis = 400,
                                        easing = EaseIn,
                                    ),
                                )
                            }
                        },
                        modifier = Modifier
                            .size(32.dp)
                            .background(color = Color.Black, shape = CircleShape),
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = "Arrow forward",
                            tint = Color.White
                        )
                    }
                    else
                        ElevatedButton(
                            onClick = { /*TODO*/ },
                            colors = ButtonDefaults.elevatedButtonColors(
                                containerColor = Color.Blue,
                                contentColor = Color.White,
                            ),
                        ) {
                            Text(
                                text = "Let's Get Started",
                                color = Color.White,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Normal,
                            )
                        }
                }
            }
        }
    }
}

