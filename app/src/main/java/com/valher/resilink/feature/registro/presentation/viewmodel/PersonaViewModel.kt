package com.valher.resilink.feature.registro.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valher.resilink.feature.registro.data.model.Persona
import com.valher.resilink.feature.registro.domain.usecase.RegistrarUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonaViewModel @Inject constructor(
    private val registrarUseCase: RegistrarUseCase
): ViewModel() {

    private val _persona = MutableStateFlow<Persona?>(null)
    open val persona: MutableStateFlow<Persona?> = _persona
    private val _errorMessage = MutableStateFlow<String?>(null)
    open val errorMessage: MutableStateFlow<String?> = _errorMessage
    private val _isLoading = MutableStateFlow(false)
    open val isLoading: MutableStateFlow<Boolean> = _isLoading


    fun registrarPersona(condominioId: String, sectorId: String, persona: Persona) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = registrarUseCase(condominioId, sectorId, persona)
                if (response.estado) {
                    _persona.value = response.data
                    _errorMessage.value = null
                } else {
                    _errorMessage.value = response.mensaje
                }

            }catch (e: Exception){
                _errorMessage.value = "Error de red: ${e.message}"

            }finally {
                _isLoading.value = false
            }
        }
    }



}