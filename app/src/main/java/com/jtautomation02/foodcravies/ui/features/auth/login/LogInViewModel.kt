package com.jtautomation02.foodcravies.ui.features.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jtautomation02.foodcravies.NavigationEvents
import com.jtautomation02.foodcravies.common.Result
import com.jtautomation02.foodcravies.model.LoginUserRequest
import com.jtautomation02.foodcravies.model.LoginUserResponse
import com.jtautomation02.foodcravies.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(
    private val loginRepository: LoginRepository
) : ViewModel() {

    private val _logInState = MutableStateFlow<Result<LoginUserResponse>>(Result.Idle)
    val loginState = _logInState.asStateFlow()
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _navigationEvents = MutableSharedFlow<NavigationEvents>()
    val navigationEvents = _navigationEvents.asSharedFlow()

     val email = MutableStateFlow("")
     val password = MutableStateFlow("")

    fun setEmail(value: String) {
        email.value = value
    }
    fun setPassword(value: String) {
        password.value = value
    }

    fun onLogin(email: String, password: String) {
        if(isLoading.value) return
        viewModelScope.launch {
            _logInState.value = Result.Loading
            val result = loginRepository.login(LoginUserRequest(email, password))
            when (result) {
                is Result.Success -> {
                    _logInState.value = result
                    _navigationEvents.emit(NavigationEvents.NavigateToHome)
                }
                is Result.Error -> {
                    _logInState.value = result
                }
                else -> {}
            }

        }
    }
}
