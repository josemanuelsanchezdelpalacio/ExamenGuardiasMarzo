package com.dam2jms.apppuntosvioletas.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dam2jms.apppuntosvioletas.models.ViewModelEncuestaScreen
import com.dam2jms.apppuntosvioletas.models.ViewModelFirstScreen
import com.dam2jms.apppuntosvioletas.models.ViewModelOpcionesScreen
import com.dam2jms.apppuntosvioletas.models.ViewModelSOS_Screen
import com.dam2jms.apppuntosvioletas.screens.FirstScreen
import com.dam2jms.apppuntosvioletas.screens.OpcionesScreen
import com.dam2jms.apppuntosvioletas.screens.SOS_Screen
import com.dam2jms.apppuntosvioletas.screens.encuestaScreen
import com.dam2jms.apppuntosvioletas.states.UiState

@Composable
fun appNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppScreens.FirstScreen.route) {
        composable(route = AppScreens.FirstScreen.route) { FirstScreen(navController, mvvm = ViewModelFirstScreen()) }
        composable(route = AppScreens.EncuestaScreen.route) { encuestaScreen(navController, mvvm = ViewModelEncuestaScreen()) }
        composable(route = AppScreens.SOS_Screen.route) { SOS_Screen(navController, mvvm = ViewModelSOS_Screen()) }
        composable(route = AppScreens.OpcionesScreen.route) { OpcionesScreen(navController, mvvm = ViewModelOpcionesScreen()) }
    }
}
