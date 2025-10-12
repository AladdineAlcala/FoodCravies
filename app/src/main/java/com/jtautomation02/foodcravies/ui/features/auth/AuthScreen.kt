package com.jtautomation02.foodcravies.ui.features.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jtautomation02.foodcravies.R
import com.jtautomation02.foodcravies.ui.theme.Orange
import com.jtautomation02.foodcravies.ui.theme.lobsterFamily

@Composable
fun AuthScreen(){
    val screenSize= remember{
        mutableStateOf(IntSize.Zero)
    }
    val brush= Brush.verticalGradient(
        colors = listOf(
            Color.Transparent,
            Color.Black
        ),
        startY = screenSize.value.height.toFloat() / 3
    )
    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.Black)){
        Image(painter = painterResource(
            id = R.drawable.background),
            contentDescription = null,
            modifier = Modifier
                .onGloballyPositioned {
                    screenSize.value = it.size
                }
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
                color = Orange
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
                    color = Orange,
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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    HorizontalDivider(
                        modifier = Modifier.weight(1f),
                        color = Color.White.copy(alpha = 0.8f)
                    )
                    Text(
                        text =  stringResource(R.string.sign_in_with),
                        color = Color.White.copy(alpha = 0.8f),
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                    HorizontalDivider(
                        modifier = Modifier.weight(1f),
                        color = Color.White.copy(alpha = 0.8f)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = { /*TODO*/ },
                        shape = RoundedCornerShape(24.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                        modifier = Modifier
                            .height(54.dp)
                            .width(160.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_fb),
                            contentDescription = "Facebook",
                            modifier = Modifier.size(24.dp),
                            tint = Color.Unspecified
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text =stringResource(R.string.sign_in_with_facebook), color = Color.Black)
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Button(
                        onClick = { /*TODO*/ },
                        shape = RoundedCornerShape(24.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                        modifier = Modifier
                            .height(54.dp)
                            .width(160.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_google),
                            contentDescription = stringResource(R.string.sign_in_with_google),
                            modifier = Modifier.size(24.dp),
                            tint = Color.Unspecified
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text =stringResource(R.string.sign_in_with_google), color = Color.Black)
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedButton(
                    onClick = { /*TODO*/ },
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
                    modifier = Modifier.clickable { /* TODO */ }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AuthScreenPreview(){
    AuthScreen()
}
