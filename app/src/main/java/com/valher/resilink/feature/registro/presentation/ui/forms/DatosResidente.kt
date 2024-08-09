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
import com.valher.resilink.common.condominio.presentation.ui.CondominioDropDown
import com.valher.resilink.common.sector.presentation.ui.SectorDropDown
import com.valher.resilink.common.utils.ui.Subtitulo

@Composable
fun DatosResidente(
    nombre: String,
    onNombreChange: (String) -> Unit,
    apellido: String,
    onApellidoChange: (String) -> Unit,
    numerocasa: String,
    onNumerocasaChange: (String) -> Unit,
    codigoAcceso: String,
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
            onValueChange = { onNumerocasaChange  },
            label = { Text("Número de casa") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = codigoAcceso,
            onValueChange = { onCodigoAccesoChange },
            label = { Text("Código de acceso") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = nombre,
            onValueChange = { onNombreChange },
            label = { Text("Nombres del residente") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = apellido,
            onValueChange = { onApellidoChange },
            label = { Text("Apellidos del residente") },
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