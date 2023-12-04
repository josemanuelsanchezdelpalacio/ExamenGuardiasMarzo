package com.dam2jms.cambiosdinerovm.states

data class UiState(
    var importe: Double = 0.0,
    var pagado: Double = 0.0,
    var cantidad: Int = 0
)
