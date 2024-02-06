@file:Suppress("UNCHECKED_CAST")

package com.dam2jms.apppuntosvioletas.screens

import com.dam2jms.apppuntosvioletas.data.PreguntasEncuesta
import com.dam2jms.apppuntosvioletas.models.ViewModelEncuestaScreen
import com.dam2jms.apppuntosvioletas.navigation.AppScreens
import com.dam2jms.apppuntosvioletas.states.UiState
import android.graphics.Paint
import android.graphics.Typeface
import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.asComposePaint
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.dam2jms.apppuntosvioletas.data.Pregunta
import kotlinx.coroutines.launch
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EncuestaScreen(navController: NavHostController, mvvm: ViewModelEncuestaScreen) {

    val uiState by mvvm.uiState.collectAsState()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(navController = navController)
        },
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("App Puntos Violetas") },
                    navigationIcon = {
                        //icono del menu para controlar la apertura y cierre del menu lateral
                        IconButton(onClick = {
                            scope.launch {
                                drawerState.apply {
                                    if (isClosed) open() else close()
                                }
                            }
                        }) {
                            Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
                        }
                    }
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { navController.navigate(route = AppScreens.SOS_Screen.route) },
                    containerColor = Color(0xFFAC53F7),
                    elevation = FloatingActionButtonDefaults.elevation()
                ) {
                    Icon(imageVector = Icons.Default.Warning, contentDescription = "SOS")
                }
            }
        ) { paddingValues ->
            EncuestaScreenBodyContent(
                modifier = Modifier.padding(paddingValues),
                mvvm,
                navController,
                uiState
            )
        }
    }
}

@Composable
fun EncuestaScreenBodyContent(modifier: Modifier, mvvm: ViewModelEncuestaScreen, navController: NavHostController, uiState: UiState) {
    val showPentagon = remember { mutableStateOf(false) }
    val statistics = remember { mutableStateOf<List<Int>>(listOf()) }
    val currentQuestionIndex = remember { mutableStateOf(0) }

    val preguntasEncuesta = PreguntasEncuesta()
    val preguntasRelacion = preguntasEncuesta.preguntas
    val context = LocalContext.current
    val estadisticas = mutableListOf<Int>()
    // Inicializa la lista de respuestas pentagonales con valores de 0
    estadisticas.addAll(List(preguntasRelacion.size) { 0 })

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (showPentagon.value) {
            Box(modifier = Modifier.size(300.dp)) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val center = Offset(size.width / 2, size.height / 2)
                    val radius = min(size.width, size.height) / 2

                    // Dibujar el pentágono de estadísticas
                    drawStatisticPentagon(
                        this,
                        center,
                        radius,
                        statistics.value
                    )

                    // Dibujar el pentágono completo
                    drawFullPentagon(
                        this,
                        center,
                        radius,
                        preguntasRelacion[currentQuestionIndex.value].opciones
                    )
                }
            }
        } else {
            if (uiState.numPregunta < preguntasRelacion.size) {
                // Muestra la pregunta actual
                Text(
                    text = preguntasRelacion[uiState.numPregunta].enunciado,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.CenterHorizontally)
                )

                preguntasRelacion[uiState.numPregunta].opciones.forEachIndexed { index, opcion ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        RadioButton(
                            selected = index == uiState.respuestaSeleccionada,
                            onClick = { mvvm.seleccionarRespuesta(index) }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = opcion)
                    }
                }

                Button(
                    onClick = {
                        // Valida la respuesta seleccionada
                        if (mvvm.uiState.value.respuestaSeleccionada != -1) {
                            Toast.makeText(
                                context,
                                "Respuesta registrada",
                                Toast.LENGTH_SHORT
                            ).show()

                            //siguiente pregunta
                            statistics.value = generateStatistics(listOf(uiState.respuestaSeleccionada))
                            mvvm.registrarRespuesta(mvvm.uiState.value.respuestaSeleccionada)
                            mvvm.siguientePregunta()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Text(text = "Siguiente Pregunta")
                }

                Button(
                    onClick = {
                        mvvm.reiniciarEncuesta()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Text(text = "Reiniciar encuesta")
                }
            }
        }
    }
}

fun generateStatistics(selectedAnswers: List<Int>): List<Int> {
    val answerCounts = selectedAnswers.groupingBy { it }.eachCount()
    return List(5) { answerCounts.getOrDefault(it, 0) }
}

fun drawStatisticPentagon(
    drawScope: androidx.compose.ui.graphics.drawscope.DrawScope,
    center: Offset,
    radius: Float,
    statistics: List<Int>
) {
    val angleStep = 72f
    var startAngle = -90f

    val statisticPentagon = Path().apply {
        repeat(5) {
            val statValue = if (statistics[it] > 0) 1f else 0.5f
            val x =
                center.x + radius * statistics[it] / statistics.size * cos(Math.toRadians(startAngle.toDouble()).toFloat()) * statValue
            val y =
                center.y + radius * statistics[it] / statistics.size * sin(Math.toRadians(startAngle.toDouble()).toFloat()) * statValue
            if (startAngle == -90f) moveTo(x, y) else lineTo(x, y)
            startAngle += angleStep
        }
        close()
    }

    drawScope.drawPath(
        statisticPentagon,
        color = Color.Blue,
        style = androidx.compose.ui.graphics.drawscope.Fill
    )
}

private fun DrawScope.drawText(
    text: AnnotatedString,
    x: Float,
    y: Float,
    paint: androidx.compose.ui.graphics.Paint
) {
    drawContext.canvas.nativeCanvas.drawText(text.toString(), x, y, paint.asFrameworkPaint())
}

fun drawFullPentagon(
    drawScope: androidx.compose.ui.graphics.drawscope.DrawScope,
    center: Offset,
    radius: Float,
    answers: List<String>
) {
    val angleStep = 72f
    var startAngle = -90f

    val fullPentagon = Path().apply {
        repeat(5) {
            val x = center.x + radius * cos(Math.toRadians(startAngle.toDouble()).toFloat())
            val y = center.y + radius * sin(Math.toRadians(startAngle.toDouble()).toFloat())
            if (startAngle == -90f) moveTo(x, y) else lineTo(x, y)
            startAngle += angleStep
        }
        close()
    }

    drawScope.drawPath(
        fullPentagon,
        color = Color.Black,
        style = androidx.compose.ui.graphics.drawscope.Stroke(4.dp.value)
    )

    val paint = Paint().apply {
        color = Color.Black.toArgb()
        textSize = 24f
        typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
    }

    val textPaint = paint.asComposePaint()
    val textOffset = 20.dp
    val textRadius = radius + textOffset.value

    repeat(5) {
        val angle = Math.toRadians(-90 + it * 72.toDouble()).toFloat()
        val x = center.x + textRadius * cos(angle)
        val y = center.y + textRadius * sin(angle)
        drawScope.drawText(
            text = AnnotatedString(answers[it]),
            x = x,
            y = y,
            paint = textPaint
        )
    }
}
