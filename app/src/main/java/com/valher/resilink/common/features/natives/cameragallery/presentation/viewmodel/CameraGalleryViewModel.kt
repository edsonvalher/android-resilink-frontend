package com.valher.resilink.common.features.natives.cameragallery.presentation.viewmodel

import android.app.Activity
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valher.resilink.common.features.natives.cameragallery.domain.usecases.SelectImageFromGalleryUseCase
import com.valher.resilink.common.features.natives.cameragallery.domain.usecases.TakePhotoWithCameraUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CameraGalleryViewModel @Inject constructor(
    private val selectImageFromGalleryUseCase: SelectImageFromGalleryUseCase,
    private val takePhotoWithCameraUseCase: TakePhotoWithCameraUseCase
) : ViewModel() {
    private val _imageUri = MutableStateFlow<Uri?>(null)
    val imageUri: StateFlow<Uri?> = _imageUri.asStateFlow()

    fun onAddPhotoClicked(activity: Activity, uri: Uri) {
        viewModelScope.launch {
            selectImageFromGalleryUseCase(activity)
            _imageUri.value = uri
        }
    }

    fun onTakePhotoClicked(activity: Activity, uri: Uri) {
        viewModelScope.launch {
            takePhotoWithCameraUseCase(activity)
            _imageUri.value = uri
        }
    }
}