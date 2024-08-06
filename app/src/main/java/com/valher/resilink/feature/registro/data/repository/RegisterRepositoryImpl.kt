package com.valher.resilink.feature.registro.data.repository

import com.valher.core.model.NetworkResponse
import com.valher.core.network.NetworkRequest
import com.valher.resilink.feature.registro.data.api.RegisterApiService
import com.valher.resilink.feature.registro.data.model.Persona
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(
    private val apiService: RegisterApiService
) : RegisterRepository {

    override suspend fun registerPersona(persona: Persona): NetworkResponse<Persona> {
        return NetworkRequest.execute { apiService.registerPersona(persona) }
    }
}