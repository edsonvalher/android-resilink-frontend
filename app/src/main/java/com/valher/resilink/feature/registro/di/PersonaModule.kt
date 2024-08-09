package com.valher.resilink.feature.registro.di

import com.valher.resilink.feature.registro.data.api.PersonaApiService
import com.valher.resilink.feature.registro.data.repository.PersonaRepository
import com.valher.resilink.feature.registro.data.repository.PersonaRepositoryImpl
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
abstract class PersonaModule {

    @Binds
    @Singleton
    abstract fun bindPersonaRepository(
        impl: PersonaRepositoryImpl
    ): PersonaRepository

    companion object {
        @Provides
        @Singleton
        fun providePersonaApiService(
            @Named("basic") retrofit: Retrofit
        ): PersonaApiService {
            return retrofit.create(PersonaApiService::class.java)
        }
    }
}