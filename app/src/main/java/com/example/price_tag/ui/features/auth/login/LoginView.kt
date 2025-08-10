package com.example.price_tag.ui.features.auth.login

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.price_tag.CustomSnackbarVisuals
import com.example.price_tag.R
import com.example.price_tag.data.model.auth.LoginRequest
import com.example.price_tag.ui.components.Loader
import com.example.price_tag.ui.navigation.NavigationItem
import com.example.price_tag.ui.theme.PrimaryColor
import com.example.price_tag.utils.Validations
import com.example.utilites.ResourceState

@Composable
fun LoginView(
    navController: NavController = rememberNavController(),
    loginViewModel: LoginViewModel,
) {
    val TAG = "LoginView"
    val loginResponse by loginViewModel.loginData.collectAsState()
    val spUtils = loginViewModel.spUtils
//    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current


    Log.e(TAG, "Outside of Scaffold")


    Scaffold(modifier = Modifier
        .fillMaxSize()
        .safeContentPadding(),
        containerColor = Color.White,
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState, snackbar = { snackbarData ->
                val customVisuals = snackbarData.visuals as? CustomSnackbarVisuals
                if (customVisuals != null) {
                    Snackbar(
                        snackbarData = snackbarData,
                        contentColor = customVisuals.contentColor,
                        containerColor = customVisuals.containerColor,
                    )
                } else {
                    Snackbar(snackbarData = snackbarData)
                }
            })
        }) { scaffoldPadding ->
        Log.e(TAG, "Inside of Scaffold")

        when (loginResponse) {
            is ResourceState.Loading -> {
                Log.i(TAG, "Inside Loading State")
                Loader(message = "Logging in, Please wait...")
            }

            is ResourceState.Success -> {
                val response = (loginResponse as ResourceState.Success).data
                Log.e(TAG, "Inside of Success")
//                LaunchedEffect(Unit) {
//                    coroutineScope.launch {
                Toast.makeText(
                    context,
                    "Login Successfully",
                    Toast.LENGTH_SHORT,
                ).show()
//                        snackbarHostState.showSnackbar(
//                            CustomSnackbarVisuals(
//                                containerColor = PrimaryColor,
//                                message = "Login successfully",
//                                contentColor = Color.White,
//                                duration = SnackbarDuration.Indefinite,
//                            ),
//                        )
//                    }
//                    delay(600)
//                    coroutineScope.launch {
                navController.navigate(NavigationItem.MainView.route) {
                    popUpTo(NavigationItem.LoginView.route) {
                        inclusive = true
                    }
                    // Avoid multiple copies of the same destination when
                    // reselecting the same item
                    launchSingleTop = true
                    // Restore state when reselecting a previously selected item
                    restoreState = true
                }
//                    }
//                }
                if (response.data?.accessToken != null && response.data.refreshToken != null) {
                    Log.i("Login response data", response.data.toString())
                    spUtils.save(
                        spUtils.bearerToken,
                        response.data.accessToken
                    )
                    spUtils.save(
                        spUtils.refreshToken,
                        response.data.refreshToken
                    )
                }
                loginViewModel.resetLoginResponse()
            }

            is ResourceState.Error -> {
                Log.e(
                    TAG, "Inside Error State : ${(loginResponse as ResourceState.Error).error}"
                )

//                LaunchedEffect(Unit) {
//                    coroutineScope.launch {
                Toast.makeText(
                    context,
                    (loginResponse as ResourceState.Error).error.toString(),
                    Toast.LENGTH_SHORT,
                ).show()
//                        snackbarHostState.showSnackbar(
//                            CustomSnackbarVisuals(
//                                containerColor = Color.Red,
//                                message = (loginResponse as ResourceState.Error).error.toString(),
//                                contentColor = Color.White,
//                            ),
//                        )
//                    }
//                }
            }

            else -> {
                Log.d(TAG, "Else State")
            }
        }


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(scaffoldPadding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,

            ) {

            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "logo",
                modifier = Modifier.width(200.dp),
            )
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Welcome Back",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W500,

                    ),
            )
            Spacer(modifier = Modifier.height(32.dp))
            OutlinedTextField(
                value = loginViewModel.email.value,
                onValueChange = {
                    loginViewModel.email.value = it
                    loginViewModel.emailError.value = Validations().validateEmail(it)
                },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text(text = "Email") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email, contentDescription = "Email"
                    )
                },
            )
            loginViewModel.emailError.value?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 12.sp,
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .align(alignment = Alignment.Start),
                    textAlign = TextAlign.Start,
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = loginViewModel.password.value,
                onValueChange = { loginViewModel.password.value = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text(text = "Password") },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock, contentDescription = "Password"
                    )
                },
                trailingIcon = {
                    IconButton(onClick = {
                        loginViewModel.obscurePassword.value = !loginViewModel.obscurePassword.value
                    }) {
                        if (loginViewModel.obscurePassword.value) {
                            Icon(
                                imageVector = Icons.Default.Visibility,
                                contentDescription = "Hide Password",
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.VisibilityOff,
                                contentDescription = "Show Password",
                            )
                        }
                    }
                },
                visualTransformation = if (loginViewModel.obscurePassword.value) PasswordVisualTransformation() else VisualTransformation.None


            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = {
                    loginViewModel.login(
                        loginRequest = LoginRequest(
                            email = loginViewModel.email.value,
                            password = loginViewModel.password.value
                        )
                    )
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
            ) {
                Text(
                    text = "LOGIN", modifier = Modifier.padding(vertical = 6.dp),
                    style = TextStyle(
                        fontWeight = FontWeight.W500,
                        fontSize = 16.sp,

                        ),
                )

            }
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                HorizontalDivider(modifier = Modifier.weight(1f))
                Spacer(modifier = Modifier.widthIn(8.dp))
                Text(text = "OR")
                Spacer(modifier = Modifier.widthIn(8.dp))
                HorizontalDivider(modifier = Modifier.weight(1f))


            }
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                Text(text = "Don't have an account?")
                Spacer(modifier = Modifier.widthIn(8.dp))

                ClickableText(
                    text = AnnotatedString("Sign Up"),
                    onClick = {
                        navController.navigate(NavigationItem.SignUpView.route)
//                        {
//                            popUpTo(NavigationItem.LoginView.route) {
//                                inclusive = true
//                            }
//                        }
                    },
                    style = TextStyle(
                        color = PrimaryColor,
                        fontWeight = FontWeight.W500,
                    ),
                )
            }

        }
    }
}

//@Preview
//@Composable
//fun LoginViewPreview() {
//    LoginView()
//}