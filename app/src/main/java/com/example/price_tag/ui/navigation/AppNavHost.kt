package com.example.price_tag.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.price_tag.ui.components.SharedViewModel
import com.example.price_tag.ui.features.auth.login.LoginView
import com.example.price_tag.ui.features.auth.login.LoginViewModel
import com.example.price_tag.ui.features.auth.otp.OtpView
import com.example.price_tag.ui.features.auth.otp.OtpViewModel
import com.example.price_tag.ui.features.auth.signup.SignUpView
import com.example.price_tag.ui.features.auth.signup.SignUpViewModel
import com.example.price_tag.ui.features.category.CategoryViewModel
import com.example.price_tag.ui.features.main.MainView
import com.example.price_tag.ui.features.main.MainViewModel
import com.example.price_tag.ui.features.onboarding.OnboardingView
import com.example.price_tag.ui.features.product.ProductView
import com.example.price_tag.ui.features.product.ProductViewModel
import com.example.price_tag.ui.features.productDetails.ProductDetailsView
import com.example.price_tag.ui.features.productDetails.ProductDetailsViewModel


@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String,
) {
    val sharedViewModel: SharedViewModel = hiltViewModel()


    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination,
    ) {
        composable(
            route = NavigationItem.OnboardingView.route,
        ) {
            OnboardingView(navController)
        }

        composable(route = NavigationItem.MainView.route, enterTransition = {
            slideIn(tween(durationMillis = 200)) {
                IntOffset(x = it.width, y = 0) // Start off-screen to the right
            }
        }, exitTransition = {
            slideOut(tween(durationMillis = 200)) {
                IntOffset(x = -it.width, y = 0) // Exit off-screen to the left
            }
        }) {
            val mainViewModel: MainViewModel = hiltViewModel()
            val categoryViewModel: CategoryViewModel = hiltViewModel()
            MainView(
                navController = navController,
                viewModel = mainViewModel,
                sharedViewModel = sharedViewModel,
                categoryViewModel = categoryViewModel,
            )
        }

        composable(route = NavigationItem.LoginView.route, enterTransition = {
            slideIn(tween(durationMillis = 200)) {
                IntOffset(x = it.width, y = 0) // Start off-screen to the right
            }
        }, exitTransition = {
            slideOut(tween(durationMillis = 200)) {
                IntOffset(x = -it.width, y = 0) // Exit off-screen to the left
            }
        }) {
            val loginViewModel: LoginViewModel = hiltViewModel()
            LoginView(
                navController = navController,
                loginViewModel = loginViewModel,
            )
        }

        composable(route = NavigationItem.SignUpView.route, enterTransition = {
            slideIn(tween(durationMillis = 200)) {
                IntOffset(x = it.width, y = 0) // Start off-screen to the right
            }
        }, exitTransition = {
            slideOut(tween(durationMillis = 200)) {
                IntOffset(x = -it.width, y = 0) // Exit off-screen to the left
            }
        }) {
            val signUpViewModel: SignUpViewModel = hiltViewModel()
            SignUpView(
                navController = navController,
                signUpViewModel = signUpViewModel,
                sharedViewModel = sharedViewModel,
            )
        }

        composable(route = NavigationItem.OtpView.route, enterTransition = {
            slideIn(tween(durationMillis = 200)) {
                IntOffset(x = it.width, y = 0) // Start off-screen to the right
            }
        }, exitTransition = {
            slideOut(tween(durationMillis = 200)) {
                IntOffset(x = -it.width, y = 0) // Exit off-screen to the left
            }
        }) {
            val otpViewModel: OtpViewModel = hiltViewModel()
            OtpView(
                navController = navController,
                otpViewModel = otpViewModel,
                sharedViewModel = sharedViewModel,
            )
        }

        composable(route = NavigationItem.ProductView.route,
            arguments = listOf(navArgument("subCategoryId") {
                type = NavType.LongType
                defaultValue = 0
            }),
            enterTransition = {
                slideIn(tween(durationMillis = 200)) {
                    IntOffset(x = it.width, y = 0) // Start off-screen to the right
                }
            }, exitTransition = {
                slideOut(tween(durationMillis = 200)) {
                    IntOffset(x = -it.width, y = 0) // Exit off-screen to the left
                }
            }) { backStackEntry ->
            val productViewModel: ProductViewModel = hiltViewModel()
            val subCategoryId = backStackEntry.arguments?.getLong("subCategoryId")
            ProductView(
                navController = navController,
                paddingValues = PaddingValues(),
                subCategoryId = subCategoryId,
                sharedViewModel = sharedViewModel,
                productViewModel = productViewModel
            )
        }

        composable(route = NavigationItem.ProductDetailsView.route,
            arguments = listOf(navArgument("productId") {
                type = NavType.LongType
                defaultValue = 0
            }),
            enterTransition = {
                slideIn(tween(durationMillis = 200)) {
                    IntOffset(x = it.width, y = 0) // Start off-screen to the right
                }
            }, exitTransition = {
                slideOut(tween(durationMillis = 200)) {
                    IntOffset(x = -it.width, y = 0) // Exit off-screen to the left
                }
            }) { backStackEntry ->
            val productDetailsViewModel: ProductDetailsViewModel = hiltViewModel()
            val productId = backStackEntry.arguments?.getLong("productId")
            ProductDetailsView(
                navController = navController,
                productId = productId,
                sharedViewModel = sharedViewModel,
                productDetailsViewModel = productDetailsViewModel,
                paddingValues = PaddingValues(),
                )
        }
    }
}