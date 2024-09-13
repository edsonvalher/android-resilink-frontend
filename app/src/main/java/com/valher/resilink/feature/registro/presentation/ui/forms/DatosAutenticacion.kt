package com.valher.resilink.feature.registro.presentation.ui.forms

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.valher.resilink.common.utils.ui.Subtitulo

@Composable
fun DatosAutenticacion(
    correo: String,
    correoError: String?,
    onCorreoChange: (String) -> Unit,
    confirmCorreo: String,
    confirmCorreoError: String?,
    onConfirmCorreoChange: (String) -> Unit,
    telefono: String,
    telefonoError: String?,
    onTelefonoChange: (String) -> Unit,
    contrasena: String,
    contrasenaError: String?,
    onContrasenaChange: (String) -> Unit,
    confirmContrasena: String,
    confirmContrasenaError: String?,
    onConfirmContrasenaChange: (String) -> Unit,
    onButtonClicked: (Boolean) -> Unit
) {
    val isButtonClicked = remember { mutableStateOf(false) }
    var passwordVisible by remember { mutableStateOf(false) }
    var repasswordVisible by remember { mutableStateOf(false) }
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
            isError = correoError != null,
            modifier = Modifier.fillMaxWidth(),
            maxLines = 1,
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )
        correoError?.let { Text(it, color = androidx.compose.ui.graphics.Color.Red) }
        OutlinedTextField(
            value = confirmCorreo,
            onValueChange = { onConfirmCorreoChange(it) },
            label = { Text("Confirme correo electrónico") },
            isError = confirmCorreoError != null,
            modifier = Modifier.fillMaxWidth(),
            maxLines = 1,
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )
        confirmCorreoError?.let { Text(it, color = androidx.compose.ui.graphics.Color.Red) }
        OutlinedTextField(
            value = telefono,
            onValueChange = { onTelefonoChange(it) },
            label = { Text("Número de teléfono") },
            isError = telefonoError != null,
            modifier = Modifier.fillMaxWidth(),
            maxLines = 1,
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
        )
        telefonoError?.let { Text(it, color = androidx.compose.ui.graphics.Color.Red) }
        OutlinedTextField(
            value = contrasena,
            onValueChange = { onContrasenaChange(it) },
            label = { Text("Contraseña") },
            isError = contrasenaError != null,
            modifier = Modifier.fillMaxWidth(),
            maxLines = 1,
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image = if (passwordVisible) {
                    Icons.Filled.VisibilityOff
                } else {
                    Icons.Filled.Visibility
                }
                IconButton(onClick = {
                    passwordVisible = !passwordVisible
                }) {
                    Icon(imageVector = image, contentDescription = if (passwordVisible) "Ocultar contraseña" else "Mostrar contraseña")
                }
            }

        )
        contrasenaError?.let { Text(it, color = androidx.compose.ui.graphics.Color.Red) }
        OutlinedTextField(
            value = confirmContrasena,
            onValueChange = { onConfirmContrasenaChange(it) },
            label = { Text("Confirme contraseña") },
            isError = confirmContrasenaError != null,
            modifier = Modifier.fillMaxWidth(),
            maxLines = 1,
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = if (repasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image = if (repasswordVisible) {
                    Icons.Filled.VisibilityOff
                } else {
                    Icons.Filled.Visibility
                }
                IconButton(onClick = {
                    repasswordVisible = !repasswordVisible
                }) {
                    Icon(imageVector = image, contentDescription = if (repasswordVisible) "Ocultar contraseña" else "Mostrar contraseña")
                }
            }

        )
        confirmContrasenaError?.let { Text(it, color = androidx.compose.ui.graphics.Color.Red) }
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