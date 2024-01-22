package com.dam2jms.apppuntosvioletas.models

import androidx.lifecycle.ViewModel
import com.dam2jms.apppuntosvioletas.states.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ViewModelFirstScreen : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    /*fun updateUbicacion(location: LatLng) {
        _uiState.value = _uiState.value.copy(ubicacion = location)
    }*/

    fun setSurveyButtonClicked(clicked: Boolean) {
        _uiState.value = _uiState.value.copy(isBotonEncuesta = clicked)
    }

    fun setSOSButtonClicked(clicked: Boolean) {
        _uiState.value = _uiState.value.copy(isBotonSOS = clicked)
    }

    fun setDropdownMenuExpanded(expanded: Boolean) {
        _uiState.value = _uiState.value.copy(isMenuDesplegable = expanded)
    }
}