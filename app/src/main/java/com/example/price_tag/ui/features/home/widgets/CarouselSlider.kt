package com.example.price_tag.ui.features.home.widgets

import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.price_tag.ui.features.home.CarouselItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CarouselSlider(
    images: List<CarouselItem>,
    pageCount: Int,
    pagerState: PagerState,
    coroutine: CoroutineScope,
) {
    Box(
        modifier = Modifier
            .height(230.dp)
            .fillMaxWidth(),
    ) {
        HorizontalPager(
            state = pagerState,
            pageSpacing = 8.dp,
            verticalAlignment = Alignment.CenterVertically,
            pageSize = PageSize.Fill,
            userScrollEnabled = true,
            modifier = Modifier.fillMaxWidth(),
        ) { page ->
            val pageOffset = (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction
            val imageSize by animateFloatAsState(
                targetValue = if (pageOffset != 0.0f) 0.9f else 1f,
                label = "Image size animation",
                animationSpec = tween(durationMillis = 300, easing = EaseIn),
            )

            LaunchedEffect(key1 = pagerState.currentPage) {
                delay(3000)
                coroutine.launch {
                    pagerState.animateScrollToPage(
                        page = (pagerState.currentPage + 1) % pagerState.pageCount,
                        animationSpec = tween(durationMillis = 300, easing = EaseIn),
                    )
                }
            }

            Image(
                painter = painterResource(id = images[page].imageResId),
                contentDescription = "Carousel Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .fillMaxSize()
                    .graphicsLayer {
                        scaleX = imageSize
                        scaleY = imageSize
                    },
            )
        }

        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .absoluteOffset(y = (-10).dp),
        ) {
            repeat(images.size) { pageValue ->
                Box(
                    contentAlignment = Alignment.BottomCenter,
                    modifier = Modifier
                        .height(5.dp)
                        .width(5.dp)
                        .clip(CircleShape)
                        .background(color = if (pagerState.currentPage == pageValue) Color.Blue else Color.White),
                ) {}
                Spacer(modifier = Modifier.width(5.dp))
            }
        }
    }
}