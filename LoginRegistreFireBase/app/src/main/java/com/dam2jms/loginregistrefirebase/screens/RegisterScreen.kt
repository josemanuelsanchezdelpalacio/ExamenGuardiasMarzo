package com.dam2jms.loginregistrefirebase.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(navController: NavController){
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "REGISTRO") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Atras")
                    }
                }
            )
        }
    ) { paddingValues ->
        RegisterScreenBody(modifier = Modifier.padding(paddingValues), navController)
    }
}

@Composable
fun RegisterScreenBody(modifier: Modifier, navController: NavController) {
    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var textContra by remember { mutableStateOf("") }
    var repeatPassword by remember { mutableStateOf("") }
    var contacto by remember { mutableStateOf("") }
    var familiaProfesional by remember { mutableStateOf("") }
    val context = LocalContext.current


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Correo") }
        )
        OutlinedTextField(
            value = correo,
            onValueChange = { correo = it },
            label = { Text("Correo") }
        )
        OutlinedTextField(
            value = textContra,
            onValueChange = { textContra = it },
            label = { Text("Contraseña") }
        )
        OutlinedTextField(value = repeatPassword,
            onValueChange = { repeatPassword = it },
            label = { Text(text = "Repetir contraseña") }
        )
        OutlinedTextField(value = contacto,
            onValueChange = { contacto = it },
            label = { Text(text = "Contacto") }
        )
        OutlinedTextField(value = familiaProfesional,
            onValueChange = { familiaProfesional = it },
            label = { Text(text = "Familia profesional") }
        )
        Button(
            onClick = {}
        ) {
            Text(text = "Registro usuario")
        }
    }
}
