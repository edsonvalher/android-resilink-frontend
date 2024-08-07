package com.valher.core.network
import com.valher.core.model.NetworkResponse
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

object NetworkRequest {

    suspend fun <T : Any> execute(call: suspend () -> Response<T>): NetworkResponse<T> {
        return try {
            val response = call.invoke()
            if (response.isSuccessful) {
                NetworkResponse(data = response.body(), mensaje = null, estado = true)
            } else {
                NetworkResponse(data = null, mensaje = "Error: ${response.code()} ${response.message()}",estado = false)
            }
        } catch (e: HttpException) {
            NetworkResponse(data = null, mensaje = "Exception: ${e.message()}", estado = false)
        } catch (e: IOException) {
            NetworkResponse(data = null, mensaje = "Network Error: ${e.message}", estado = false)
        } catch (e: Exception) {
            NetworkResponse(data = null, mensaje = "Unexpected Error: ${e.message}", estado = false)
        }
    }
}
