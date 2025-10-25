package com.jtautomation02.foodcravies.ui.features.auth.login

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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.credentials.CredentialManager
import android.credentials.GetCredentialException
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.credentials.GetCredentialResponse
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.jtautomation02.foodcravies.BuildConfig
import com.jtautomation02.foodcravies.MAIN
import com.jtautomation02.foodcravies.NavigationEvents
import com.jtautomation02.foodcravies.R
import com.jtautomation02.foodcravies.SIGNUP
import com.jtautomation02.foodcravies.common.Result
import com.jtautomation02.foodcravies.model.LoginUserResponse
import com.jtautomation02.foodcravies.ui.FoodCraviesTextFieldComponent
import com.jtautomation02.foodcravies.ui.SocialGroupComponent
import com.jtautomation02.foodcravies.ui.theme.Primary
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch



@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Composable
fun LogInScreen(
    onLoginSuccess: (logInUserResponse: LoginUserResponse) -> Unit,
    navController: NavController,
    viewModel: LogInViewModel = hiltViewModel<LogInViewModel>()
){
    val context = LocalContext.current

    val serverClientId = BuildConfig.GOOGLE_CLIENT_ID

    val email by viewModel.email.collectAsStateWithLifecycle()
    val password by viewModel.password.collectAsStateWithLifecycle()
    var isPasswordVisible by remember { mutableStateOf(false) }

    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    val loginState by viewModel.loginState.collectAsStateWithLifecycle()
    val snackBarHostState = remember { SnackbarHostState() }

    val scope = rememberCoroutineScope()

    val credentialManager = remember(context) {
        CredentialManager.create(context)
    }


    LaunchedEffect(loginState) {
        when (val result = loginState) {
            is Result.Error -> {
                isLoading = false
                snackBarHostState.showSnackbar(result.message)
            }
            is Result.Success -> {
                isLoading = false
                onLoginSuccess(result.data)
            }
            is Result.Loading -> {
                isLoading = true
            }
            Result.Idle -> {}
        }
    }


    LaunchedEffect(Unit) {
        viewModel.navigationEvents.collectLatest { event ->
            // This block will run every time a new event is emitted
            when (event) {
                is NavigationEvents.NavigateMain -> {
                    // Navigate to the Home screen and clear the entire back stack
                    // so the user cannot go back to the signup/login flow.
                    navController.navigate(MAIN) {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }
                    }
                }
               else -> {}
            }
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
                Text(
                    text = stringResource(R.string.sign_in),
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth(),
                )
                Spacer(modifier = Modifier.height(20.dp))

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
                            modifier = Modifier
                                .clickable { isPasswordVisible = !isPasswordVisible }
                                .size(24.dp))
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
                        viewModel.onLogin( email, password)
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
                            Text(text = stringResource(R.string.sign_in)
                                , color = Color.White,
                                modifier = Modifier.padding(horizontal = 32.dp)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = buildAnnotatedString {
                        append("Don't have an account? ")
                        withStyle(style = SpanStyle(color = Color.Black, fontWeight = FontWeight.Bold)) {
                            append("Sign Up")
                        }
                    },
                    textAlign = TextAlign.Center,
                    color = Color.Black.copy(alpha = 0.8f),
                    modifier = Modifier
                        .clickable {
                            navController.navigate(SIGNUP)
                        }
                        .fillMaxWidth(),

                    )
                SocialGroupComponent(
                    color = Color.Black,
                    onFaceBookClick = { },
                    onGoogleClick = {
                        // 5. Launch the coroutine on click
                        scope.launch {
                            var finalResult: GetCredentialResponse? = null
                            try {
                                // --- STEP 1: Try signing in returning users ---
                                Log.d("GoogleSignIn", "Attempting sign-in for returning users...")
                                val request = viewModel.createSignInRequestForReturningUsers(serverClientId)
                                finalResult = credentialManager.getCredential(context, request)

                            } catch (e: GetCredentialException) {
                                // --- STEP 2: If Step 1 fails with NoCredentialException, it's a new user ---
                                // We specifically check for NoCredentialException. Other exceptions are real errors.
                                if (e is androidx.credentials.exceptions.NoCredentialException) {
                                    Log.d("GoogleSignIn", "No returning user found. Launching sign-in for new users...")
                                    try {
                                        val requestForNewUser = viewModel.createSignInRequestForNewUsers(serverClientId)
                                        finalResult = credentialManager.getCredential(context, requestForNewUser)
                                    } catch (e2: GetCredentialException) {
                                        // If the second attempt also fails (e.g., user cancels), handle it.
                                        viewModel.handleSignInFailure(e2)
                                    }
                                } else {
                                    // Handle other exceptions from the first attempt (network errors, etc.)
                                    viewModel.handleSignInFailure(e)
                                }
                            }

                            // --- Process the final result ---
                            // This will only run if one of the attempts was successful.
                            finalResult?.let {
                                viewModel.handleSignInSuccess(it)
                            }
                        }
                    }
                )
            }
        }
    }
}