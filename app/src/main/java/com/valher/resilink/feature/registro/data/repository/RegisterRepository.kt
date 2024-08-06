package com.valher.resilink.feature.registro.data.repository

import com.valher.core.model.NetworkResponse
import com.valher.resilink.feature.registro.data.model.Persona

interface RegisterRepository {
    suspend fun registerPersona(persona: Persona): NetworkResponse<Persona>
}