package com.valher.resilink.feature.registro.domain.usecase

import com.valher.resilink.feature.registro.data.model.Persona
import com.valher.resilink.feature.registro.data.repository.RegisterRepository
import com.valher.core.model.NetworkResponse
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: RegisterRepository
) {
    suspend operator fun invoke(persona: Persona): NetworkResponse<Persona> {
        // Puedes añadir lógica adicional o validaciones aquí
        return repository.registerPersona(persona)
    }
}