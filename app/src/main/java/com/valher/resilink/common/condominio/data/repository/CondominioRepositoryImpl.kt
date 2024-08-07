package com.valher.resilink.common.condominio.data.repository

import com.valher.core.model.NetworkResponse
import com.valher.core.network.NetworkRequest
import com.valher.resilink.common.condominio.data.api.CondominioApiService
import com.valher.resilink.common.condominio.data.model.Condominio
import javax.inject.Inject
import javax.inject.Named

class CondominioRepositoryImpl @Inject constructor(
    private val apiService: CondominioApiService
) : CondominioRepository {
    override suspend fun getCondominios(): NetworkResponse<List<Condominio>> {
        val response = apiService.getCondominios()
        return if (response.isSuccessful) {
            response.body() ?: NetworkResponse(null, "Respuesta vacía", false)
        } else {
            NetworkResponse(null, "Error de red: ${response.code()} ${response.message()}", false)
        }

    }
}