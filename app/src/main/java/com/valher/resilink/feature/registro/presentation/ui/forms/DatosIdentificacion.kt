package com.valher.resilink.feature.registro.presentation.ui.forms

import android.app.DatePickerDialog
import android.widget.DatePicker
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
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
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
    val context = LocalContext.current
    val isButtonClicked = remember { mutableStateOf(false) }

    // Dropdown states
    val expandedPhoto = remember { mutableStateOf(false) }
    val expandedDoc = remember { mutableStateOf(false) }

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

    val imageUri by cameraGalleryViewModel.imageUri.collectAsState()

    LaunchedEffect(imageUri) {
        imageUri?.let {
            onFotoUriChange(it.toString())
        }
    }

    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDay: Int ->
            val newDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
            selectedDate.value = newDate
            onFechaNacimientoChange(newDate)
        },
        year,
        month,
        day
    )

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Subtitulo(texto = "Datos de Identificación")

        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(selected = tipoDocumento == "DPI", onClick = { onTipoDocumentoChange("DPI") })
            Text(text = "DPI")
            Spacer(modifier = Modifier.width(16.dp))
            RadioButton(selected = tipoDocumento == "Pasaporte", onClick = { onTipoDocumentoChange("Pasaporte") })
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
        Box {
            TextButton(onClick = { expandedPhoto.value = true }) {
                Text("Agregue su fotografía")
            }

            DropdownMenu(
                expanded = expandedPhoto.value,
                onDismissRequest = { expandedPhoto.value = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Cámara") },
                    onClick = {
                        expandedPhoto.value = false
                        onTakePhoto()  // Lanza la cámara
                    }
                )
                DropdownMenuItem(
                    text = { Text("Galería") },
                    onClick = {
                        expandedPhoto.value = false
                        onPickImage()  // Lanza la galería
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Foto del documento
        Box {
            TextButton(onClick = { expandedDoc.value = true }) {
                Text("Agregue fotografía del documento de Identificación")
            }

            DropdownMenu(
                expanded = expandedDoc.value,
                onDismissRequest = { expandedDoc.value = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Cámara") },
                    onClick = {
                        expandedDoc.value = false
                        onTakePhoto()  // Lanza la cámara
                    }
                )
                DropdownMenuItem(
                    text = { Text("Galería") },
                    onClick = {
                        expandedDoc.value = false
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
    }
}
