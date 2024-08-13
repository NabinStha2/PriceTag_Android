package com.example.price_tag.ui.features.auth.signup

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.price_tag.CustomSnackbarVisuals
import com.example.price_tag.data.model.auth.SignUpRequest
import com.example.price_tag.ui.components.Loader
import com.example.price_tag.ui.components.SharedViewModel
import com.example.price_tag.ui.navigation.NavigationItem
import com.example.price_tag.ui.theme.PrimaryColor
import com.example.price_tag.utils.Validations
import com.example.utilites.ResourceState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SignUpView(
    navController: NavController = rememberNavController(),
    signUpViewModel: SignUpViewModel = hiltViewModel(),
    sharedViewModel: SharedViewModel = hiltViewModel(),
) {
    var obscurePassword by remember { mutableStateOf(true) }
    var obscureConfirmPassword by remember { mutableStateOf(true) }

    var fullNameError by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var confirmPasswordError by remember { mutableStateOf<String?>(null) }
    var phoneNumberError by remember { mutableStateOf<String?>(null) }

    val coroutineScope = rememberCoroutineScope()

    val snackbarHostState: SnackbarHostState = sharedViewModel.snackbarHostState

    val signUpResponse by signUpViewModel.signUpData.collectAsState()

    var TAG = "SignUpView"




    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .safeContentPadding(),
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                snackbar = { snackbarData ->
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
                }
            )
        }
    ) { scaffoldPadding ->
        when (signUpResponse) {
            is ResourceState.Loading -> {
                Log.i(TAG, "Inside Loading State")
                Loader(message = "Registering, Please wait...")
            }

            is ResourceState.Success -> {
                val response = (signUpResponse as ResourceState.Success).data
                LaunchedEffect(Unit) {
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(
                            CustomSnackbarVisuals(
                                containerColor = PrimaryColor,
                                message = response.message,
                                contentColor = Color.White,
                            ),
                        )
                    }
                    delay(600)
                    coroutineScope.launch {
                        navController.navigate(NavigationItem.OtpView.route)
                    }
                }
            }

            is ResourceState.Error -> {
                Log.e(
                    TAG,
                    "Inside Error State : ${(signUpResponse as ResourceState.Error).error.toString()}"
                )
                LaunchedEffect(Unit) {
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(
                            CustomSnackbarVisuals(
                                containerColor = Color.Red,
                                message = (signUpResponse as ResourceState.Error).error.toString(),
                                contentColor = Color.White,
                            ),
                        )
                    }
                }
            }

            else -> {
                Log.d(TAG, "Else State")
            }
        }


        Column(
            modifier = Modifier
                .padding(scaffoldPadding)
                .padding(horizontal = 16.dp)
                .fillMaxHeight()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Create New Account",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W600,

                    ),
            )
            Spacer(modifier = Modifier.height(24.dp))
            OutlinedTextField(
                value = signUpViewModel.userName.value,
                onValueChange = {
                    signUpViewModel.userName.value = it
                    fullNameError = Validations().validateFullName(it)
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text(text = "User Name") },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Text,
                ),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "UserName"
                    )
                }

            )
            fullNameError?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 12.sp,
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .align(alignment = Alignment.Start),
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = signUpViewModel.email.value,
                onValueChange = {
                    signUpViewModel.email.value = it
                    emailError = Validations().validateEmail(it)
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text(text = "Email") },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Email,
                ),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = "Email"
                    )
                }
            )
            emailError?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 12.sp,
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .align(alignment = Alignment.Start),
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = signUpViewModel.phoneNumber.value,
                onValueChange = {
                    signUpViewModel.phoneNumber.value = it
                    phoneNumberError = Validations().validatePhoneNumber(it)
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text(text = "Phone Number") },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Phone,
                ),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = "Email"
                    )
                }

            )
            phoneNumberError?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 12.sp,
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .align(alignment = Alignment.Start),
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = signUpViewModel.password.value,
                onValueChange = {
                    signUpViewModel.password.value = it
                    passwordError = Validations().validatePasswords(it)
                    confirmPasswordError = Validations().validateConfirmPasswords(
                        password = it,
                        confirmPassword = signUpViewModel.confirmPassword.value
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text(text = "Password") },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Password,
                ),
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Password"
                    )
                },
                trailingIcon = {
                    IconButton(onClick = { obscurePassword = !obscurePassword }) {
                        if (obscurePassword) {
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
                visualTransformation = if (obscurePassword) PasswordVisualTransformation() else VisualTransformation.None
            )
            passwordError?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 12.sp,
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .align(alignment = Alignment.Start),
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = signUpViewModel.confirmPassword.value,
                onValueChange = {
                    signUpViewModel.confirmPassword.value = it
                    passwordError = Validations().validatePasswords(it)
                    confirmPasswordError = Validations().validateConfirmPasswords(
                        password = it,
                        confirmPassword = signUpViewModel.confirmPassword.value
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text(text = "Confirm Password") },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Password,
                ),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "ConfirmPassword"
                    )
                },
                trailingIcon = {
                    IconButton(onClick = { obscureConfirmPassword = !obscureConfirmPassword }) {
                        if (obscureConfirmPassword) {
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
                visualTransformation = if (obscureConfirmPassword) PasswordVisualTransformation() else VisualTransformation.None
            )
            confirmPasswordError?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 12.sp,
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .align(alignment = Alignment.Start),
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = {
                    signUpViewModel.register(
                        signUpRequest = SignUpRequest(
                            fullName = signUpViewModel.userName.value,
                            email = signUpViewModel.email.value,
                            password = signUpViewModel.password.value,
                            phoneNumber = signUpViewModel.phoneNumber.value.toLong(),
                        )
                    )
                },
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryColor),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
            ) {
                Text(
                    text = "SIGN UP",
                    modifier = Modifier.padding(vertical = 6.dp),
                    style = TextStyle(
                        fontWeight = FontWeight.W500,
                        fontSize = 16.sp,
                    )
                )

            }

            Spacer(modifier = Modifier.height(24.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                Text(text = "Already have an account?")
                Spacer(modifier = Modifier.widthIn(8.dp))

                ClickableText(
                    text = AnnotatedString("Login Now"),
                    onClick = {
                        navController.navigate(NavigationItem.LoginView.route) {
                            popUpTo(NavigationItem.LoginView.route) {
                                inclusive = true
                            }
                        }
                    },
                    style = TextStyle(
                        color = PrimaryColor,
                    ),
                )
            }

        }
    }
}


@Preview
@Composable
fun SignUpViewPreview() {
    SignUpView()
}