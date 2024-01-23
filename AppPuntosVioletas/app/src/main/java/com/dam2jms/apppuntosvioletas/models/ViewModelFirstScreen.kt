package com.dam2jms.apppuntosvioletas.models

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.dam2jms.apppuntosvioletas.states.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ViewModelFirstScreen : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun cambiarIconoYNombreDeLaApp() {
        TODO("Not yet implemented")
    }

    fun establecerContactoDeEmergencia() {
        TODO("Not yet implemented")
    }

    fun mostrarVideosExplicativos(context: Context, urlStr: String?) {


    }

    fun openWebPage() {

    }

    fun mostrarFormularioParaEmpezar() {
        TODO("Not yet implemented")
    }
}