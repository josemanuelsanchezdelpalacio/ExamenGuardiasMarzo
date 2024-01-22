package com.dam2jms.apppuntosvioletas.data

data class Pregunta(val enunciado: String, val opciones: List<String>)

class PreguntasEncuesta {
    val preguntas: List<Pregunta> = listOf(
        Pregunta("¿Sientes que puedes expresar tus opiniones libremente en la relación?", listOf("Sí", "No")),
        Pregunta("¿Te sientes apoyado/a en tus metas y aspiraciones?", listOf("Sí", "No")),
        Pregunta("¿Hay comunicación abierta y honesta en la relación?", listOf("Sí", "No")),
        Pregunta("¿Se resuelven los conflictos de manera constructiva?", listOf("Sí", "No")),
        Pregunta("¿Sientes que tu pareja respeta tus límites personales?", listOf("Sí", "No"))
    )
}
