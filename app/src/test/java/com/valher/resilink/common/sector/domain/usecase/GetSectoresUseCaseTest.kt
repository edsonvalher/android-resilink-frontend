package com.valher.resilink.common.sector.domain.usecase

import com.valher.core.model.NetworkResponse
import com.valher.resilink.common.sector.data.model.Sector
import com.valher.resilink.common.sector.data.repository.SectorRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever


class GetSectoresUseCaseTest{
    private val repository = mock<SectorRepository>()
    private val useCase = GetSectoresUseCase(repository)

    @Test
    fun `deberia invocar apropiadamente el repositorio`(): Unit = runBlocking {
        //GIVEN
        val mockCondominioId = "1"
        val mockSectores = listOf(
            Sector(id = "1", nombre = "Sector 1", activo = true),
            Sector(id = "2", nombre = "Sector 2", activo = true)
        )
        whenever(repository.getSectores(mockCondominioId)).thenReturn(NetworkResponse(data = mockSectores, mensaje = null, estado = true))
        //WHEN
        val response = useCase.invoke(mockCondominioId)
        //THEN
        assertEquals(mockSectores, response.data)
        assertEquals(true, response.estado)
        assertEquals(null, response.mensaje)
    }

}