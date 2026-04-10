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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.price_tag.ui.components.SharedViewModel
import com.example.price_tag.ui.navigation.AppNavHost
import com.example.price_tag.ui.navigation.NavigationItem
import com.example.price_tag.utils.SharedPreferencesUtils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val TAG = "MainActivity"

    @Inject
    lateinit var sharedPreferencesUtils: SharedPreferencesUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var navigationRoute: String = NavigationItem.OnboardingView.route

            Log.i(TAG,
                  "${
                      sharedPreferencesUtils.getValueBoolean(sharedPreferencesUtils.firstTimeInApp,
                                                             true)
                  } => ${
                      sharedPreferencesUtils.getValueString(sharedPreferencesUtils.bearerToken)
                  }")
            if (!sharedPreferencesUtils.getValueBoolean(sharedPreferencesUtils.firstTimeInApp,
                                                        true)) {
                navigationRoute =
                    if (sharedPreferencesUtils.getValueString(sharedPreferencesUtils.bearerToken) != null) {
                        NavigationItem.MainView.route
                    } else {
                        NavigationItem.LoginView.route
                    }
            }

            Log.i(TAG,
                  "navigationRoute${navigationRoute}")

            val navController: NavHostController = rememberNavController()
            val sharedViewModel: SharedViewModel = hiltViewModel()


            Surface(modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)) {
                AppNavHost(
                    startDestination = navigationRoute,
                    navController = navController,
                    sharedViewModel = sharedViewModel,
                )
            }
        }
    }
}

data class CustomSnackbarVisuals(override val message: String,
                                 override val duration: SnackbarDuration = SnackbarDuration.Short,
                                 val containerColor: Color = Color.White,
                                 val contentColor: Color = Color.Red,
                                 override val actionLabel: String? = null,
                                 override val withDismissAction: Boolean = false) : SnackbarVisuals


//@Preview(showBackground = true)
//@Composable
//fun PriceTagPreview() {
//    Price_tagTheme {
//        AppNavHost(
//            startDestination = NavigationItem.OnboardingView.route
//        )
//    }
//}