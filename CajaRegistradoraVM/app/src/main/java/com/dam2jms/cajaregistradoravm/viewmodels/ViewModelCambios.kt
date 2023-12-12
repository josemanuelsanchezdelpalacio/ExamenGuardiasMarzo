package com.dam2jms.cajaregistradoravm.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import com.dam2jms.cajaregistradoravm.states.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ViewModelCambios : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun onChange(importe: Double, pago: Double) {
        _uiState.update { currentState ->
            currentState.copy(importe = importe, pago = pago)
        }
    }

    fun cambios(context: Context) {
        val billetesMonedas = listOf(100, 50, 20, 10, 5, 2, 1)
        val listaCambios = mutableMapOf<Int, Int>()

        var cambio = _uiState.value.pago - _uiState.value.importe

        for (billete in billetesMonedas) {
            val cantidad = (cambio / billete).toInt()
            if (cantidad > 0) {
                cambio %= billete
                listaCambios[billete] = cantidad
            }
        }

        _uiState.value.resultado = buildString {
            for ((billete, cantidad) in listaCambios) {
                append("$cantidad de $billete\n")
            }
        }

        _uiState.update { currentState ->
            currentState.copy(resultado = _uiState.value.resultado)
        }
    }
}