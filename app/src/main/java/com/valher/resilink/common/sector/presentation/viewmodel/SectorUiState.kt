package com.valher.resilink.common.sector.presentation.viewmodel

import com.valher.resilink.common.sector.data.model.Sector

interface SectorUiState {
    object Loading : SectorUiState
    data class Success(val sectores: List<Sector>) : SectorUiState
    data class Error(val errorMessage: String) : SectorUiState
    object Empty : SectorUiState

}