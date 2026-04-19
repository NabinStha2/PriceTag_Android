package com.example.price_tag.ui.features.auth.otp

import android.util.Log
import androidx.compose.foundation.border
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.price_tag.CustomSnackbarVisuals
import com.example.price_tag.data.model.auth.OtpRequest
import com.example.price_tag.domain.model.auth.User
import com.example.price_tag.ui.components.Loader
import com.example.price_tag.ui.components.SelectedCategoryViewModel
import com.example.price_tag.ui.features.auth.signup.SignUpViewModel
import com.example.price_tag.core.navigation.NavigationItem
import com.example.price_tag.core.theme.PrimaryColor
import com.example.utilites.ResourceState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OtpView(
    navController: NavController = rememberNavController(),
    otpViewModel: OtpViewModel,
    selectedCategoryViewModel: SelectedCategoryViewModel,
) {
    var TAG = "OtpView"

    val signUpViewModel: SignUpViewModel = hiltViewModel()
    val coroutineScope = rememberCoroutineScope()
    var otpCode by remember { mutableStateOf("") }
    val verifyOtpData: ResourceState<User> by otpViewModel.verifyOtpData.collectAsState()
    val snackbarHostState: SnackbarHostState = selectedCategoryViewModel.snackbarHostState
    val context = LocalContext.current

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .safeContentPadding(),
        containerColor = Color.White,
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
        },
        topBar = {
            CenterAlignedTopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBackIosNew,
                            contentDescription = "Back",
                            tint = Color.Black
                        )
                    }
                },
            )
        }
    ) { scaffoldPadding ->

        when (verifyOtpData) {
            is ResourceState.Loading -> {
                Log.i(TAG, "Inside Loading State")
                Loader(message = "Registering, Please wait...")
            }

            is ResourceState.Success -> {
                LaunchedEffect(Unit) {
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(
                            CustomSnackbarVisuals(
                                containerColor = PrimaryColor,
                                message = "OTP Verified Successfully",
                                contentColor = Color.White,
                            ),
                        )
                    }
                    delay(500)
                    coroutineScope.launch {
                        navController.navigate(NavigationItem.MainView) {
                            popUpTo(NavigationItem.LoginView) {
                                inclusive = true
                            }
                        }
                        otpViewModel.resetVerifyOtpResponse()
                    }
                }
            }

            is ResourceState.Error -> {
                Log.e(
                    TAG,
                    "Inside Error State : ${(verifyOtpData as ResourceState.Error).error}"
                )
                LaunchedEffect(Unit) {
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(
                            CustomSnackbarVisuals(
                                containerColor = Color.Red,
                                message = (verifyOtpData as ResourceState.Error).error.toString(),
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
                .fillMaxSize()
                .padding(scaffoldPadding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,

            ) {

            Text(
                text = "Enter your otp code",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W600,

                    ),
            )

            Spacer(modifier = Modifier.height(32.dp))

            BasicTextField(
                value = otpCode,
                onValueChange = { newValue ->
                    otpCode = newValue
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.NumberPassword,
                    imeAction = ImeAction.Done,
                ),
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    repeat(4) { index ->
                        val number = when {
                            index >= otpCode.length -> ""
                            else -> otpCode[index].toString()
                        }
                        Text(
                            text = number,
                            modifier = Modifier
                                .width(40.dp)
                                .height(40.dp)
                                .border(
                                    1.dp, Color.Gray,
                                    RoundedCornerShape(8.dp),
                                )
                                .padding(4.dp),
                            style = TextStyle(
                                fontWeight = FontWeight.W500,
                                fontSize = 16.sp,
                            ),
                            textAlign = TextAlign.Center,
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    otpViewModel.verifyOtp(
                        otpRequest = OtpRequest(
                            otpCode = otpCode.toInt(),
                            newPassword = signUpViewModel.password.value,
                            email = signUpViewModel.email.value,
                        )
                    )
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
            ) {
                Text(
                    text = "Continue", modifier = Modifier.padding(vertical = 6.dp),
                    style = TextStyle(
                        fontWeight = FontWeight.W500,
                        fontSize = 16.sp,
                    ),
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Preview
@Composable
fun OtpViewPreview() {

}
