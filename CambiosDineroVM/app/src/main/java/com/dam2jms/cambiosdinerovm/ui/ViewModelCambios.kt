package com.dam2jms.cambiosdinerovm.ui

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.dam2jms.cambiosdinerovm.states.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ViewModelCambios : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun onChange(importe: Double, pagado: Double){
        _uiState.update {
                currentState -> currentState.copy(importe = importe, pagado = pagado)
        }
    }

    fun cambios(importe: Double, billetesMonedas: List<Int>, listaCambios: MutableMap<Int, Int>) {
        var cambio = _uiState.value.pagado - _uiState.value.pagado

        for (billete in billetesMonedas) {
            val cantidad = (cambio / billete).toInt()
            if (cantidad > 0) {
                cambio %= billete
                listaCambios[billete] = cantidad
            }
        }
    }

    /*val importe: Double = 66.75
    val pagado: Double = 100.0
    val billetesMonedas = listOf(100, 50, 20, 10, 5, 2, 1)
    val listaCambios = mutableMapOf<Int, Int>()

    fun cambios(importe: Double, billetesMonedas: List<Int>, listaCambios: MutableMap<Int, Int>) {
        var cambio = pagado - importe

        for (billete in billetesMonedas) {
            val cantidad = (cambio / billete).toInt()
            if (cantidad > 0) {
                cambio %= billete
                listaCambios[billete] = cantidad.toInt()
            }
        }
    }

    cambios(importe, billetesMonedas, listaCambios)
    for((billete, cantidad) in listaCambios){
        println("$cantidad de $billete")
    }*/

}