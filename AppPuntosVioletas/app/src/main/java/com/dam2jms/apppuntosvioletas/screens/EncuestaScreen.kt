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
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
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
            EncuestaScreenBodyContent(modifier = Modifier.padding(paddingValues), mvvm, navController, uiState)
        }
    }
}

@Composable
fun EncuestaScreenBodyContent(modifier: Modifier, mvvm: ViewModelEncuestaScreen, navController: NavHostController, uiState: UiState) {
    val preguntasEncuesta = PreguntasEncuesta()
    val preguntasRelacion = preguntasEncuesta.preguntas

    val context = LocalContext.current

    val respuestasPentagonales = mutableListOf<Int>()

    // Inicializa la lista de respuestas pentagonales con valores de 0
    respuestasPentagonales.addAll(List(preguntasRelacion.size) { 0 })


    fun reiniciarEncuesta() {
        mvvm.reiniciarEncuesta()
    }

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
        } else {
            // Muestra el esquema pentagonal
            PentagonChart(respuestasPentagonales)
        }
    }
}

@Composable
fun PentagonChart(respuestas: List<Int>) {
    Spacer(
        modifier = Modifier.drawWithCache {
            val path = Path()
            val angleIncrement = 2.0 * PI / 5.0
            val radius = min(size.width, size.height) / 4.0f
            for (i in 0 until 5) {
                val angle = i * angleIncrement - PI / 2.0
                val x = (radius * cos(angle)).toFloat() + size.width / 2.0f
                val y = (radius * sin(angle)).toFloat() + size.height / 2.0f
                if (i == 0) {
                    path.moveTo(x, y)
                } else {
                    path.lineTo(x, y)
                }
                // Dibuja el texto de la respuesta en cada esquina
                val textX = ((radius + 30f) * cos(angle)).toFloat() + size.width / 2.0f
                val textY = ((radius + 30f) * sin(angle)).toFloat() + size.height / 2.0f
                /*drawContext.canvas.nativeCanvas.drawText(
                    "Opción ${i + 1}",
                    textX,
                    textY,
                    android.graphics.Paint().apply {
                        color = android.graphics.Color.BLACK
                        textSize = 15f
                        textAlign = android.graphics.Paint.Align.CENTER
                    }
                )*/
            }
            path.close()
            onDrawBehind {
                drawPath(path, Color.Magenta, style = Stroke(width = 10f))
            }
        }
            .fillMaxSize()
    )
}
