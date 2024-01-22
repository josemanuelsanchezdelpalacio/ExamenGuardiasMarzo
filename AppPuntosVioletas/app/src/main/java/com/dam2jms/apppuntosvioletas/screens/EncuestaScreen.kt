package com.dam2jms.apppuntosvioletas.screens

import android.annotation.SuppressLint
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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.dam2jms.apppuntosvioletas.data.Pregunta
import com.dam2jms.apppuntosvioletas.data.PreguntasEncuesta
import com.dam2jms.apppuntosvioletas.models.ViewModelEncuestaScreen
import com.dam2jms.apppuntosvioletas.navigation.AppScreens
import com.dam2jms.apppuntosvioletas.states.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun encuestaScreen(navController: NavHostController, mvvm: ViewModelEncuestaScreen) {

    val uiState by mvvm.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFAC53F7),
                    titleContentColor = Color.White,
                ),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Atrás")
                    }
                },
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        IconButton(onClick = { navController.navigate(route = AppScreens.OpcionesScreen.route) }) {
                            Icon(imageVector = Icons.Default.Menu, contentDescription = "Menú")
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(route = AppScreens.SOS_Screen.route) },
                containerColor = Color(0xFFAC53F7),
                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
            ) {
                Icon(imageVector = Icons.Default.Warning, contentDescription = "SOS")
            }
        },
    ) { paddingValues ->
        encuestaScreenBodyContent(modifier = Modifier.padding(paddingValues), mvvm, navController, uiState)
    }
}

@Composable
fun encuestaScreenBodyContent(modifier: Modifier, mvvm: ViewModelEncuestaScreen, navController: NavHostController, uiState: UiState) {
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
    // Dibuja el esquema pentagonal según las respuestas obtenidas
    val pentagonSize = 200.dp
    val lineWidth = 2.dp
    val path = androidx.compose.ui.graphics.Path()

    for (i in 0 until respuestas.size) {
        val angle = Math.toRadians((i * (360.0 / respuestas.size)).toDouble())

        /*val x = (pentagonSize / 2 * angle)
        val y = (pentagonSize / 2 * angle)

        if (i == 0) {
            path.moveTo(x, y)
        } else {
            path.lineTo(x, y)
        }*/
    }

    path.close()

    Canvas(
        modifier = Modifier.size(pentagonSize),
        onDraw = {
            drawPath(
                path = path,
                color = Color(0xFF6200EA),
                style = Stroke(lineWidth.toPx())
            )
        }
    )
}


