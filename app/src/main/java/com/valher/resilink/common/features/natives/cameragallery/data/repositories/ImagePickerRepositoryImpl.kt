package com.valher.resilink.common.features.natives.cameragallery.data.repositories
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.provider.MediaStore
import javax.inject.Inject

class ImagePickerRepositoryImpl @Inject constructor(
    private val context: Context
) : ImagePickerRepository {

    override fun pickImage(activity: Activity) {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        activity.startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST)
    }

    override fun openCamera(activity: Activity) {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        activity.startActivityForResult(cameraIntent, TAKE_PICTURE_REQUEST)
    }

    companion object {
        const val PICK_IMAGE_REQUEST = 1
        const val TAKE_PICTURE_REQUEST = 2
    }
}