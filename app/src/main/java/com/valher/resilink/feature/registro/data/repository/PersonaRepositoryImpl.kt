package com.valher.resilink.feature.registro.data.repository

import com.valher.core.model.NetworkResponse
import com.valher.core.network.NetworkRequest
import com.valher.resilink.feature.registro.data.api.PersonaApiService
import com.valher.resilink.feature.registro.data.model.Persona
import javax.inject.Inject

class PersonaRepositoryImpl @Inject constructor(
    private val apiService: PersonaApiService
) : PersonaRepository {
    override suspend fun registrarPersona(
        condominioId: String,
        sectorId: String,
        persona: Persona
    ): NetworkResponse<Persona> {

        val response = apiService.registrarPersona(condominioId, sectorId, persona)
        return if (response.isSuccessful) {
            response.body() ?: NetworkResponse(null, "Respuesta vacía", false)
        } else {
            NetworkResponse(null, "Error de red: ${response.code()} ${response.message()}", false)
        }
    }

}