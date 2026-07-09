package com.example.price_tag

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.price_tag.core.navigation.NavigationCoordinator
import com.example.price_tag.core.navigation.ObserveNavigationEvents
import com.example.price_tag.core.navigation.RootNavHost
import com.example.price_tag.core.state.AppState
import com.example.price_tag.core.theme.PriceTagTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val TAG = "MainActivity"

    @Inject
    lateinit var navigationCoordinator: NavigationCoordinator
    private val appViewModel: AppViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController: NavHostController = rememberNavController()
            val appState: AppState by appViewModel.appState.collectAsStateWithLifecycle()

            // Wire the coordinator to the NavController.
            // Lives at the root — handles ALL navigation events app-wide.
            ObserveNavigationEvents(
                coordinator = navigationCoordinator,
                navController = navController,
            )

            PriceTagTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) { // Render the correct graph for the current session state.
                    RootNavHost(
                        navController = navController,
                        appState = appState,
                    )
                }
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
    override val withDismissAction: Boolean = false,
) : SnackbarVisuals
