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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jtautomation02.foodcravies.ui.features.auth.AuthScreen
import com.jtautomation02.foodcravies.ui.features.home.HomeScreen
import com.jtautomation02.foodcravies.ui.theme.FoodCraviesTheme
import com.jtautomation02.foodcravies.viewmodel.MainViewModel
import com.jtautomation02.foodcravies.viewmodel.StartDestination
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            FoodCraviesTheme {
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
    mainViewModel: MainViewModel =viewModel(),
){
    val navController = rememberNavController()
    val startDestination by mainViewModel.startDestination.collectAsState()
    if(startDestination== StartDestination.AUTH){
        AuthScreen()
        println("Auth Screen loaded")
        return
    }

    NavHost(navController = navController, startDestination = startDestination.name.lowercase()) {
        composable(StartDestination.AUTH.name.lowercase()) {
            AuthScreen(
                onNavigateToSignUp={
                    navController.navigate(StartDestination.SIGNUP.name.lowercase())
                }
            )

        }
        composable(StartDestination.HOME.name.lowercase()) {
            HomeScreen()
        }
        composable(StartDestination.LOGIN.name.lowercase()) {

        }
    }
}