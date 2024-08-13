package com.valher.resilink.feature.registro.presentation.ui.forms

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.valher.resilink.common.utils.ui.Subtitulo

@Composable
fun DatosAutenticacion(
    correo: String,
    onCorreoChange: (String) -> Unit,
    confirmCorreo: String,
    onConfirmCorreoChange: (String) -> Unit,
    telefono: String,
    onTelefonoChange: (String) -> Unit,
    contrasena: String,
    onContrasenaChange: (String) -> Unit,
    confirmContrasena: String,
    onConfirmContrasenaChange: (String) -> Unit,
    onButtonClicked: (Boolean) -> Unit
) {
    val isButtonClicked = remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Subtitulo(
            texto="Datos de Autenticación"
        )
        OutlinedTextField(
            value = correo,
            onValueChange = { onCorreoChange(it) },
            label = { Text("Correo electrónico") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = confirmCorreo,
            onValueChange = { onConfirmCorreoChange(it) },
            label = { Text("Confirme correo electrónico") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = telefono,
            onValueChange = { onTelefonoChange(it) },
            label = { Text("Número de teléfono") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = contrasena,
            onValueChange = { onContrasenaChange(it) },
            label = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = confirmContrasena,
            onValueChange = { onConfirmContrasenaChange(it) },
            label = { Text("Confirme contraseña") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            modifier = Modifier.fillMaxWidth().padding(top=8.dp),
            onClick = {
                isButtonClicked.value = true
                onButtonClicked(true)
            }) {
            Text(text = "Siguiente")
        }
    }
}