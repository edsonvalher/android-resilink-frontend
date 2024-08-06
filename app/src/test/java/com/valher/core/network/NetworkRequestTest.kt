package com.valher.core.network

import com.valher.core.model.NetworkResponse
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody
import org.junit.Test
import org.junit.Assert.assertEquals
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException


class NetworkRequestTest{

    @Test
    fun `Debe retornar success en la respuesta cuando el API sea exitoso`() = runBlocking {
        // GIVEN
        val data = "Success"
        val response = Response.success(data)

        // WHEN
        val result = NetworkRequest.execute { response }

        // THEN
        assertEquals(NetworkResponse(data = data, mensaje = null, estado = true), result)
    }

    @Test
    fun `Debe retornar error en la respuesta cuando el API falle HttpException`() = runBlocking {
        // GIVEN
        val responseBody = ResponseBody.create("application/json".toMediaType(), "{}")
        val httpException = HttpException(Response.error<String>(404, responseBody))

        // WHEN
        val result: NetworkResponse<String> = NetworkRequest.execute { throw httpException }

        // THEN
        assertEquals(false, result.estado)
        assertEquals("Exception: Response.error()", result.mensaje)
        assertEquals(null, result.data)
    }

    @Test
    fun `Debe retornar error en la respuesta cuando el API falle IOException`() = runBlocking {
        // Ejecutar y especificar el tipo esperado
        val result: NetworkResponse<String> = NetworkRequest.execute { throw IOException("Network Error") }

        // Verificar
        assertEquals(false, result.estado)
        assertEquals("Network Error: Network Error", result.mensaje)
        assertEquals(null, result.data)
    }

}