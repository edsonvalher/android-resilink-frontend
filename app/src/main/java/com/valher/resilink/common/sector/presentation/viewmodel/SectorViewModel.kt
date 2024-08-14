package com.valher.resilink.common.sector.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valher.resilink.common.sector.data.model.Sector
import com.valher.resilink.common.sector.domain.usecase.GetSectoresUseCase
import com.valher.resilink.shared.SharedDataCondominioSector
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class SectorViewModel @Inject constructor(
    private val getSectoresUseCase: GetSectoresUseCase
): ViewModel(){
    private val _uiState = MutableStateFlow<SectorUiState>(SectorUiState.Empty)
    val uiState: StateFlow<SectorUiState> = _uiState

    init {

        SharedDataCondominioSector.selectedCondominioId
            .onEach { condominioId ->
                condominioId?.let { loadSectores(it) }
            }
            .launchIn(viewModelScope)

    }
    private fun loadSectores(condominioId: String) {
        viewModelScope.launch {
            _uiState.value = SectorUiState.Loading
            try {
                if(!condominioId.isEmpty()){
                    val response = getSectoresUseCase(condominioId)
                    if (response.estado) {
                        if(response.data.isNullOrEmpty()){
                            _uiState.value = SectorUiState.Empty
                        }else{
                            _uiState.value = SectorUiState.Success(response.data!!)
                        }
                    } else {
                        _uiState.value = SectorUiState.Error(response.mensaje!!)
                    }
                }else{
                    _uiState.value = SectorUiState.Error("No hay condominio seleccionado")
                }
            }catch (e: Exception){
                _uiState.value = SectorUiState.Error("Error de red: ${e.message}")
            }
        }
    }
}