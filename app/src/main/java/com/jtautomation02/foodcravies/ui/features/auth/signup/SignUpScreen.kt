package com.jtautomation02.foodcravies.ui.features.auth.signup

import android.util.Log
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.jtautomation02.foodcravies.R
import com.jtautomation02.foodcravies.ui.FoodCraviesTextFieldComponent
import com.jtautomation02.foodcravies.ui.SocialGroupComponent
import com.jtautomation02.foodcravies.ui.theme.Primary

@Composable
fun SignUpScreen(){
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()){
        Image(
            painter = painterResource(id = R.drawable.sign_bg),
            contentDescription =null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
        Column(modifier = Modifier
            .fillMaxSize()
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
            FoodCraviesTextFieldComponent(value = fullName,
                onValueChange = {fullName=it},
                label ={
                    Text(text = stringResource(R.string.fullname),
                        color = Color.Black.copy(alpha = 0.8f)
                    )
                },
                modifier = Modifier.fillMaxWidth(),
            )

            FoodCraviesTextFieldComponent(email,
                onValueChange = {email=it},
                label ={
                    Text(text = stringResource(R.string.email),
                        color = Color.Black.copy(alpha = 0.8f)
                    )
                },
                modifier = Modifier.fillMaxWidth(),
            )

            FoodCraviesTextFieldComponent(password,
                onValueChange = {password=it},
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
                }
            )

            Button(onClick = {
                Log.d("SignUpScreen", "Full Name: $fullName, Email: $email, Password: $password")
            },
                modifier =Modifier.height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Primary)
            )
            {
                Text(text = stringResource(R.string.sign_up)
                    , color = Color.White,
                    modifier = Modifier.padding(horizontal = 32.dp)
                )
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


@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview(){
    SignUpScreen()

}
