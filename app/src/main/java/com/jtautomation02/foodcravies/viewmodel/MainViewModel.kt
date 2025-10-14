package com.jtautomation02.foodcravies.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
): ViewModel() {
    private val _startDestination = MutableStateFlow(StartDestination.AUTH)
    val startDestination = _startDestination.asStateFlow()
    var isLoggedIn = false
    init {
        viewModelScope.launch {
            //change implementation when connected to auth_manager
            logSimulation()
            _startDestination.value = if (isLoggedIn) {
                StartDestination.HOME
            } else {
                StartDestination.AUTH
            }
        }
    }

   suspend fun logSimulation(){
       delay(2000)
       println("Logging in at MainViewModel: $isLoggedIn")
    }
}

enum class StartDestination {
    AUTH,    // The initial loading/checking screen
    HOME,      // The main content screen
    LOGIN,      // The login screen
    SIGNUP    // The signup screen
}
