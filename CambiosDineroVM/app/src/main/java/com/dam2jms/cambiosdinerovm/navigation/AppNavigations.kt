package com.dam2jms.cambiosdinerovm.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dam2jms.cambiosdinerovm.screens.cambiosScreenState
import com.dam2jms.cambiosdinerovm.ui.ViewModelCambios

@Composable
fun appNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppScreens.CambiosScreen.route) {
        composable(route = AppScreens.CambiosScreen.route) { cambiosScreenState(navController, mvvm = ViewModelCambios()) }
    }
}