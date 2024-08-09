package com.valher.resilink.feature.registro.data.repository

import com.valher.core.model.NetworkResponse
import com.valher.resilink.feature.registro.data.model.Persona

interface PersonaRepository {
    suspend fun registrarPersona(
        condominioId: String,
        sectorId: String,
        persona: Persona): NetworkResponse<Persona>
}