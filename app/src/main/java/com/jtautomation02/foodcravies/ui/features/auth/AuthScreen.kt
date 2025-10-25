package com.jtautomation02.foodcravies.ui.features.auth

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.jtautomation02.foodcravies.AUTH
import com.jtautomation02.foodcravies.LOGIN
import com.jtautomation02.foodcravies.MAIN
import com.jtautomation02.foodcravies.R
import com.jtautomation02.foodcravies.SIGNUP
import com.jtautomation02.foodcravies.ui.SocialGroupComponent
import com.jtautomation02.foodcravies.ui.theme.Primary
import com.jtautomation02.foodcravies.ui.theme.lobsterFamily
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AuthScreen(
    navController: NavController,
    viewModel: AuthScreenViewModel = viewModel()
){
//    val screenSize= remember{
//        mutableStateOf(IntSize.Zero)
//    }

    LaunchedEffect(key1 = Unit){
        viewModel.authNavigationEvent.collectLatest { event->
            when(event) {
               is AuthScreenViewModel.AuthNavigationEvent.NavigateToMain-> {
                   navController.navigate(MAIN){
                       popUpTo(AUTH){
                           inclusive=true
                       }
                   }
               }
               is AuthScreenViewModel.AuthNavigationEvent.NavigateToSignUp -> {
                   navController.navigate(SIGNUP)
               }
               is AuthScreenViewModel.AuthNavigationEvent.ShowErrorDialog -> {

               }
            }
        }
    }
    val brush = Brush.verticalGradient(
        0.0f to Color.Transparent,  // From 0% to 33% will be transparent
        0.80f to Color.Black,       // Start fading to black at 33%
        1.0f to Color.Black         // Fully black by the end
    )
    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.Black)){
        Image(painter = painterResource(
            id = R.drawable.background),
            contentDescription = null,
            modifier = Modifier
                // The image should fill the entire size of the parent Box.
                .fillMaxSize()
                .alpha(0.6f)
        )
        //Gradient
        Box(modifier = Modifier
            .matchParentSize()
            .background(brush = brush)
        )

        val glossyGradient = Brush.verticalGradient(
            colors = listOf(Color.White, Color.LightGray)
        )
        //Skip Button
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .statusBarsPadding()
                .padding(16.dp)
                .width(55.dp)
                .height(32.dp)
                .clip(CircleShape)
                .background(glossyGradient)
                .border(width = 1.dp, color = Color.White.copy(alpha = 0.5f), shape = CircleShape)
                .clickable { /* TODO: Handle skip action */ },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(R.string.Skip),
                color = Primary
            )
        }

        Column(modifier = Modifier.fillMaxSize()) {
            //Welcome Text
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 180.dp, start = 16.dp, end = 16.dp),
                horizontalAlignment = Alignment.Start
            ){
                Text(
                    text = stringResource(R.string.Welcome),
                    color = Color.White,
                    fontSize = 50.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = stringResource(R.string.app_name),
                    color = Primary,
                    fontSize = 40.sp,
                    fontFamily = lobsterFamily,
                    fontWeight = FontWeight.Normal
                )
                Text(
                    text = stringResource(R.string.app_name_description),
                    color = Color.LightGray,
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // Login UI
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SocialGroupComponent(onGoogleClick ={
                    Log.d("TAG","Google Clicked")
                }, onFaceBookClick ={
                   Log.d("TAG","FaceBook Clicked")
                })

                OutlinedButton(
                    onClick = {
                        println("print authscrim")
                        navController.navigate(SIGNUP)
                              },

                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    shape = RoundedCornerShape(24.dp),
                ) {
                    Text(text =stringResource(R.string.email_or_phone), color = Color.White)
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = buildAnnotatedString {
                        append("Already have an account? ")
                        withStyle(style = SpanStyle(color = Color.White, fontWeight = FontWeight.Bold)) {
                            append("Sign In")
                        }
                    },
                    color = Color.White.copy(alpha = 0.8f),
                    modifier = Modifier.clickable { navController.navigate(LOGIN) }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}
