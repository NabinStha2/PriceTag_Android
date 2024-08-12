package com.example.price_tag.ui.features.auth.otp

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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.price_tag.R
import com.example.price_tag.data.model.auth.LoginRequest
import com.example.price_tag.ui.navigation.NavigationItem
import com.example.price_tag.ui.theme.PrimaryColor

@Composable
fun OtpView(
    navController: NavController = rememberNavController(),
//    otpViewModel: OtpViewModel = hiltViewModel(),
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var obscurePassword by remember { mutableStateOf(true) }

//    val loginResponse by otpViewModel.loginData.collectAsState()


    var TAG = "OtpView"

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

            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "logo",
                modifier = Modifier.width(200.dp),
            )
            Spacer(modifier = Modifier.height(32.dp))
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Divider(modifier = Modifier.weight(1f))
                Spacer(modifier = Modifier.widthIn(8.dp))
                Text(text = "OR")
                Spacer(modifier = Modifier.widthIn(8.dp))
                Divider(modifier = Modifier.weight(1f))


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
                        navController.navigate(NavigationItem.SignUpView.route) {
                            popUpTo(NavigationItem.LoginView.route) {
                                inclusive = true
                            }
                        }
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

@Preview
@Composable
fun OtpViewPreview() {
    OtpView()
}