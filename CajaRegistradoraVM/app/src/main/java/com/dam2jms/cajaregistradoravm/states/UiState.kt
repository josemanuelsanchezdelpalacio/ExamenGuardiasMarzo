package com.dam2jms.cajaregistradoravm.states

data class UiState(
    var importe: Double = 0.0,
    var pago: Double = 0.0,
    var cantidad: Int = 0,
    var resultado: String = ""
)
