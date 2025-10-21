package com.jtautomation02.foodcravies

sealed class NavigationEvents {
    object NavigateToLogin : NavigationEvents()
    object NavigateToSignUp : NavigationEvents()
    object NavigateToHome : NavigationEvents()
    object NavigateToForgotPassword : NavigationEvents()
    object NavigateToResetPassword : NavigationEvents()
    object NavigateToChangePassword : NavigationEvents()
    object NavigateToProfile : NavigationEvents()
}