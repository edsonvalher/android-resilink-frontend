package com.valher.resilink.feature.registro.presentation.ui.forms

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.onInterceptKeyBeforeSoftKeyboard
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.valher.resilink.common.condominio.presentation.ui.CondominioDropDown
import com.valher.resilink.common.sector.presentation.ui.SectorDropDown
import com.valher.resilink.common.utils.ui.Subtitulo

@Composable
fun DatosResidente(
    nombre: String,
    nombreError: String?,
    onNombreChange: (String) -> Unit,
    apellido: String,
    apellidoError: String?,
    onApellidoChange: (String) -> Unit,
    numerocasa: String,
    numerocasaError: String?,
    onNumerocasaChange: (String) -> Unit,
    codigoAcceso: String,
    codigoAccesoError : String?,
    onCodigoAccesoChange: (String) -> Unit,
    onButtonClicked: (Boolean) -> Unit
){

    val isButtonClicked = remember { mutableStateOf(false) }

    Column (
        modifier = Modifier
            .fillMaxHeight()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Subtitulo(
            texto="Datos de Residente"
        )
        CondominioDropDown()
        SectorDropDown()
        OutlinedTextField(
            value = numerocasa,
            onValueChange = { onNumerocasaChange(it)  },
            label = { Text("Número de casa") },
            isError = numerocasaError != null,
            modifier = Modifier.fillMaxWidth(),
            maxLines = 1,
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        numerocasaError?.let { Text(it, color = androidx.compose.ui.graphics.Color.Red) }

        OutlinedTextField(
            value = codigoAcceso,
            onValueChange = { onCodigoAccesoChange(it) },
            label = { Text("Código de acceso") },
            isError = codigoAccesoError != null,
            modifier = Modifier.fillMaxWidth(),
            maxLines = 1,
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        codigoAccesoError?.let { Text(it, color = androidx.compose.ui.graphics.Color.Red) }

        OutlinedTextField(
            value = nombre,
            onValueChange = { onNombreChange(it) },
            label = { Text("Nombres del residente") },
            isError = nombreError != null,
            modifier = Modifier.fillMaxWidth(),
            maxLines = 1,
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text, // Teclado de texto
                capitalization = KeyboardCapitalization.Sentences // Primera letra mayúscula
            )
        )
        nombreError?.let { Text(it, color = androidx.compose.ui.graphics.Color.Red) }
        OutlinedTextField(
            value = apellido,
            onValueChange = { onApellidoChange(it) },
            label = { Text("Apellidos del residente") },
            isError = apellidoError != null,
            modifier = Modifier.fillMaxWidth(),
            maxLines = 1,
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text, // Teclado de texto
                capitalization = KeyboardCapitalization.Sentences // Primera letra mayúscula
            )
        )
        apellidoError?.let { Text(it, color = androidx.compose.ui.graphics.Color.Red) }

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