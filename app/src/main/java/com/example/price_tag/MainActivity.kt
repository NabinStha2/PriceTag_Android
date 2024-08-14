package com.example.price_tag

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.price_tag.ui.components.SharedPreferencesViewModel
import com.example.price_tag.ui.navigation.AppNavHost
import com.example.price_tag.ui.navigation.NavigationItem
import com.example.price_tag.ui.theme.Price_tagTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val TAG = "MainActivity"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val sharedPreferencesUtil: SharedPreferencesViewModel = hiltViewModel()
            var navigationRoute: String = NavigationItem.OnboardingView.route

            Log.i(
                TAG, "${
                    sharedPreferencesUtil.getValueBoolean(
                        sharedPreferencesUtil.firstTimeInApp,
                        true
                    )
                } => ${
                    sharedPreferencesUtil.getValueString(
                        sharedPreferencesUtil.bearerToken
                    )
                }"
            )
            if (!sharedPreferencesUtil.getValueBoolean(
                    sharedPreferencesUtil.firstTimeInApp,
                    true
                )
            ) {
                navigationRoute =
                    if (sharedPreferencesUtil.getValueString(sharedPreferencesUtil.bearerToken) != null) {
                        NavigationItem.MainView.route
                    } else {
                        NavigationItem.LoginView.route
                    }
            }

            Log.i(
                TAG, "navigationRoute${navigationRoute}"
            )

            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.White)
            ) {
                AppNavHost(
                    startDestination = navigationRoute,
                )
            }
        }
    }
}

data class CustomSnackbarVisuals(
    override val message: String,
    override val duration: SnackbarDuration = SnackbarDuration.Short,
    val containerColor: Color = Color.White,
    val contentColor: Color = Color.Red,
    override val actionLabel: String? = null,
    override val withDismissAction: Boolean = false
) : SnackbarVisuals


@Preview(showBackground = true)
@Composable
fun PriceTagPreview() {
    Price_tagTheme {
        AppNavHost(
            startDestination = NavigationItem.OnboardingView.route
        )
    }
}