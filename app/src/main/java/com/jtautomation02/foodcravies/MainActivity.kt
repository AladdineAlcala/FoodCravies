package com.jtautomation02.foodcravies

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.OvershootInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.jtautomation02.foodcravies.ui.features.auth.signup.SignUpScreen
import com.jtautomation02.foodcravies.ui.theme.FoodCraviesTheme
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
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding))
                    SignUpScreen()
                }
            }
        }
        CoroutineScope(Dispatchers.IO).launch {
            delay(3000)
            keepSplash = false
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier,
        style = MaterialTheme.typography.titleLarge
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FoodCraviesTheme {
        Greeting("Android")
    }
}