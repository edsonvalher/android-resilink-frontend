package com.valher.resilink.common.condominio.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valher.core.model.NetworkResponse
import com.valher.resilink.common.condominio.data.model.Condominio
import com.valher.resilink.common.condominio.domain.usecase.GetCondominiosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class CondominioViewModel  @Inject constructor(
    private val getCondominiosUseCase: GetCondominiosUseCase
): ViewModel()
{

    private val _uiState = MutableStateFlow<CondominioUiState>(CondominioUiState.Loading)
    val uiState: StateFlow<CondominioUiState> = _uiState

    init {
        loadCondominios()
    }

    private fun loadCondominios() {
        viewModelScope.launch {
            _uiState.value = CondominioUiState.Loading
            try {
                val response = getCondominiosUseCase()
                if (response.estado) {
                    if (response.data.isNullOrEmpty()) {
                        _uiState.value = CondominioUiState.Empty
                    } else{
                        _uiState.value = CondominioUiState.Success(response.data!!)
                    }
                } else {
                    _uiState.value = CondominioUiState.Error(response.mensaje!!)
                }
            }catch (e: Exception){
                _uiState.value = CondominioUiState.Error("Error de red: ${e.message}")
            }
        }
    }
}