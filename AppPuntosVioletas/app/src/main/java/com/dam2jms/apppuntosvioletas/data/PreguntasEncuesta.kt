package com.dam2jms.apppuntosvioletas.data

data class Pregunta(val enunciado: String, val opciones: List<String>)

class PreguntasEncuesta {
    val preguntas: List<Pregunta> = listOf(
        Pregunta("1. ¿Te sientes satisfecho/a con la comunicación en tu relación?", listOf("Sí", "No", "No estoy seguro/a")),
        Pregunta("2. ¿Consideras que ambos comparten equitativamente las responsabilidades en la relación?", listOf("Sí", "No", "En parte")),
        Pregunta("3. ¿Cómo describirías el nivel de confianza que tienes en tu pareja?", listOf("Alto", "Moderado", "Bajo")),
        Pregunta("4. ¿Han discutido alguna vez sobre metas y planes a largo plazo como pareja?", listOf("Sí", "No", "No aplica")),
        Pregunta("5. ¿Sientes que hay suficiente espacio para la individualidad dentro de la relación?", listOf("Sí", "No", "A veces"))
    )
}