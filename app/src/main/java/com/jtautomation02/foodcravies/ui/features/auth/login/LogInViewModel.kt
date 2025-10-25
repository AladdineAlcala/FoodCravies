package com.jtautomation02.foodcravies.ui.features.auth.login


import android.content.Context
import android.credentials.GetCredentialException
import android.util.Log
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.jtautomation02.foodcravies.NavigationEvents
import com.jtautomation02.foodcravies.common.Result
import com.jtautomation02.foodcravies.model.GoogleSignInAccount
import com.jtautomation02.foodcravies.model.LoginUserRequest
import com.jtautomation02.foodcravies.model.LoginUserResponse
import com.jtautomation02.foodcravies.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject





@HiltViewModel
class LogInViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    @ApplicationContext private val application: Context
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
                    _navigationEvents.emit(NavigationEvents.NavigateMain)
                }
                is Result.Error -> {
                    _logInState.value = result
                }
                else -> {}
            }

        }
    }

    /**
     * STEP 1: Creates the initial sign-in request for RETURNING users.
     */
    fun createSignInRequestForReturningUsers(serverClientId: String): GetCredentialRequest {
        val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
            // IMPORTANT: This is the key. 'true' looks for returning users.
            .setFilterByAuthorizedAccounts(true)
            .setServerClientId(serverClientId)
            .build()

        return GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()
    }

    /**
     * STEP 2: Creates the fallback sign-in request for NEW users if the first one fails.
     */
    fun createSignInRequestForNewUsers(serverClientId: String): GetCredentialRequest {
        val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
            // IMPORTANT: 'false' allows the user to pick from any Google account.
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(serverClientId)
            .build()

        return GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()
    }

    /**
     * Creates the Google Sign-In request object.
     * This is called by the UI just before launching the flow.
     */
    fun createSignInRequest(serverClientId: String): GetCredentialRequest {
        val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
            // false = show all accounts, true = "One Tap" returning users only
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(serverClientId)
            .build()

        return GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()
    }

    /**
     * Handles the successful result from Credential Manager.
     * This is called by the UI after the flow succeeds.
     */
    fun handleSignInSuccess(result: GetCredentialResponse) {
        val credential = result.credential
        if (credential is CustomCredential &&
            credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {

            try {
                val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                val googleSignInAccount = GoogleSignInAccount(
                    token =  googleIdTokenCredential.idToken,
                    displayName =  googleIdTokenCredential.displayName ?:"",
                    profileImageUrl = googleIdTokenCredential.profilePictureUri?.toString()?:""
                )
                println(googleSignInAccount)
                // 🚀 NOW send this token to your backend in a coroutine
                viewModelScope.launch {
                    _logInState.value = Result.Loading
                    Log.d("GoogleSignIn", "Got token! Authenticating with backend...")
                    if(googleSignInAccount.token !=null) {
                        val result= loginRepository.loginWithGoogle(googleSignInAccount)
                        when (result) {
                            is Result.Success -> {
                                _logInState.value = result
                                _navigationEvents.emit(NavigationEvents.NavigateMain)
                            }
                            is Result.Error -> {
                                _logInState.value = result
                            }
                            else -> {}
                        }
                    }
                    else{
                        _logInState.value = Result.Error("Something went wrong")
                    }
                }

            } catch (e: Exception) {
                Log.e("GoogleSignIn", "Failed to parse credential: ${e.message}", e)
                // Update UI state to "Error"
            }
        } else {
            Log.e("GoogleSignIn", "Received an unexpected credential type: ${credential.type}")
            // Update UI state to "Error"
        }
    }
    /**
     * Handles any failure, including the user canceling the dialog.
     */
    fun handleSignInFailure(e: GetCredentialException) {
        // This includes GetCredentialCancellationException when the user taps outside
        Log.e("GoogleSignIn", "Sign-in failed: ${e.message}")
        // Update UI state to "Error" or "Idle"
        _logInState.value = Result.Error( "Sign-in failed: ${e.message}")
    }

}