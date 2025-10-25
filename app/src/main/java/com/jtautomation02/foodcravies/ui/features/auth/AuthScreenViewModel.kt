package com.jtautomation02.foodcravies.ui.features.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthScreenViewModel @Inject constructor(
) : ViewModel() {

    private val _uiState= MutableStateFlow<AuthEvent>(AuthEvent.Nothing)
     val uiState = _uiState.asStateFlow()


    private val _authNavigationEvent = MutableSharedFlow<AuthNavigationEvent>()
     val  authNavigationEvent = _authNavigationEvent.asSharedFlow()

    fun loading(){
        viewModelScope.launch {
            _uiState.value=AuthEvent.Loading
        }
    }

    sealed class AuthNavigationEvent {
        object NavigateToSignUp : AuthNavigationEvent()
        object NavigateToMain : AuthNavigationEvent()
        object ShowErrorDialog : AuthNavigationEvent()
    }

    sealed class AuthEvent {
        object Nothing : AuthEvent()
        object Success : AuthEvent()
        object Error : AuthEvent()
        object Loading : AuthEvent()
    }

}