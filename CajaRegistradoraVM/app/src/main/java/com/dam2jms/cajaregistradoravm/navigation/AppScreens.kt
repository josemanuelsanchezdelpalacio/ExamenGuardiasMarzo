package com.dam2jms.cajaregistradoravm.navigation
sealed class AppScreens (val route: String){
    object CambiosScreen: AppScreens(route = "cambios_screen")
}
