package com.valher.core.network
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.*
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.check
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class InterceptorTest {
    @Test
    fun `BasicAuthInterceptor debe traer un authorization header`() {
        //GIVEN
        val username = "testuser"
        val password = "testpass"
        val interceptor = BasicAuthInterceptor(username, password)

        //simula la cadena de interceptores en una solicitud HTTP
        val chain = mock<Interceptor.Chain>()
        val request = Request.Builder()
            .url("https://pruebas.com")
            .build()

        val response = Response.Builder()
            .request(request)
            .protocol(okhttp3.Protocol.HTTP_1_1)
            .code(200)
            .message("")
            .body(ResponseBody.create(null, ""))
            .build()

        //WHEN
        `when`(chain.request()).thenReturn(request)
        whenever(chain.proceed(anyOrNull())).thenReturn(response)

        interceptor.intercept(chain)

        //THEN
        verify(chain).proceed(check {
            assertEquals("Basic dGVzdHVzZXI6dGVzdHBhc3M=", it.header("Authorization"))
        })
    }

    @Test
    fun `JWTAuthInterceptor debe traer un authorization header`(){
        //GIVEN
        val token = "testtoken"
        val interceptor = JwtAuthInterceptor(token)
        val chain = mock<Interceptor.Chain>()
        val request = Request.Builder()
            .url("https://pruebas.com")
            .build()

        val response = Response.Builder()
            .request(request)
            .protocol(okhttp3.Protocol.HTTP_1_1)
            .code(200)
            .message("")
            .body(ResponseBody.create(null, ""))
            .build()

        //WHEN
        `when`(chain.request()).thenReturn(request)
        whenever(chain.proceed(anyOrNull())).thenReturn(response)

        interceptor.intercept(chain)

        //THEN
        verify(chain).proceed(check {
            assertEquals("Bearer testtoken", it.header("Authorization"))
        })

    }

}