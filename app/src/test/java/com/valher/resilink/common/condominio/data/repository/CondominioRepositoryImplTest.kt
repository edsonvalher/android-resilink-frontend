package com.valher.resilink.common.condominio.data.repository
import com.valher.resilink.common.condominio.data.api.CondominioApiService
import com.valher.resilink.common.condominio.data.model.Condominio
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.kotlin.*
import retrofit2.Response

class CondominioRepositoryImplTest{
    private val apiService = mock<CondominioApiService>()
    private val repository = CondominioRepositoryImpl(apiService)

    @Test
    fun `Deberia retornar un listado de condominios al llamar el API exitosamente`() = runTest {
        //GIVEN
        val mockCondominios = listOf(
            Condominio(id = "1", nombre = "Condominio 1", version = "1.0", activo = true),
            Condominio(id = "2", nombre = "Condominio 2", version = "1.0", activo = true)
        )
        whenever(apiService.getCondominios()).thenReturn(Response.success(mockCondominios))
        //WHEN
        val result = apiService.getCondominios()
        //THEN
        assertTrue(result.isSuccessful)
        assertEquals(mockCondominios, result.body())
    }

    @Test
    fun `Deberia retornar un error al llamar el API con error`() = runTest {
        //GIVEN
        val errorResponse = Response.error<List<Condominio>>(
            404,
            ResponseBody.create("application/json".toMediaType(), "{}")
        )
        whenever(apiService.getCondominios()).thenReturn(errorResponse)
        //WHEN
        val result = apiService.getCondominios()
        //THEN
        assertEquals(404, result.code())
        assertEquals(false, result.isSuccessful)
        assertEquals(null, result.body())
    }
}