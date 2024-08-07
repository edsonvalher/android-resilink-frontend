package com.valher.core.network

import okhttp3.Interceptor
import okhttp3.Response

abstract class AuthInterceptor : Interceptor {
    abstract fun getAuthHeader(): String

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .header("Authorization", getAuthHeader())
            .build()
        return chain.proceed(request)
    }
}
