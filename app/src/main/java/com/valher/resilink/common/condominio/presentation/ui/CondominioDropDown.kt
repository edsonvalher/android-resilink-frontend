package com.valher.resilink.common.condominio.presentation.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Alignment
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.valher.resilink.common.condominio.presentation.viewmodel.CondominioViewModel
import com.valher.resilink.shared.SharedDataCondominioSector

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CondominioDropDown(viewModel: CondominioViewModel = hiltViewModel()) {
    var abrir by remember { mutableStateOf(false) }
    var selected by remember { mutableStateOf("") }
    var selectedId by remember { mutableStateOf<String?>(null) }

    val condominios by viewModel.condominios.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        when {
            isLoading -> {
                Box(modifier = Modifier.fillMaxWidth().height(50.dp), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp))
                }
            }
            errorMessage != null -> {
                Box(modifier = Modifier.fillMaxWidth().height(50.dp), contentAlignment = Alignment.Center) {
                    Text(
                        text = "No fue posible cargar el control",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.testTag("ErrorMessage")
                    )
                }
            }
            condominios.isNotEmpty() -> {
                ExposedDropdownMenuBox(
                    expanded = abrir,
                    onExpandedChange = { abrir = !abrir },
                    modifier = Modifier.testTag("DropdownMenu")
                ) {
                    OutlinedTextField(
                        value = selected,
                        onValueChange = { selected = it },
                        readOnly = true,
                        modifier = Modifier
                            .fillMaxWidth() // Se asegura de ocupar todo el ancho disponible
                            .menuAnchor()
                            .background(MaterialTheme.colorScheme.background),
                        singleLine = true,
                        label = { Text("Seleccione un condominio") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = abrir)
                        },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                        )
                    )
                    ExposedDropdownMenu(
                        expanded = abrir,
                        onDismissRequest = { abrir = false }
                    ) {
                        condominios.forEach { condominio ->
                            DropdownMenuItem(
                                text = { Text(text = condominio.nombre) },
                                onClick = {
                                    selected = condominio.nombre
                                    selectedId = condominio.id
                                    condominio.id?.let {
                                        SharedDataCondominioSector.selectCondominio(it)
                                    }
                                    abrir = false
                                }
                            )
                        }
                    }
                }
            }
            else -> {
                Box(modifier = Modifier.fillMaxWidth().height(50.dp), contentAlignment = Alignment.Center) {
                    Text(
                        text = "No hay condominios disponibles",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}
