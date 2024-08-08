package com.valher.core.di

import com.valher.core.CoreConfig
import com.valher.core.network.ApiService
import com.valher.core.network.BasicAuthInterceptor
import com.valher.core.network.JwtAuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    @Named("basic")
    fun provideBasicAuthOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = if (CoreConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE

        val username = CoreConfig.USERNAME // Reemplaza con tu usuario
        val password = CoreConfig.PASSWORD // Reemplaza con tu contraseña

        return OkHttpClient.Builder()
            .addInterceptor(BasicAuthInterceptor(username, password))
            .addInterceptor(logging)
            .build()
    }

    @Provides
    @Singleton
    @Named("jwt")
    fun provideJwtAuthOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = if (CoreConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE

        val token = "tu_token_jwt" // Reemplaza con tu token JWT

        return OkHttpClient.Builder()
            .addInterceptor(JwtAuthInterceptor(token))
            .addInterceptor(logging)
            .build()
    }

    @Provides
    @Singleton
    @Named("basic")
    fun provideBasicAuthRetrofit(@Named("basic") okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("${CoreConfig.BASE_URL}/api/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    @Provides
    @Singleton
    @Named("jwt")
    fun provideJwtAuthRetrofit(@Named("jwt") okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("${CoreConfig.BASE_URL}/api/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    @Named("basic")
    fun provideBasicAuthApiService(@Named("basic") retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    @Named("jwt")
    fun provideJwtAuthApiService(@Named("jwt") retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }


}