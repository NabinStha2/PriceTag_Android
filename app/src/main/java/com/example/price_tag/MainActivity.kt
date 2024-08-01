package com.example.price_tag

import LoginView
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.price_tag.navigation.NavigationStack
import com.example.price_tag.ui.features.onboarding.OnboardingView
import com.example.price_tag.ui.theme.Price_tagTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NavigationStack()
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PriceTagPreview() {
    Price_tagTheme {
        OnboardingView()
    }
}