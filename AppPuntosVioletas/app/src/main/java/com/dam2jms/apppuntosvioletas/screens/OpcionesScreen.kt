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
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.dam2jms.apppuntosvioletas.models.ViewModelFirstScreen
import com.dam2jms.apppuntosvioletas.models.ViewModelOpcionesScreen
import com.dam2jms.apppuntosvioletas.navigation.AppScreens
import com.dam2jms.apppuntosvioletas.states.UiState
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OpcionesScreen(navController: NavHostController, mvvm: ViewModelOpcionesScreen) {

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
            opcionesScreenBodyContent(modifier = Modifier.padding(paddingValues), mvvm, navController, uiState)
        }
    }
}

@Composable
fun opcionesScreenBodyContent(modifier: Modifier, mvvm: ViewModelOpcionesScreen, navController: NavHostController, uiState: UiState) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        VideoPlayer()
    }
}

@Composable
fun VideoPlayer(){
    val videoURL = "https://www.youtube.com/watch?v=orCO55yZc-c"
    val playWhenReady by remember { mutableStateOf(true) }

    val context = LocalContext.current

    val player = SimpleExoPlayer.Builder(context).build()
    val playerView = PlayerView(context)
    val mediaItem = MediaItem.fromUri(videoURL)

    player.setMediaItem(mediaItem)
    playerView.player = player
    LaunchedEffect(player){
        player.prepare()
        player.playWhenReady = playWhenReady
    }
    AndroidView(factory = {playerView})
}