package com.dam2jms.cambiosdinerovm.navigation

sealed class AppScreens (val route: String){
    object CambiosScreen: AppScreens(route = "cambios_screen")
}
