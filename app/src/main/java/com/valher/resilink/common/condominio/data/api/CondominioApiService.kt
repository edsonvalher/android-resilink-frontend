package com.valher.resilink.common.condominio.data.api

import com.valher.core.model.NetworkResponse
import com.valher.resilink.common.condominio.data.model.Condominio
import retrofit2.Response
import retrofit2.http.GET

interface CondominioApiService {
    @GET("condominios")
    suspend fun getCondominios(): Response<NetworkResponse<List<Condominio>>>
}