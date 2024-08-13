package com.valher.resilink.common.features.natives.cameragallery.domain.usecases

import android.app.Activity
import com.valher.resilink.common.features.natives.cameragallery.data.repositories.ImagePickerRepository
import com.valher.resilink.common.features.natives.cameragallery.data.repositories.PermissionsRepository
import javax.inject.Inject

class TakePhotoWithCameraUseCase @Inject constructor(
    private val permissionsRepository: PermissionsRepository,
    private val imagePickerRepository: ImagePickerRepository
) {
    suspend operator fun invoke(activity: Activity) {
        if (permissionsRepository.hasCameraAndStoragePermissions()) {
            imagePickerRepository.openCamera(activity)
        } else {
            permissionsRepository.requestCameraAndStoragePermissions(activity)
        }
    }
}