package com.valher.resilink.shared

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object SharedDataCondominioSector {
    // Estado para el condominio seleccionado
    private val _selectedCondominioId = MutableStateFlow<String?>(null)
    val selectedCondominioId: StateFlow<String?> get() = _selectedCondominioId

    fun selectCondominio(condominioId: String) {
        _selectedCondominioId.value = condominioId
    }
}