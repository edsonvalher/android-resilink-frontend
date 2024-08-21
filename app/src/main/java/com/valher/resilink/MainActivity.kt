package com.valher.resilink

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.valher.resilink.common.features.natives.cameragallery.presentation.viewmodel.CameraGalleryViewModel
import com.valher.resilink.feature.registro.presentation.ui.RegistrarPersonaScreen
import com.valher.resilink.routes.Routes
import com.valher.resilink.ui.theme.ResilinkTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.File

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val cameraGalleryViewModel: CameraGalleryViewModel by viewModels()
    private var tempImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Gestor de permisos
        val requestPermissionsLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            val allGranted = permissions.entries.all { it.value }
            cameraGalleryViewModel.onPermissionsResult(allGranted)
        }

        // Gestor para seleccionar una imagen desde la galería
        val pickImageLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val imageUri: Uri? = result.data?.data
                imageUri?.let {
                    cameraGalleryViewModel.setImageUriGallery(it)
                }
            }
        }

        // Gestor para tomar una foto con la cámara
        val takePhotoLauncher = registerForActivityResult(
            ActivityResultContracts.TakePicture()
        ) { success ->
            if (success && tempImageUri != null) {
                cameraGalleryViewModel.setImageUriCamera(tempImageUri!!)
            } else {
                tempImageUri = null
            }
        }

        // Observar si se deben solicitar permisos
        lifecycleScope.launch {
            cameraGalleryViewModel.requestPermissions.collect { shouldRequest ->
                if (shouldRequest) {
                    val requiredPermissions = arrayOf(
                        android.Manifest.permission.CAMERA,
                        android.Manifest.permission.READ_EXTERNAL_STORAGE
                    )
                    requestPermissionsLauncher.launch(requiredPermissions)
                }
            }
        }

        enableEdgeToEdge()
        setContent {
            ResilinkTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainContent(
                        modifier = Modifier.padding(innerPadding),
                        cameraGalleryViewModel = cameraGalleryViewModel,
                        onPickImage = { pickImageLauncher.launch(it) },
                        onTakePhoto = {
                            tempImageUri = createImageUri()
                            takePhotoLauncher.launch(tempImageUri!!)
                        }

                    )
                }
            }
        }
    }

    // Método para crear una URI a partir de un archivo temporal
    private fun createImageUri(): Uri {
        val imageFile = File(externalCacheDir, "${System.currentTimeMillis()}.jpg")
        return FileProvider.getUriForFile(this, "$packageName.fileprovider", imageFile)
    }
}

@Composable
fun MainContent(
    modifier: Modifier = Modifier,
    cameraGalleryViewModel: CameraGalleryViewModel= hiltViewModel(),
    onPickImage: (android.content.Intent) -> Unit,
    onTakePhoto: (android.content.Intent) -> Unit,
) {

    val navigationController = rememberNavController()
    NavHost(navController = navigationController, startDestination = Routes.Registrarse.route) {
        composable(Routes.Registrarse.route) {
                RegistrarPersonaScreen(
                    navigationController,
                    onPickImage = onPickImage,
                    onTakePhoto = onTakePhoto,
                    viewmodel = cameraGalleryViewModel
                )
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ResilinkTheme {
        Greeting("Android")
    }
}
