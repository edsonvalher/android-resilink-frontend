package com.valher.resilink.feature.registro.data.api

import com.valher.core.network.ApiService
import com.valher.resilink.feature.registro.data.model.Persona
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterApiService: ApiService {
    @POST("personas/register") // Cambia el endpoint según tu API
    suspend fun registerPersona(@Body persona: Persona): Response<Persona>
}