package com.valher.resilink.common.sector.data.repository

import com.valher.core.model.NetworkResponse
import com.valher.resilink.common.sector.data.api.SectorApiService
import com.valher.resilink.common.sector.data.model.Sector
import javax.inject.Inject

class SectorRepositoryImpl @Inject constructor(
    private val apiService: SectorApiService
) : SectorRepository{
    override suspend fun getSectores(condominioId: String): NetworkResponse<List<Sector>> {
        val response = apiService.getSectores(condominioId)
        return if (response.isSuccessful) {
            response.body() ?: NetworkResponse(null, "Respuesta vacía", false)
        } else {
            NetworkResponse(null, "Error de red: ${response.code()} ${response.message()}", false)
        }
    }
}