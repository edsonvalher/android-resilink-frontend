package com.valher.resilink.common.condominio.data.repository

import com.valher.core.model.NetworkResponse
import com.valher.resilink.common.condominio.data.model.Condominio

interface CondominioRepository {
    suspend fun getCondominios(): NetworkResponse<List<Condominio>>
}