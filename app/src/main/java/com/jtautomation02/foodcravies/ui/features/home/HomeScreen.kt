package com.jtautomation02.foodcravies.ui.features.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController

@Composable
fun HomeScreen(){
    Column(modifier = Modifier.fillMaxSize())  {
        Text(text = "Home Screen")
    }
}