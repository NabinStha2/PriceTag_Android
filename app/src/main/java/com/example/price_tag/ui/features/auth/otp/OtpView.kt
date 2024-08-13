package com.example.price_tag.ui.features.auth.otp

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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

@Composable
fun OtpView(
    navController: NavController = rememberNavController(),
    otpViewModel: OtpViewModel = hiltViewModel(),
) {
    var TAG = "OtpView"

    var otpCode by remember {
        mutableStateOf("")
    }

    val loginResponse by otpViewModel.loginData.collectAsState()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .safeContentPadding(),
        containerColor = Color.White,
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
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
//                    otpViewModel.login(
//                        loginRequest = LoginRequest(
//                            email = email, password = password
//                        )
//                    )
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