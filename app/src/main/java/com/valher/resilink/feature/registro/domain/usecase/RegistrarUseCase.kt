package com.valher.resilink.feature.registro.domain.usecase

import com.valher.resilink.feature.registro.data.model.Persona
import com.valher.resilink.feature.registro.data.repository.PersonaRepository
import com.valher.core.model.NetworkResponse
import javax.inject.Inject

class RegistrarUseCase @Inject constructor(
    private val repository: PersonaRepository
) {
    suspend operator fun invoke(
        condominioId: String,
        sectorId: String,
        persona: Persona
    ): NetworkResponse<Persona> {
        return repository.registrarPersona(condominioId, sectorId, persona)
    }
}