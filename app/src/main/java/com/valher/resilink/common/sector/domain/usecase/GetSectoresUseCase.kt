package com.valher.resilink.common.sector.domain.usecase

import com.valher.core.model.NetworkResponse
import com.valher.resilink.common.sector.data.model.Sector
import com.valher.resilink.common.sector.data.repository.SectorRepository
import javax.inject.Inject

class GetSectoresUseCase @Inject constructor(
private val repository: SectorRepository
){
    suspend operator fun invoke(condominioId: String): NetworkResponse<List<Sector>> {
        return repository.getSectores(condominioId)
    }
}