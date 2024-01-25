package com.dam2jms.apppuntosvioletas.models

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.dam2jms.apppuntosvioletas.states.UiState
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ViewModelOpcionesScreen : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun cambiarIconoYNombreDeLaApp() {
        TODO("Not yet implemented")
    }

    fun establecerContactoDeEmergencia() {
        TODO("Not yet implemented")
    }

    fun mostrarFormularioParaEmpezar() {
        TODO("Not yet implemented")
    }
}