package com.valher.core.network

import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("public-endpoint")
    suspend fun getPublicData(): Response<List<Any>>

    @GET("secure-endpoint")
    suspend fun getSecureData(): Response<List<Any>>
}