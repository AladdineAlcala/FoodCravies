package com.jtautomation02.foodcravies.ui.features.auth.signup

import android.util.Patterns
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.jtautomation02.foodcravies.R
import com.jtautomation02.foodcravies.common.Result
import com.jtautomation02.foodcravies.ui.FoodCraviesTextFieldComponent
import com.jtautomation02.foodcravies.ui.SocialGroupComponent
import com.jtautomation02.foodcravies.ui.theme.Primary

@Composable
fun SignUpScreen(
    navController: NavController,
    viewModel: SignUpViewModel = hiltViewModel()
){
    val fullName by viewModel.fullName.collectAsStateWithLifecycle()
    val email by viewModel.email.collectAsStateWithLifecycle()
    val password by viewModel.password.collectAsStateWithLifecycle()
    var isPasswordVisible by remember { mutableStateOf(false) }

    var fullNameError by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    val registerState by viewModel.registerState.collectAsStateWithLifecycle()
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(registerState) {
        when (val result = registerState) {
            is Result.Error -> {
                isLoading = false
                snackBarHostState.showSnackbar(result.message)
            }
            is Result.Success -> {
                isLoading = false
            }
            Result.Loading -> {
                isLoading = true
            }
            Result.Idle -> {}
        }
    }

    Box(modifier = Modifier.fillMaxSize()){
        Image(
            painter = painterResource(id = R.drawable.sign_bg),
            contentDescription =null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
        Scaffold(snackbarHost = { SnackbarHost(snackBarHostState) },
            containerColor = Color.Transparent) { it ->
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(modifier = Modifier.weight(1f))
                Text(text = stringResource(R.string.sign_up),
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth(),
                )
                Spacer(modifier = Modifier.height(20.dp))
                FoodCraviesTextFieldComponent(
                    value = fullName,
                    onValueChange = {
                        viewModel.setFullName(it)
                        fullNameError = null
                    },
                    label ={
                        Text(text = stringResource(R.string.fullname),
                            color = Color.Black.copy(alpha = 0.8f)
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    isError = fullNameError != null,
                    supportingText = {
                        if (fullNameError != null) {
                            Text(text = fullNameError!!, color = MaterialTheme.colorScheme.error)
                        }
                    }
                )

                FoodCraviesTextFieldComponent(
                    value = email,
                    onValueChange = {
                        viewModel.setEmail(it)
                        emailError = null
                    },
                    label ={
                        Text(text = stringResource(R.string.email),
                            color = Color.Black.copy(alpha = 0.8f)
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    isError = emailError != null,
                    supportingText = {
                        if (emailError != null) {
                            Text(text = emailError!!, color = MaterialTheme.colorScheme.error)
                        }
                    }
                )

                FoodCraviesTextFieldComponent(password,
                    onValueChange = {
                        viewModel.setPassword(it)
                        passwordError = null
                    },
                    label ={
                        Text(text = stringResource(R.string.password),
                            color = Color.Black.copy(alpha = 0.8f)
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        val image = if (isPasswordVisible) R.drawable.ic_eye_slash else R.drawable.ic_eye
                        Image(painter = painterResource(id = image),
                            contentDescription = "Toggle password visibility",
                            modifier = Modifier.clickable { isPasswordVisible = !isPasswordVisible }.size(24.dp))
                    },
                    isError = passwordError != null,
                    supportingText = {
                        if (passwordError != null) {
                            Text(text = passwordError!!, color = MaterialTheme.colorScheme.error)
                        }
                    }
                )

                Button(onClick = {
                    var hasError = false

                    if (fullName.isBlank()) {
                        fullNameError = "Full name cannot be empty."
                        hasError = true
                    }

                    if (email.isBlank()) {
                        emailError = "Email cannot be empty."
                        hasError = true
                    } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        emailError = "Please enter a valid email address."
                        hasError = true
                    }

                    if (password.isBlank()) {
                        passwordError = "Password cannot be empty."
                        hasError = true
                    }

                    if (!hasError) {
                        viewModel.onRegister(fullName, email, password)
                    }
                },
                    modifier =Modifier.height(48.dp),
                    enabled = !isLoading,
                    colors = ButtonDefaults.buttonColors(containerColor = Primary)
                )
                {
                    AnimatedContent(
                        targetState = isLoading,
                        transitionSpec = {
                            (fadeIn() + scaleIn()).togetherWith(fadeOut() + scaleOut())
                        },
                        label = "Loading Animation"
                    ) {
                        if (it) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = Color.White,
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text(text = stringResource(R.string.sign_up)
                                , color = Color.White,
                                modifier = Modifier.padding(horizontal = 32.dp)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = buildAnnotatedString {
                        append("Already have an account? ")
                        withStyle(style = SpanStyle(color = Color.Black, fontWeight = FontWeight.Bold)) {
                            append("Sign In")
                        }
                    },
                    textAlign = TextAlign.Center,
                    color = Color.Black.copy(alpha = 0.8f),
                    modifier = Modifier
                        .clickable { /* TODO */ }
                        .fillMaxWidth(),

                )
                SocialGroupComponent(
                    color = Color.Black,
                    onFaceBookClick = {/* Todo */}
                    ) {

                }
            }
        }
    }
}
