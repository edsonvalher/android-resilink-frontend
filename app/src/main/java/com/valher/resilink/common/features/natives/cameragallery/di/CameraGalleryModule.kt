package com.valher.resilink.common.features.natives.cameragallery.di

import android.content.Context
import com.valher.resilink.common.features.natives.cameragallery.data.repositories.ImagePickerRepository
import com.valher.resilink.common.features.natives.cameragallery.data.repositories.ImagePickerRepositoryImpl
import com.valher.resilink.common.features.natives.cameragallery.data.repositories.PermissionsRepository
import com.valher.resilink.common.features.natives.cameragallery.data.repositories.PermissionsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
object CameraGalleryModule {

    @Provides
    @ActivityRetainedScoped
    fun providePermissionsRepository(
        @ApplicationContext context: Context
    ): PermissionsRepository {
        return PermissionsRepositoryImpl(context)
    }

    @Provides
    @ActivityRetainedScoped
    fun provideImagePickerRepository(
        @ApplicationContext context: Context
    ): ImagePickerRepository {
        return ImagePickerRepositoryImpl(context)
    }
}