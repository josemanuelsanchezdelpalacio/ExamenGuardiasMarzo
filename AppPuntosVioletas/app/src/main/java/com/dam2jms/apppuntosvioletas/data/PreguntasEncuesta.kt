package com.dam2jms.apppuntosvioletas.data

data class Pregunta(val enunciado: String, val opciones: List<String>)

class PreguntasEncuesta {
    val preguntas: List<Pregunta> = listOf(
        Pregunta("pregunta1", listOf("Sí", "No")),
        Pregunta("pregunta2", listOf("Sí", "No")),
        Pregunta("pregunta3", listOf("Sí", "No")),
        Pregunta("pregunta4", listOf("Sí", "No")),
        Pregunta("pregunta5", listOf("Sí", "No"))
    )
}
