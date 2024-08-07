package com.valher.resilink.common.condominio.di

import com.valher.resilink.common.condominio.data.api.CondominioApiService
import com.valher.resilink.common.condominio.data.repository.CondominioRepository
import com.valher.resilink.common.condominio.data.repository.CondominioRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CondominioModule {

    @Binds
    @Singleton
    abstract fun bindCondominioRepository(
        impl: CondominioRepositoryImpl
    ): CondominioRepository

    companion object {
        @Provides
        @Singleton
        fun provideCondominioApiService(
            @Named("basic") retrofit: Retrofit
        ): CondominioApiService {
            return retrofit.create(CondominioApiService::class.java)
        }
    }
}
