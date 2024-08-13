package com.valher.resilink.common.features.natives.cameragallery.data.repositories

import android.app.Activity

interface PermissionsRepository {
    fun hasCameraAndStoragePermissions(): Boolean
    fun requestCameraAndStoragePermissions(activity: Activity)
}