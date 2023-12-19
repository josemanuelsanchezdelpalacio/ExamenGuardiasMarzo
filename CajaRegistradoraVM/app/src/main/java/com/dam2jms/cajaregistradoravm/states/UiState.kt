package com.dam2jms.cajaregistradoravm.states

import com.dam2jms.cajaregistradoravm.R

data class UiState(
    var importe: Double = 0.0,
    var pago: Double = 0.0,
    var listaCambios: MutableMap<Int, Int> = mutableMapOf()
)