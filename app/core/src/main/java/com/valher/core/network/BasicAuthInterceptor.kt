package com.valher.core.network
import okhttp3.Credentials

class BasicAuthInterceptor(
    private val username: String,
    private val password: String
) : AuthInterceptor() {

    override fun getAuthHeader(): String {
        return Credentials.basic(username, password)
    }
}
