package com.jtautomation02.foodcravies

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.OvershootInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jtautomation02.foodcravies.ui.features.auth.AuthScreen
import com.jtautomation02.foodcravies.ui.features.auth.signup.SignUpScreen
import com.jtautomation02.foodcravies.ui.features.home.HomeScreen
import com.jtautomation02.foodcravies.ui.theme.FoodCraviesTheme
import com.jtautomation02.foodcravies.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    var keepSplash = true
    override fun onCreate(savedInstanceState: Bundle?) {
        val showSplashScreen = installSplashScreen()
        showSplashScreen.apply {
            setKeepOnScreenCondition { keepSplash }
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
                AppNavigation()
            }
        }
        CoroutineScope(Dispatchers.IO).launch {
            delay(3000)
            keepSplash = false
        }
    }
}

@Composable
fun AppNavigation(
    mainViewModel: MainViewModel = hiltViewModel(),
){
    val navHostController = rememberNavController()
    val startDestination by mainViewModel.startDestination.collectAsState()

    NavHost(navController = navHostController, startDestination = AUTH) {
        composable<AUTH> {
            AuthScreen(navController = navHostController)
        }
        composable<HOME> {
            HomeScreen()
        }
        composable<SIGNUP> {
            SignUpScreen(navController = navHostController)
        }
    }
}

@Serializable
object AUTH

@Serializable
object HOME

@Serializable
object SIGNUP
