package com.dam2jms.loginregistrefirebase.navigation

sealed class AppScreens (val route: String){
    object LoginScreen : AppScreens(route = "login_screen")
    object HomeScreen : AppScreens(route = "home_screen")
    object RegisterScreen : AppScreens(route = "register_screen")
}
