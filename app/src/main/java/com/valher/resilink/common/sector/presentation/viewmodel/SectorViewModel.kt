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
    private val _sectores = MutableStateFlow<List<Sector>>(emptyList())
    open val sectores: StateFlow<List<Sector>> = _sectores
    private val _errorMessage = MutableStateFlow<String?>(null)
    open val errorMessage: StateFlow<String?> = _errorMessage
    private val _isLoading = MutableStateFlow(false)
    open val isLoading: StateFlow<Boolean> = _isLoading

    init {

        SharedDataCondominioSector.selectedCondominioId
            .onEach { condominioId ->
                condominioId?.let { loadSectores(it) }
            }
            .launchIn(viewModelScope)

    }
    private fun loadSectores(condominioId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                if(!condominioId.isEmpty()){
                    val response = getSectoresUseCase(condominioId)
                    if (response.estado) {
                        if(response.data.isNullOrEmpty()){
                            _errorMessage.value = "No hay sectores disponibles"
                        }else{
                            _sectores.value = response.data!!
                            _errorMessage.value = null
                        }
                    } else {
                        _errorMessage.value = response.mensaje
                    }
                }else{
                    _errorMessage.value = "No hay condominio seleccionado"
                }
            }catch (e: Exception){
                _errorMessage.value = "Error de red: ${e.message}"
            }finally {
                _isLoading.value = false
            }
        }
    }
}