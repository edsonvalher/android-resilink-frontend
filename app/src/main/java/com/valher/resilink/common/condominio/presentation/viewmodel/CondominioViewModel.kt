package com.valher.resilink.common.condominio.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valher.core.model.NetworkResponse
import com.valher.resilink.common.condominio.data.model.Condominio
import com.valher.resilink.common.condominio.domain.usecase.GetCondominiosUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class CondominioViewModel  @Inject constructor(
    private val getCondominiosUseCase: GetCondominiosUseCase): ViewModel(){
        private val _condominios = MutableStateFlow<List<Condominio>>(emptyList())
    val condominios: StateFlow<List<Condominio>> = _condominios
        private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    init {
        loadCondominios()
    }

    private fun loadCondominios() {
        viewModelScope.launch {
            when (val response = getCondominiosUseCase()){
                is NetworkResponse<List<Condominio>> -> {
                    if(response.estado){
                        _condominios.value = response.data ?: emptyList()
                    }else{
                        _errorMessage.value = response.mensaje
                    }

                }
            }

        }
    }
}