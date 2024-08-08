package com.valher.resilink.common.sector.data.repository

import com.valher.core.model.NetworkResponse
import com.valher.resilink.common.sector.data.api.SectorApiService
import com.valher.resilink.common.sector.data.model.Sector
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import retrofit2.Response


class SectorRepositoryImplTest{
    private val apiService = mock<SectorApiService>()
    private val repository = SectorRepositoryImpl(apiService)

    @Test
    fun `Deberia retornar un listado de sectores al llamar el API exitosamente`() = runTest {
        //GIVEN
        var mockSectores = listOf(
            Sector(id = "1", nombre = "Sector 1", activo = true),
            Sector(id = "2", nombre = "Sector 2", activo = true)
        )
        val mockResponse = NetworkResponse(
            data = mockSectores,
            mensaje = "Sectores obtenidos exitosamente",
            estado = true
        )
        whenever(apiService.getSectores("1")).thenReturn(Response.success(mockResponse))
        //WHEN
        val result = repository.getSectores("1")
        //THEN
        assertEquals(true, result.estado)
        assertEquals(mockSectores, result.data)
    }

    @Test
    fun `Deberia retornar un error al llamar el API con error`() = runTest {
        //GIVEN
        val errorResponseBody = ResponseBody.create("application/json".toMediaType(), "{}")
        val errorResponse = Response.error<NetworkResponse<List<Sector>>>(404, errorResponseBody)
        whenever(apiService.getSectores("1")).thenReturn(errorResponse)
        //WHEN
        val result = apiService.getSectores("1")
        //THEN
        assertEquals(404, result.code())
        assertEquals(false, result.isSuccessful)

    }


}