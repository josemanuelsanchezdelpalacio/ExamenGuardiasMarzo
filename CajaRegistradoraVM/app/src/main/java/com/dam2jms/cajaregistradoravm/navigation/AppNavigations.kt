package com.dam2jms.cajaregistradoravm.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dam2jms.cajaregistradoravm.screens.cambiosScreenState
import com.dam2jms.cajaregistradoravm.viewmodels.ViewModelCambios

@Composable
fun appNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppScreens.CambiosScreen.route) {
        composable(route = AppScreens.CambiosScreen.route) { cambiosScreenState(navController, mvvm = ViewModelCambios()) }
    }
}