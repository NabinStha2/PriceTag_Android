package com.example.price_tag.ui.features.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.DoorFront
import androidx.compose.material.icons.filled.IndeterminateCheckBox
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.example.price_tag.core.datastore.DataStoreManager
import com.example.price_tag.core.navigation.NavigationItem

@Composable
fun ProfileView(
    padding: PaddingValues,
    profileViewModel: ProfileViewModel = hiltViewModel(),
) {
    var showLogoutDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(padding)
            .padding(horizontal = 16.dp)
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(36.dp))
        Box(
            modifier = Modifier
                .height(100.dp)
                .width(100.dp)
                .background(
                    color = Color.Blue, shape = CircleShape,
                )
                .align(Alignment.CenterHorizontally)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://i.pinimg.com/736x/59/3b/9c/593b9c9d109a166084016f8dcf7afd1d.jpg")
                    .crossfade(true)
                    .transformations(CircleCropTransformation())
                    .build(),
                contentDescription = "Profile Image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop

            )

        }

        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Zhao Lusi",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontSize = 22.sp,
            fontWeight = FontWeight.W600,

            )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "zhaolusi@gmail.com",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontSize = 14.sp,
        )

        Spacer(modifier = Modifier.height(24.dp))
        ListItem(

            headlineContent = {
                Text(text = "Profile Details")
            },
            leadingContent = {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = null,
                )
            },

            trailingContent = {
                Icon(
                    imageVector = Icons.Filled.ChevronRight,
                    contentDescription = null,
                )
            }


        )
        Spacer(modifier = Modifier.height(8.dp))
        ListItem(
            modifier = Modifier.background(Color.Transparent),

            headlineContent = {
                Text(text = "Payment Information")
            },
            leadingContent = {
                Icon(
                    imageVector = Icons.Filled.CreditCard,
                    contentDescription = null,
                )
            },

            trailingContent = {
                Icon(
                    imageVector = Icons.Filled.ChevronRight,
                    contentDescription = null,
                )
            }


        )

        Spacer(modifier = Modifier.height(8.dp))
        ListItem(
            modifier = Modifier.background(Color.Transparent),

            headlineContent = {
                Text(text = "Order Details")
            },
            leadingContent = {
                Icon(
                    imageVector = Icons.Filled.IndeterminateCheckBox,
                    contentDescription = null,
                )
            },

            trailingContent = {
                Icon(
                    imageVector = Icons.Filled.ChevronRight,
                    contentDescription = null,
                )
            }


        )
        Spacer(modifier = Modifier.height(8.dp))
        ListItem(
            modifier = Modifier.background(Color.Transparent),

            headlineContent = {
                Text(text = "Terms of Use")
            },
            leadingContent = {
                Icon(
                    imageVector = Icons.Filled.Newspaper,
                    contentDescription = null,
                )
            },

            trailingContent = {
                Icon(
                    imageVector = Icons.Filled.ChevronRight,
                    contentDescription = null,
                )
            }


        )
        Spacer(modifier = Modifier.height(8.dp))
        ListItem(
            modifier = Modifier.background(Color.Transparent),

            headlineContent = {
                Text(text = "Privacy Policy")
            },
            leadingContent = {
                Icon(
                    imageVector = Icons.Filled.Shield,
                    contentDescription = null,
                )
            },

            trailingContent = {
                Icon(
                    imageVector = Icons.Filled.ChevronRight,
                    contentDescription = null,
                )
            }


        )
        Spacer(modifier = Modifier.height(12.dp))
        ListItem(
            colors = ListItemDefaults.colors(containerColor = Color.Transparent),


            headlineContent = {
                Text(
                    text = "Logout",
                    color = Color(0xFFFF6F6F),
                    fontWeight = FontWeight.W600,
                    )
            },
            leadingContent = {
                Icon(
                    imageVector = Icons.Filled.DoorFront,
                    contentDescription = null,
                    tint = Color(0xFFFF6F6F),
                )
            },
            modifier = Modifier.clickable {
                showLogoutDialog = true
            }


        )

        if (showLogoutDialog) {
            AlertDialog(
                onDismissRequest = { showLogoutDialog = false },
                title = { Text("Confirm Logout") },
                text = { Text("Are you sure you want to log out?") },
                confirmButton = {
                    Button(
                        onClick = {
                            profileViewModel.logout()
                            showLogoutDialog = false
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                    ) {
                        Text("Logout")
                    }
                },
                dismissButton = {
                    Button(onClick = { showLogoutDialog = false }) {
                        Text("Cancel")
                    }
                }
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun ProfileViewPreview() {
}