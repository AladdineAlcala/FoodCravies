package com.jtautomation02.foodcravies

import android.animation.ObjectAnimator
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.animation.OvershootInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jtautomation02.foodcravies.model.LoginUserResponse
import com.jtautomation02.foodcravies.ui.MainAppScreen
import com.jtautomation02.foodcravies.ui.features.auth.AuthScreen
import com.jtautomation02.foodcravies.ui.features.auth.login.LogInScreen
import com.jtautomation02.foodcravies.ui.features.auth.signup.SignUpScreen
import com.jtautomation02.foodcravies.ui.features.home.HomeScreen
import com.jtautomation02.foodcravies.ui.theme.FoodCraviesTheme
import com.jtautomation02.foodcravies.viewmodel.AuthState
import com.jtautomation02.foodcravies.viewmodel.MainViewModel
import com.jtautomation02.foodcravies.viewmodel.TokenSession
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    var keepSplash = true
    private val viewModel: MainViewModel by viewModels()
    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override fun onCreate(savedInstanceState: Bundle?) {
        val showSplashScreen = installSplashScreen()
        showSplashScreen.apply {
            setKeepOnScreenCondition {
                keepSplash
                viewModel.authState.value == AuthState.LOADING
            }
            setOnExitAnimationListener { screen ->
                val zoomX= ObjectAnimator.ofFloat(screen.iconView, View.SCALE_Y,0.4f,0.0f)
                val zoomY=ObjectAnimator.ofFloat(screen.iconView,View.SCALE_X,0.4f,0.0f)
                zoomX.duration=500
                zoomY.duration=500
                zoomY.interpolator= OvershootInterpolator()
                zoomX.interpolator= OvershootInterpolator()
                zoomX.doOnEnd {
                    screen.remove()
                }
                zoomY.doOnEnd {
                    screen.remove()
                }
                zoomX.start()
                zoomY.start()
            }
        }
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FoodCraviesTheme {
                // The NavHost is now the top-level composable, allowing it to fill the entire screen.
                AppNavigation(authState = viewModel.authState)
            }
        }
        CoroutineScope(Dispatchers.IO).launch {
            delay(3000)
            keepSplash = false
        }
    }
}

@OptIn(ExperimentalTime::class)
@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Composable
fun AppNavigation(
    authState: StateFlow<AuthState>
){
    val navHostController = rememberNavController()

    // 3. Collect the state here
    val currentState by authState.collectAsStateWithLifecycle()

    // This is the root-level navigation logic
    when (currentState) {

        AuthState.LOGGED_IN -> {
            MainAppScreen()
        }
        AuthState.LOGGED_OUT -> {
            // User is logged out, show the auth flow
            NavHost(navController = navHostController, startDestination = AUTH) {
                composable<AUTH> {
                    AuthScreen(navController = navHostController)
                }

                composable<MAIN> {
                    MainAppScreen()
                }

                composable<SIGNUP> {
                    SignUpScreen(navController = navHostController)
                }
                composable<LOGIN> {
                    val mainViewModel: MainViewModel = hiltViewModel()
                    LogInScreen(navController = navHostController,
                        onLoginSuccess = { data ->
                            val token = data.accessToken
                            val refreshToken = data.refreshTokenResponse.token
                            val expiresIn = data.accessTokenExpires
                            mainViewModel.onLoginSuccess(TokenSession(token,refreshToken,parseIsoString(expiresIn)))
                        }
                   )
                }
            }
        }
        AuthState.LOADING -> {

        }
    }

}

/**
 * Parses an ISO-8601 date-time string with high-precision (nanoseconds)
 * by truncating it to milliseconds and converting it to epoch milliseconds (a Long).
 *
 * @param isoString The date-time string (e.g., "2025-10-25T15:01:18.1953498Z").
 * @return The epoch milliseconds as a Long, or null if parsing fails.
 */
fun parseIsoString(isoString: String): Long? {
    // Check if the string is long enough to truncate (e.g., "yyyy-MM-ddTHH:mm:ss.SSS")
    if (isoString.length < 23) {
        return null
    }

    try {
        // 1. Truncate the string to 3 decimal places (milliseconds)
        val truncatedString = isoString.substring(0, 23) + "Z"

        // 2. Create the SimpleDateFormat object
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
        sdf.timeZone = TimeZone.getTimeZone("UTC") // MUST set this!

        // 3. Parse the truncated string and return its time in milliseconds
        val date = sdf.parse(truncatedString)
        return date?.time
    } catch (e: ParseException) {
        // Handle parsing errors
        e.printStackTrace()
        return null
    } catch (e: IndexOutOfBoundsException) {
        // Handle strings that are too short
        e.printStackTrace()
        return null
    }
}

@Serializable
object AUTH

@Serializable
object MAIN

@Serializable
object SIGNUP

@Serializable
object LOGIN
