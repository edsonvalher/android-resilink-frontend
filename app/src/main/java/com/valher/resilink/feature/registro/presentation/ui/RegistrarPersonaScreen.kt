package com.valher.resilink.feature.registro.presentation.ui

import android.content.Intent
import android.util.Log
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.valher.resilink.common.features.natives.cameragallery.presentation.viewmodel.CameraGalleryViewModel
import com.valher.resilink.feature.registro.presentation.ui.forms.DatosAutenticacion
import com.valher.resilink.feature.registro.presentation.ui.forms.DatosIdentificacion
import com.valher.resilink.feature.registro.presentation.ui.forms.DatosResidente
import com.valher.resilink.feature.registro.presentation.viewmodel.PersonaViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun RegistrarPersonaScreen(
    navigationController: NavHostController,
    onPickImage: (Intent) -> Unit,
    onTakePhoto: (Intent) -> Unit,
    viewmodel: CameraGalleryViewModel = hiltViewModel(),
    personaviewModel: PersonaViewModel = hiltViewModel()
) {
    val persona by personaviewModel.persona.collectAsState()
    val errorMessage by personaviewModel.errorMessage.collectAsState()
    val isLoading by personaviewModel.isLoading.collectAsState()

    val nombre by personaviewModel.nombre.collectAsState()
    val nombreError by personaviewModel.nombreError.collectAsState()
    val apellido by personaviewModel.apellido.collectAsState()
    val apellidoError by personaviewModel.apellidoError.collectAsState()
    val correo by personaviewModel.correo.collectAsState()
    val correoError by personaviewModel.correoError.collectAsState()
    val recorreo by personaviewModel.recorreo.collectAsState()
    val telefono by personaviewModel.telefono.collectAsState()
    val telefonoError by personaviewModel.telefonoError.collectAsState()
    val contrasena by personaviewModel.contrasena.collectAsState()
    val recontrasena by personaviewModel.recontrasena.collectAsState()
    val numeroDocumento by personaviewModel.numeroDocumento.collectAsState()
    val tipoDocumento by personaviewModel.tipoDocumento.collectAsState()
    val codigoAcceso by personaviewModel.codigoAcceso.collectAsState()
    val codigoAccesoError by personaviewModel.codigoAccesoError.collectAsState()
    val fechaNacimiento by personaviewModel.fechaNacimiento.collectAsState()
    val numerocasa by personaviewModel.numerocasa.collectAsState()
    val numerocasaError by personaviewModel.numerocasaError.collectAsState()
    val fotoUri by personaviewModel.fotoUri.collectAsState()
    val documentoUri by personaviewModel.documentoUri.collectAsState()

    val TAG = "RegistrarPersonaScreen"
    val pagerState = rememberPagerState(pageCount = { 3 })
    val coroutineScope = rememberCoroutineScope()
    val galleryIntent = Intent(Intent.ACTION_PICK).apply {
        type = "image/*"
    }

    val cameraIntent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)

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
                        ) { page ->
                            when (page) {
                                0 -> {
                                    DatosResidente(
                                        nombre = nombre,
                                        onNombreChange = { personaviewModel.onNombreChange(it) },
                                        nombreError = nombreError,
                                        apellido = apellido,
                                        apellidoError = apellidoError,
                                        onApellidoChange = { personaviewModel.onApellidoChange(it) },
                                        numerocasa = numerocasa,
                                        numerocasaError = numerocasaError,
                                        onNumerocasaChange = { personaviewModel.onNumerocasaChange(it) },
                                        codigoAcceso = codigoAcceso,
                                        codigoAccesoError = codigoAccesoError,
                                        onCodigoAccesoChange = { personaviewModel.onCodigoAccesoChange(it) },
                                        onButtonClicked = { clicked ->
                                            if (clicked) {
                                                coroutineScope.launch {
                                                    pagerState.animateScrollToPage(page = 1)
                                                }
                                            }
                                        }
                                    )
                                }
                                1 -> {
                                    DatosAutenticacion(
                                        correo = correo,
                                        onCorreoChange = { personaviewModel.onCorreoChange(it) },
                                        confirmCorreo = recorreo,
                                        onConfirmCorreoChange = { personaviewModel.onReCorreoChange(it) },
                                        telefono = telefono,
                                        onTelefonoChange = { personaviewModel.onTelefonoChange(it) },
                                        contrasena = contrasena,
                                        onContrasenaChange = { personaviewModel.onContrasenaChange(it) },
                                        confirmContrasena = recontrasena,
                                        onConfirmContrasenaChange = { personaviewModel.onReContrasenaChange(it) },
                                        onButtonClicked = { clicked ->
                                            if (clicked) {
                                                coroutineScope.launch {
                                                    pagerState.animateScrollToPage(page = 2)
                                                }
                                            }
                                        }
                                    )
                                }
                                2 -> {
                                    DatosIdentificacion(
                                        tipoDocumento = tipoDocumento,
                                        onTipoDocumentoChange = { personaviewModel.onTipoDocumentoChange(it) },
                                        fechaNacimiento = fechaNacimiento,
                                        onFechaNacimientoChange = { personaviewModel.onFechaNacimientoChange(it) },
                                        dpi = numeroDocumento,
                                        onDpiChange = { personaviewModel.onNumeroDocumentoChange(it) },
                                        fotoUri = fotoUri,
                                        onFotoUriChange = { personaviewModel.onFotoUriChange(it) },
                                        documentoUri = documentoUri,
                                        onDocumentoUriChange = { personaviewModel.onDocumentoUriChange(it) },
                                        onButtonClicked = { clicked ->
                                            if (clicked) {
                                                coroutineScope.launch {
                                                    personaviewModel.registrarPersona()
                                                    Log.d(TAG, "RegistrarPersonaScreen: $persona")
                                                }
                                            }
                                        },
                                        onPickImage = { onPickImage(galleryIntent) },
                                        onTakePhoto = { onTakePhoto(cameraIntent) },
                                        viewmodel
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