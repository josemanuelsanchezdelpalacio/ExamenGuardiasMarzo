package com.dam2jms.apppuntosvioletas.screens

import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.CacheDrawScope
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.dam2jms.apppuntosvioletas.data.PreguntasEncuesta
import com.dam2jms.apppuntosvioletas.models.ViewModelEncuestaScreen
import com.dam2jms.apppuntosvioletas.navigation.AppScreens
import com.dam2jms.apppuntosvioletas.states.UiState
import kotlinx.coroutines.launch
import kotlin.math.PI
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
fun EncuestaScreenBodyContent(
    modifier: Modifier,
    mvvm: ViewModelEncuestaScreen,
    navController: NavHostController,
    uiState: UiState
) {
    val preguntasEncuesta = PreguntasEncuesta()
    val preguntasRelacion = preguntasEncuesta.preguntas

    val context = LocalContext.current

    val respuestasPentagonales = mutableListOf<Int>()

    // Inicializa la lista de respuestas pentagonales con valores de 0
    respuestasPentagonales.addAll(List(preguntasRelacion.size) { 0 })
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(50.dp))
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
        } else {
            PentagonChart(respuestasPentagonales)
        }
    }
}

@Composable
fun PentagonChart(respuestas: List<Int>) {

    val textMeasurer = rememberTextMeasurer()

    Canvas(modifier = Modifier.fillMaxSize()) {
        // Se calcula el punto central y el tamaño
        val X = size.width / 2
        val Y = size.height / 2
        val radioExterno = 300f
        val radioInterno = 300f

        val respuestasEsquinas =
            listOf("V.Psicologica", "V.Sexual", "R.Sana", "R.Constructiva", "X", "V.Sexual")

        val hexagono = Path().apply {
            moveTo(X + radioExterno * cos(0f), Y + radioExterno * sin(0f))
            for (i in 1..5) {
                val angle = i * (2 * PI / 6)
                lineTo(
                    (X + radioExterno * cos(angle)).toFloat(),
                    (Y + radioExterno * sin(angle)).toFloat()
                )
            }
            close()
        }
        drawPath(path = hexagono, color = Color.Magenta, style = Stroke(width = 10f))

        estadisticPentagonChart(
            drawScope = this,
            center = Offset(size.width / 2, size.height / 2),
            radius = 150f,
            respuestas = listOf(3, 4, 2, 5, 1) // Replace with your actual data
        )

        for (i in respuestasEsquinas.indices) {
            val angulo = i * (2 * PI / 6)
            val textoX = (X + (radioExterno + 20) * cos(angulo)).toFloat()
            val textoY = (Y + (radioExterno + 20) * sin(angulo)).toFloat()
            drawText(
                textMeasurer = textMeasurer,
                text = respuestasEsquinas[i],
                style = TextStyle(fontSize = 16.sp, color = Color.Black),
                offset = Offset(x = textoX, y = textoY)
            )
        }
    }
}

@Composable
fun estadisticPentagonChart(
    drawScope: DrawScope,
    center: Offset,
    radius: Float,
    respuestas: List<Int>
) {
    var startAngle = -90f
    val angleStep = 72f

    val hexagono = Path().apply {
        repeat(5) {
            val statValue = if (respuestas[it] > 0) 1f + ((respuestas[it] / 2f) / 5f)
            else 0.5f
            val x = center.x + radius * cos(Math.toRadians(startAngle.toDouble())).toFloat() * statValue
            val y = center.y + radius * sin(Math.toRadians(startAngle.toDouble())).toFloat() * statValue
            if(startAngle == -90f) moveTo(x, y) else lineTo(x, y)
            startAngle += angleStep
        }
        close()
    }
    drawScope.drawPath(
        path = hexagono,
        color = Color.Magenta,
        style = Stroke(width = 10f)
    )
}