package com.valher.core.network

class JwtAuthInterceptor(private val token: String) : AuthInterceptor() {

    override fun getAuthHeader(): String {
        return "Bearer $token"
    }
}
