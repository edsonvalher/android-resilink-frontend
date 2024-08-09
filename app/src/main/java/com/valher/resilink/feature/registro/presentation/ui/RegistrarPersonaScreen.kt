package com.valher.resilink.feature.registro.presentation.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.valher.resilink.feature.registro.presentation.ui.forms.DatosIdentificacion
import com.valher.resilink.feature.registro.presentation.ui.forms.DatosResidente
import com.valher.resilink.feature.registro.presentation.viewmodel.PersonaViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun RegistrarPersonaScreen(
    navigationController: NavHostController,
    viewModel: PersonaViewModel = hiltViewModel()
) {
    val persona by viewModel.persona.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    val paginas = listOf("Datos Personales", "Datos de Identificación")
    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var numeroDocumento by remember { mutableStateOf("") }
    var tipoDocumento by remember { mutableStateOf("") }
    var codigoAcceso by remember { mutableStateOf("") }
    var fechaNacimiento by remember { mutableStateOf("") }
    var numerocasa by remember { mutableStateOf("") }
    var activo by remember { mutableStateOf(true) }
    val pagerState = rememberPagerState(pageCount = { 3 })
    var isDatosResidenteSuccess by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("Registrarse")
                }
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                when {
                    isLoading -> {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .testTag("ProgressIndicator")
                        )
                    }
                    errorMessage != null -> {
                        Text(
                            text = "No fue posible cargar el control",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.testTag("ErrorMessage")
                        )
                    }
                    else -> {
                        HorizontalPager(
                            state = pagerState,
                            modifier = Modifier.weight(1f),
                            //userScrollEnabled = false,
                        ) { page ->
                            when (page) {
                                0 -> {
                                    DatosResidente(
                                        nombre = nombre,
                                        onNombreChange = { nombre = it },
                                        apellido = apellido,
                                        onApellidoChange = { apellido = it },
                                        numerocasa = numerocasa,
                                        onNumerocasaChange = { numerocasa = it },
                                        codigoAcceso = codigoAcceso,
                                        onCodigoAccesoChange = { codigoAcceso = it },
                                        onButtonClicked = { clicked ->
                                            if(clicked){
                                                coroutineScope.launch {
                                                    pagerState.animateScrollToPage(page = 1)
                                                }
                                            }
                                        }
                                    )
                                }
                                1 -> {

                                    DatosIdentificacion(
                                        correo = correo,
                                        onCorreoChange = { correo = it },
                                        confirmCorreo = correo,
                                        onConfirmCorreoChange = { correo = it },
                                        telefono = telefono,
                                        onTelefonoChange = { telefono = it },
                                        contrasena = contrasena,
                                        onContrasenaChange = { contrasena = it },
                                        confirmContrasena = contrasena,
                                        onConfirmContrasenaChange = { contrasena = it },
                                        onButtonClicked = { clicked ->
                                            if(clicked){
                                                coroutineScope.launch {
                                                    pagerState.animateScrollToPage(page = 2)
                                                }
                                            }
                                        }
                                    )
                                }
                                2 -> {
                                    Text(
                                        text = "Page: 3",
                                    )
                                }
                            }
                        }
                        Row(
                            Modifier
                                .wrapContentHeight()
                                .fillMaxWidth()
                                .align(Alignment.CenterHorizontally)
                                .padding(top = 8.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            repeat(pagerState.pageCount) { iteration ->
                                val color =
                                    if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
                                Box(
                                    modifier = Modifier
                                        .padding(2.dp)
                                        .clip(CircleShape)
                                        .background(color)
                                        .size(16.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    )
}
