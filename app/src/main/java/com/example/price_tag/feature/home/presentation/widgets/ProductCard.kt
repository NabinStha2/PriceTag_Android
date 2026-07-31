package com.example.price_tag.feature.home.presentation.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.example.price_tag.core.theme.NeutralColor100
import com.example.price_tag.core.theme.NeutralColor50
import com.example.price_tag.feature.product.domain.model.ProductData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductCard(
    index: Int? = 0,
    product: ProductData,
    width: Dp = 160.dp,
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier

            .border(
                width = 1.dp,
                color = NeutralColor50,
                shape = RoundedCornerShape(12.dp),
            ),
        onClick = onClick,
        colors = CardDefaults.cardColors(Color.White),
    ) {
        Column {
            if (product.imageUrl != null) {
                val painter = rememberAsyncImagePainter(model = product.imageUrl)
                val state = painter.state
                Box(
                    modifier = Modifier
                        .height(120.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentAlignment = Alignment.Center,
                ) {
                    Image(
                        painter = painter,
                        contentDescription = "Product Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .height(120.dp)
                            .fillMaxWidth(),
                    )

                    when (state) {
                        is AsyncImagePainter.State.Loading -> {
                            CircularProgressIndicator(modifier = Modifier.size(24.dp))
                        }

                        is AsyncImagePainter.State.Error -> {
                            Text("Image not found",
                                 fontSize = 12.sp)
                        }

                        else -> Unit
                    }
                }
            } else {
                AsyncImage(
                    model = "https://plus.unsplash.com/premium_photo-1664474619075-644dd191935f?fm=jpg&q=60&w=3000&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MXx8aW1hZ2V8ZW58MHx8MHx8fDA%3D",
                    contentDescription = "Product Image",
                    modifier = Modifier
                        .height(120.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .fillMaxWidth(),
                    contentScale = ContentScale.Crop,
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Box(
                    modifier = Modifier
                        .border(
                            width = 1.dp,
                            color = NeutralColor100,
                            shape = RoundedCornerShape(percent = 40),
                        )
                        .padding(4.dp),
                ) {
                    Text(
                        text = product.brand ?: "",
                        style = MaterialTheme.typography.labelMedium,
                    )
                }
                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = product.name,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.W500,
                    ),
                )
                Spacer(modifier = Modifier.height(6.dp))


                Row {
                    Text(
                        text = product.basePrice.toString(),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.error,
                        ),
                        textDecoration = TextDecoration.LineThrough,
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = product.discountedPrice.toString(),
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.W600,
                        ),
                    )
                }
            }

            Spacer(modifier = Modifier.height(2.dp))
        }
    }
}
