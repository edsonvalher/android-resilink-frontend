package com.valher.resilink.common.sector.data.repository

import com.valher.core.model.NetworkResponse
import com.valher.resilink.common.sector.data.model.Sector

interface SectorRepository {
    suspend fun getSectores(condominioId: String): NetworkResponse<List<Sector>>
}