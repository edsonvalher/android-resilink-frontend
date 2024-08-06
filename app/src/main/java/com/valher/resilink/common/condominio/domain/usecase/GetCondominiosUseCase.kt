package com.valher.resilink.common.condominio.domain.usecase

import com.valher.core.model.NetworkResponse
import com.valher.resilink.common.condominio.data.model.Condominio
import com.valher.resilink.common.condominio.data.repository.CondominioRepository
import javax.inject.Inject

class GetCondominiosUseCase @Inject constructor(
    private val repository: CondominioRepository
) {
    suspend operator fun invoke(): NetworkResponse<List<Condominio>> {
        return repository.getCondominios()
    }
}