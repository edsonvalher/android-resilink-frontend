package com.valher.resilink.common.condominio.presentation.viewmodel

import com.valher.resilink.common.condominio.data.model.Condominio

sealed interface CondominioUiState {
    object Loading : CondominioUiState
    data class Success(val condominios: List<Condominio>) : CondominioUiState
    data class Error(val errorMessage: String) : CondominioUiState
    object Empty : CondominioUiState
}