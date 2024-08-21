package com.valher.resilink.feature.registro.presentation.ui.forms

import VisualizarImagenDialog
import android.app.Activity
import android.app.DatePickerDialog
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import android.widget.DatePicker
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.valher.resilink.R
import com.valher.resilink.common.features.natives.cameragallery.presentation.viewmodel.CameraGalleryViewModel
import com.valher.resilink.common.utils.ui.Subtitulo

import java.util.Calendar

@Composable
fun DatosIdentificacion(
    tipoDocumento: String,
    onTipoDocumentoChange: (String) -> Unit,
    fechaNacimiento: String,
    onFechaNacimientoChange: (String) -> Unit,
    dpi: String,
    onDpiChange: (String) -> Unit,
    fotoUri: String,
    onFotoUriChange: (String) -> Unit,
    documentoUri: String,
    onDocumentoUriChange: (String) -> Unit,
    onButtonClicked: (Boolean) -> Unit,
    onPickImage: () -> Unit,
    onTakePhoto: () -> Unit,
    cameraGalleryViewModel: CameraGalleryViewModel = hiltViewModel()
) {
    val activity = LocalContext.current as Activity
    val isButtonClicked = remember { mutableStateOf(false) }

    // Dropdown states
    val expandedPhoto = remember { mutableStateOf(false) }
    val expandedDoc = remember { mutableStateOf(false) }

    // Estados para mostrar el diálogo de imagen ampliada
    val isUserPhotoPreviewVisible = remember { mutableStateOf(false) }
    val isDocumentPhotoPreviewVisible = remember { mutableStateOf(false) }

    // Estado de permisos
    val permissionsGranted by cameraGalleryViewModel.permissionsGranted.collectAsState()

    // URI de la imagen
    val imageCameraUri by cameraGalleryViewModel.imageUriCamera.collectAsState()
    val imageGalleryUri by cameraGalleryViewModel.imageUriGallery.collectAsState()
    // URI separadas para foto de usuario y foto de documento
    val userPhotoUri = remember { mutableStateOf<Uri?>(null) }
    val documentPhotoUri = remember { mutableStateOf<Uri?>(null) }


    val calendar = Calendar.getInstance().apply {
        add(Calendar.YEAR, -18)
    }
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val selectedDate = remember { mutableStateOf("$day/${month + 1}/$year") }

    if (fechaNacimiento.isNotEmpty()) {
        selectedDate.value = fechaNacimiento
    } else {
        onFechaNacimientoChange(selectedDate.value)
    }

    val datePickerDialog = DatePickerDialog(
        activity,
        { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDay: Int ->
            val newDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
            selectedDate.value = newDate
            onFechaNacimientoChange(newDate)
        },
        year,
        month,
        day
    )

    // Solicitar permisos si no están concedidos
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        cameraGalleryViewModel.onPermissionsResult(isGranted)
    }
    LaunchedEffect(Unit) {
        if (!cameraGalleryViewModel.permissionsGranted.value) {
            launcher.launch(android.Manifest.permission.CAMERA)
        }else{
            cameraGalleryViewModel.onPermissionsResult(true)
        }
    }
    LaunchedEffect(imageCameraUri) {
        Log.d("CameraGalleryViewModel", "camera updated: $imageCameraUri")
        if (expandedPhoto.value) {
            userPhotoUri.value = cameraGalleryViewModel.imageUriCamera.value
            onFotoUriChange(cameraGalleryViewModel.imageUriCamera.value.toString())
            expandedPhoto.value = false
        } else if (expandedDoc.value) {
            documentPhotoUri.value = cameraGalleryViewModel.imageUriCamera.value
            onDocumentoUriChange(cameraGalleryViewModel.imageUriCamera.value.toString())
            expandedDoc.value = false
        }
    }
    LaunchedEffect(imageGalleryUri) {
        Log.d("CameraGalleryViewModel", "gallery updated: $imageGalleryUri")
        if (expandedPhoto.value) {
            userPhotoUri.value = cameraGalleryViewModel.imageUriGallery.value
            onFotoUriChange(cameraGalleryViewModel.imageUriGallery.value.toString())
            expandedPhoto.value = false
        } else if (expandedDoc.value) {
            documentPhotoUri.value = cameraGalleryViewModel.imageUriGallery.value
            onDocumentoUriChange(cameraGalleryViewModel.imageUriGallery.value.toString())
            expandedDoc.value = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Subtitulo(texto = "Datos de Identificación")

        if (permissionsGranted) {
            // Contenido principal cuando los permisos están concedidos
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = tipoDocumento == "DPI",
                    onClick = { onTipoDocumentoChange("DPI") })
                Text(text = "DPI")
                Spacer(modifier = Modifier.width(16.dp))
                RadioButton(
                    selected = tipoDocumento == "Pasaporte",
                    onClick = { onTipoDocumentoChange("Pasaporte") })
                Text(text = "Pasaporte")
            }

            OutlinedTextField(
                value = dpi,
                onValueChange = { onDpiChange(it) },
                label = { Text("Número de Documento") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = selectedDate.value,
                onValueChange = { },
                label = { Text("Fecha de Nacimiento") },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { datePickerDialog.show() },
                enabled = false,
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Foto personal
            Box (
                modifier = Modifier.fillMaxWidth()
            ){
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (userPhotoUri.value != null) {
                        val bitmapFotoUsuario = remember(userPhotoUri.value) { getBitmapFromUri(userPhotoUri.value!!, activity) }
                        bitmapFotoUsuario?.let {
                            Image(
                                bitmap = it.asImageBitmap(),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(50.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .border(
                                        BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                                        shape = RoundedCornerShape(8.dp))
                                    .clickable { isUserPhotoPreviewVisible.value = true }
                            )
                        }
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.sinimagen),
                            contentScale = ContentScale.Crop,
                            contentDescription = "Imagen por defecto",
                            modifier = Modifier
                                .size(50.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .border(
                                    BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                                    shape = RoundedCornerShape(8.dp))
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    TextButton(onClick = { expandedPhoto.value = true }) {
                        Text("Agregue su fotografía")
                    }
                }
                DropdownMenu(
                    expanded = expandedPhoto.value,
                    onDismissRequest = { expandedPhoto.value = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Cámara") },
                        onClick = {
                            onTakePhoto()  // Lanza la cámara
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Galería") },
                        onClick = {
                            onPickImage()  // Lanza la galería
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Foto del documento
            Box (
                modifier = Modifier.fillMaxWidth()
            ){
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier.fillMaxWidth()
                ){
                    if (documentPhotoUri.value != null) {
                        val bitmapFotoDocumento = remember(documentPhotoUri.value) { getBitmapFromUri(documentPhotoUri.value!!, activity) }
                        bitmapFotoDocumento?.let {
                            Image(
                                bitmap = it.asImageBitmap(),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(50.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .border(
                                        BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                                        shape = RoundedCornerShape(8.dp))
                                    .clickable { isDocumentPhotoPreviewVisible.value = true }
                            )
                        }
                    }
                    else {
                        Image(
                            painter = painterResource(id = R.drawable.sinimagen),
                            contentDescription = "Imagen por defecto",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(50.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .border(
                                    BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                                    shape = RoundedCornerShape(8.dp)
                                )
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    TextButton(onClick = { expandedDoc.value = true }) {
                        Text("Agregue fotografía del documento de Identificación")
                    }
                }
                DropdownMenu(
                    expanded = expandedDoc.value,
                    onDismissRequest = { expandedDoc.value = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Cámara") },
                        onClick = {
                            onTakePhoto()  // Lanza la cámara
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Galería") },
                        onClick = {
                            onPickImage()  // Lanza la galería
                        }
                    )
                }
            }

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                onClick = {
                    isButtonClicked.value = true
                    onButtonClicked(true)
                }
            ) {
                Text(text = "Finalizar")
            }
        } else {
            // Mostrar mensaje de error si no se han concedido los permisos
            Text(
                text = "Permisos de cámara y almacenamiento no concedidos. " +
                        "Por favor, conceda los permisos para continuar.",
                color = MaterialTheme.colorScheme.error
            )
        }
    }
    if (isUserPhotoPreviewVisible.value) {
        VisualizarImagenDialog(
            imagen = getBitmapFromUri(userPhotoUri.value!!, activity),
            onClose = { isUserPhotoPreviewVisible.value = false }
        )
    }
    if (isDocumentPhotoPreviewVisible.value) {
        VisualizarImagenDialog(
            imagen = getBitmapFromUri(documentPhotoUri.value!!, activity),
            onClose = { isDocumentPhotoPreviewVisible.value = false }
        )
    }
}


fun getBitmapFromUri(uri: Uri, activity: Activity): Bitmap? {
    return activity.contentResolver.openInputStream(uri)?.use { inputStream ->
        BitmapFactory.decodeStream(inputStream)
    }
}

