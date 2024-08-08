package com.valher.resilink.common.sector.data.api

import com.valher.core.model.NetworkResponse
import com.valher.resilink.common.condominio.data.model.Condominio
import com.valher.resilink.common.sector.data.model.Sector
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface SectorApiService {
    @GET("condominios/{condominioId}/sectores")
    suspend fun getSectores(@Path("condominioId") condominioId: String): Response<NetworkResponse<List<Sector>>>
}