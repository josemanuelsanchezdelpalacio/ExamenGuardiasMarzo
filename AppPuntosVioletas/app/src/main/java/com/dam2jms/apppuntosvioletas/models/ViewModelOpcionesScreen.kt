package com.dam2jms.apppuntosvioletas.models

import androidx.lifecycle.ViewModel
import com.dam2jms.apppuntosvioletas.states.UiState
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

    fun mostrarVideosExplicativos() {
        TODO("Not yet implemented")
    }

    fun mostrarFormularioParaEmpezar() {
        TODO("Not yet implemented")
    }
}