package com.example.price_tag.ui.features.auth.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.price_tag.ui.navigation.NavigationItem
import com.example.price_tag.ui.theme.PrimaryColor

@Composable
fun SignUpView(navController: NavController = rememberNavController()) {
    var userName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var obscurePassword by remember { mutableStateOf(true) }
    var obscureConfirmPassword by remember { mutableStateOf(true) }

    Surface(
        color = Color.White,
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
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
                value = userName,
                onValueChange = { userName = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text(text = "User Name") },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "UserName"
                    )
                }

            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text(text = "Email") },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = "Email"
                    )
                }

            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text(text = "Password") },
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
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text(text = "Confirm Password") },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
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
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryColor),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
            ) {
                Text(text = "SIGN UP", modifier = Modifier.padding(vertical = 6.dp),style = TextStyle(
                    fontWeight = FontWeight.W500,
                    fontSize = 16.sp,

                    ))

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
                            popUpTo(NavigationItem.SignUpView.route) {
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