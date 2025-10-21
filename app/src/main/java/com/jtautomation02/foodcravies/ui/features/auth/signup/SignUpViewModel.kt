package com.jtautomation02.foodcravies.ui.features.auth.signup

import com.jtautomation02.foodcravies.common.Result
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jtautomation02.foodcravies.NavigationEvents
import com.jtautomation02.foodcravies.model.RegisterUserRequest
import com.jtautomation02.foodcravies.model.RegisterUserResponse
import com.jtautomation02.foodcravies.repository.RegisterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val registerRepository: RegisterRepository
) : ViewModel() {

    private val _registerState = MutableStateFlow<Result<RegisterUserResponse>>(Result.Idle)
    val registerState = _registerState.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _navigationEvents = MutableSharedFlow<NavigationEvents>()
    val navigationEvents = _navigationEvents.asSharedFlow()

     val email = MutableStateFlow("")
     val password = MutableStateFlow("")
//     val confirmPassword = MutableStateFlow("")

     val fullName = MutableStateFlow("")

    fun setEmail(value: String) {
        email.value = value
    }
    fun setPassword(value: String) {
        password.value = value
    }

//    fun setConfirmPassword(value: String) {
//        confirmPassword.value = value
//    }

    fun setFullName(value: String) {
        fullName.value = value
    }

    fun onRegister(fullName:String, email: String, password: String) {
        if(isLoading.value) return
        viewModelScope.launch {
            _registerState.value = Result.Loading
            when (val result = registerRepository.register(
                RegisterUserRequest(
                    username = fullName,
                    email = email,
                    password = password
                ))) {
                is Result.Success -> {
                    _registerState.value = result
                    _navigationEvents.tryEmit(NavigationEvents.NavigateToHome)
                }
                is Result.Error -> {
                    _registerState.value = result
                }
                else -> {}
            }
            _isLoading.value = false
        }
    }
}
