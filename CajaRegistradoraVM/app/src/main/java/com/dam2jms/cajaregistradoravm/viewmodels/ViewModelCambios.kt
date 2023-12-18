package com.dam2jms.cajaregistradoravm.viewmodels

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.dam2jms.cajaregistradoravm.states.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import com.dam2jms.cajaregistradoravm.R

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
            listaCambios[billete] = cantidad
            cambio %= billete
        }

        _uiState.value.resultado = buildString {
            for ((billete, cantidad) in listaCambios) {
                when (billete) {
                    100 -> { _uiState.value.imagen = R.drawable.billete_cien }
                    50 -> { _uiState.value.imagen = R.drawable.billete_cincuenta }
                    20 -> { _uiState.value.imagen = R.drawable.billete_veinte }
                    10 -> { _uiState.value.imagen = R.drawable.billete_diez }
                    5 -> { _uiState.value.imagen = R.drawable.billete_cinco }
                    2 -> { _uiState.value.imagen = R.drawable.moneda_dos }
                    else -> { _uiState.value.imagen = R.drawable.moneda_uno }
                }
                append("$cantidad de $billete\n")
            }
        }

        _uiState.update { currentState ->
            currentState.copy(resultado = _uiState.value.resultado)
        }
    }
}