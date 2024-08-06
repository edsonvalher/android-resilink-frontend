package com.valher.resilink.common.condominio.domain.usecase

import com.valher.core.model.NetworkResponse
import com.valher.resilink.common.condominio.data.model.Condominio
import com.valher.resilink.common.condominio.data.repository.CondominioRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class GetCondominiosUseCaseTest{
    private val repository = mock<CondominioRepository>()
    private val useCase = GetCondominiosUseCase(repository)

    @Test
    fun `invoke should handle repository exceptions correctly`() = runBlocking {
        //GIVEN
        val mockCondominios = listOf(
            Condominio(id = "1", nombre = "Condominio 1", version = "1.0", activo = true),
            Condominio(id = "2", nombre = "Condominio 2", version = "1.0", activo = true)
        )
        whenever(repository.getCondominios()).thenReturn(NetworkResponse(data = mockCondominios, mensaje = null, estado = true))
        //WHEN
        val response = useCase.invoke()

        //THEN
        assertEquals(NetworkResponse(data = mockCondominios, mensaje = null, estado = true), response)
    }
}