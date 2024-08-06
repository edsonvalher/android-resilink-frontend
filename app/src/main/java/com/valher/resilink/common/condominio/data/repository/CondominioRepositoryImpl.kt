package com.valher.resilink.common.condominio.data.repository

import com.valher.core.model.NetworkResponse
import com.valher.core.network.NetworkRequest
import com.valher.resilink.common.condominio.data.api.CondominioApiService
import com.valher.resilink.common.condominio.data.model.Condominio
import javax.inject.Inject
import javax.inject.Named

class CondominioRepositoryImpl @Inject constructor(
    @Named("basic") private val apiService: CondominioApiService
) : CondominioRepository {

    override suspend fun getCondominios(): NetworkResponse<List<Condominio>> {
        return NetworkRequest.execute {
            apiService.getCondominios()
        }
    }
}