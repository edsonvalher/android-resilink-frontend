package com.valher.resilink.common.sector.di

import com.valher.resilink.common.sector.data.api.SectorApiService
import com.valher.resilink.common.sector.data.repository.SectorRepository
import com.valher.resilink.common.sector.data.repository.SectorRepositoryImpl
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
abstract class SectorModule{
    @Binds
    @Singleton
    abstract fun bindSectorRepository(
        impl: SectorRepositoryImpl
    ): SectorRepository
    companion object {
        @Provides
        @Singleton
        fun provideSectorApiService(
            @Named("basic") retrofit: Retrofit
        ): SectorApiService {
            return retrofit.create(SectorApiService::class.java)
        }
    }

}