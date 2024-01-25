package com.dam2jms.apppuntosvioletas.screens

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Camera
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialogDefaults.containerColor
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavHostController
import com.dam2jms.apppuntosvioletas.R
import com.dam2jms.apppuntosvioletas.navigation.AppScreens
import com.dam2jms.apppuntosvioletas.models.ViewModelFirstScreen
import com.dam2jms.apppuntosvioletas.states.UiState
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FirstScreen(navController: NavHostController, mvvm: ViewModelFirstScreen) {

    val uiState by mvvm.uiState.collectAsState()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(navController = navController)
        },
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("App Puntos Violetas") },
                    navigationIcon = {
                        //icono del menu para controlar la apertura y cierre del menu lateral
                        IconButton(onClick = {
                            scope.launch {
                                drawerState.apply {
                                    if (isClosed) open() else close()
                                }
                            }
                        }) {
                            Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
                        }
                    }
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { navController.navigate(route = AppScreens.SOS_Screen.route) },
                    containerColor = Color(0xFFAC53F7),
                    elevation = FloatingActionButtonDefaults.elevation()
                ) {
                    Icon(imageVector = Icons.Default.Warning, contentDescription = "SOS")
                }
            }
        ) { paddingValues ->
            firstScreenBodyContent(modifier = Modifier.padding(paddingValues), mvvm, navController, uiState)
        }
    }
}

//metodo para los items del menu lateral
@Composable
fun DrawerContent(navController: NavHostController) {
    ModalDrawerSheet {
        Image(
            modifier = Modifier
                .size(200.dp)
                .background(MaterialTheme.colorScheme.background),
            contentScale = ContentScale.Crop,
            painter = painterResource(id = R.drawable.logo_puntos_violetas),
            contentDescription = "logoPuntos"
        )
        Divider()
        NavigationDrawerItem(
            label = { Text(text = "Videos explicativos") },
            selected = false,
            onClick = { }
        )
        Divider()
        NavigationDrawerItem(
            label = { Text(text = "Encuesta") },
            selected = false,
            onClick = { navController.navigate(route = AppScreens.EncuestaScreen.route) }
        )
        Divider()
    }
}

@Composable
fun firstScreenBodyContent(
    modifier: Modifier,
    mvvm: ViewModelFirstScreen,
    navController: NavHostController,
    uiState: UiState
) {
    val defaultCameraPosition = CameraPosition.fromLatLngZoom(uiState.coordenadas, 11f)
    val cameraPositionState = rememberCameraPositionState { position = defaultCameraPosition }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(650.dp)
                .clip(MaterialTheme.shapes.medium)
        ) {
            GoogleMapView(
                modifier = Modifier.matchParentSize(),
                cameraPositionState = cameraPositionState,
                uiState = uiState
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun GoogleMapView(
    modifier: Modifier = Modifier,
    cameraPositionState: CameraPositionState = rememberCameraPositionState(),
    content: @Composable () -> Unit = {},
    uiState: UiState
) {
    val puntoVioleta1State = rememberMarkerState(position = uiState.puntoVioleta1)
    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraPositionState
    ){
        Marker(
            state =puntoVioleta1State,
            title = "puntoVioleta1"
        )
    }
}
