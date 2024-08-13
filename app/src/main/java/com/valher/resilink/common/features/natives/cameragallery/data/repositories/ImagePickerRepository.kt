package com.valher.resilink.common.features.natives.cameragallery.data.repositories

import android.app.Activity

interface ImagePickerRepository {
    fun pickImage(activity: Activity)
    fun openCamera(activity: Activity)
}