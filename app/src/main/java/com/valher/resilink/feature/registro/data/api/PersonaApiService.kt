package com.valher.resilink.feature.registro.data.api

import com.valher.core.model.NetworkResponse
import com.valher.core.network.ApiService
import com.valher.resilink.feature.registro.data.model.Persona
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface PersonaApiService: ApiService {
    @POST("/condominios/{condominioId}/sectores/{sectorId}/personas")
    suspend fun registrarPersona(
        @Path("condominioId") condominioId: String,
        @Path("sectorId") sectorId: String,
        @Body persona: Persona
    ): Response<NetworkResponse<Persona>>
}