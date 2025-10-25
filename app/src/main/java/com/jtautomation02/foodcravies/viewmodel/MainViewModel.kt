package com.jtautomation02.foodcravies.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jtautomation02.foodcravies.repository.SessionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


data class TokenSession(
    val token: String? = null,
    val refreshToken: String? = null,
    val expiresIn: Long? = null
)
@HiltViewModel
class MainViewModel @Inject constructor(
    private val sessionRepository: SessionRepository
): ViewModel() {
    // This is the single source of truth for authentication state
    val authState: StateFlow<AuthState> = sessionRepository.userTokenFlow
        .map { token ->
            if (token.isNullOrBlank()) {
                AuthState.LOGGED_OUT
            } else {
                AuthState.LOGGED_IN
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = AuthState.LOADING // Start as loading
        )

    fun onLoginSuccess(tokenSession: TokenSession) {
        viewModelScope.launch {
            sessionRepository.saveUserSession(tokenSession.token ?: "",tokenSession.refreshToken ?: "",tokenSession.expiresIn ?: 0L)
        }
    }

    fun onLogout() {
        viewModelScope.launch {
            sessionRepository.clearUserSession()
        }
    }
}

enum class AuthState {
    LOADING,      // Initial state, checking DataStore
    LOGGED_IN,    // Token exists
    LOGGED_OUT    // No token
}