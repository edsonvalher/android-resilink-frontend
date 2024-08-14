package com.valher.resilink.common.sector.presentation.ui


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
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.valher.resilink.common.sector.data.model.Sector
import com.valher.resilink.common.sector.presentation.viewmodel.SectorUiState
import com.valher.resilink.common.sector.presentation.viewmodel.SectorViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SectorDropDown(viewModel: SectorViewModel = hiltViewModel()) {
    var abrir by remember { mutableStateOf(false) }
    var selected by remember { mutableStateOf("") }
    var selectedId by remember { mutableStateOf<String?>(null) }

    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        when(uiState) {
            is SectorUiState.Loading -> {
                Box(modifier = Modifier.fillMaxWidth().height(50.dp), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp))
                }
            }
            is SectorUiState.Error -> {
                Box(modifier = Modifier.fillMaxWidth().height(50.dp), contentAlignment = Alignment.Center) {
                    Text(
                        text = "No fue posible cargar el control",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.testTag("ErrorMessage")
                    )
                }
            }
            is SectorUiState.Success -> {
                val sectores = (uiState as SectorUiState.Success).sectores
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
                            .fillMaxWidth()
                            .menuAnchor()
                            .background(MaterialTheme.colorScheme.background),
                        singleLine = true,
                        label = { Text("Seleccione un sector") },
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
                        sectores.forEach { sector ->
                            DropdownMenuItem(
                                text = { Text(text = sector.nombre) },
                                onClick = {
                                    selected = sector.nombre
                                    selectedId = sector.id
                                    abrir = false
                                }
                            )
                        }
                    }
                }
            }
            is SectorUiState.Empty -> {
                Box(modifier = Modifier.fillMaxWidth().height(50.dp), contentAlignment = Alignment.Center) {
                    Text(
                        text = "No hay sectores disponibles",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}