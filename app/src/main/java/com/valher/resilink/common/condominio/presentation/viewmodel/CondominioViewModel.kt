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
    private val getCondominiosUseCase: GetCondominiosUseCase): ViewModel(){
        private val _condominios = MutableStateFlow<List<Condominio>>(emptyList())
    open val condominios: StateFlow<List<Condominio>> = _condominios
        private val _errorMessage = MutableStateFlow<String?>(null)
    open val errorMessage: StateFlow<String?> = _errorMessage
        private val _isLoading = MutableStateFlow(false)
    open val isLoading: StateFlow<Boolean> = _isLoading

    init {
        loadCondominios()
    }

    private fun loadCondominios() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = getCondominiosUseCase()
                if (response.estado) {
                    _condominios.value = response.data ?: emptyList()
                    _errorMessage.value = null
                } else {
                    _errorMessage.value = response.mensaje
                }
            }catch (e: Exception){
                _errorMessage.value = "Error de red: ${e.message}"
            }
            finally {
                _isLoading.value = false
            }
        }
    }
}