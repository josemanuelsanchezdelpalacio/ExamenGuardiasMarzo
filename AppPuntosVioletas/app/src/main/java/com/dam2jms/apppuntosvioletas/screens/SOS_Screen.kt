package com.dam2jms.apppuntosvioletas.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.dam2jms.apppuntosvioletas.models.ViewModelSOS_Screen
import com.dam2jms.apppuntosvioletas.navigation.AppScreens

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SOS_Screen(navController: NavHostController, mvvm: ViewModelSOS_Screen) {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFAC53F7),
                    titleContentColor = Color.White,
                ),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Atrás")
                    }
                },
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        // Menú en el centro
                        IconButton(onClick = { navController.navigate(route = AppScreens.OpcionesScreen.route) }) {
                            Icon(imageVector = Icons.Default.Menu, contentDescription = "Menú")
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(route = AppScreens.SOS_Screen.route) },
                containerColor = Color(0xFFAC53F7),
                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
            ) {
                Icon(imageVector = Icons.Default.Warning, contentDescription = "SOS")
            }
        },
    ) { paddingValues ->
        SOS_ScreenBodyContent(modifier = Modifier.padding(paddingValues), mvvm, navController)
    }
}


@Composable
fun SOS_ScreenBodyContent(modifier: Modifier, mvvm: ViewModelSOS_Screen, navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { mvvm.llamada112() }) {
            Text("Llamada 112")
        }
        Button(onClick = { mvvm.alarma() }) {
            Text("Alarma")
        }
        Button(onClick = { mvvm.mandarUbicacionContacto() }) {
            Text("Mandar ubicacion a contacto")
        }
    }
}

