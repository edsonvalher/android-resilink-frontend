package com.valher.resilink

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.valher.resilink.ui.theme.ResilinkTheme
import dagger.hilt.android.AndroidEntryPoint
import com.valher.resilink.common.condominio.presentation.ui.CondominioDropDown
import com.valher.resilink.common.features.natives.cameragallery.presentation.viewmodel.CameraGalleryViewModel
import com.valher.resilink.common.sector.presentation.ui.SectorDropDown
import com.valher.resilink.feature.registro.presentation.ui.RegistrarPersonaScreen
import com.valher.resilink.routes.Routes

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: CameraGalleryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val pickImageLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val imageUri: Uri? = result.data?.data
                imageUri?.let {
                    viewModel.onAddPhotoClicked(this, it)
                }
            }
        }

        val takePhotoLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val imageUri: Uri? = result.data?.data
                imageUri?.let {
                    viewModel.onTakePhotoClicked(this, it)
                }
            }
        }

        enableEdgeToEdge()
        setContent {
            ResilinkTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainContent(
                        modifier = Modifier.padding(innerPadding),
                        onPickImage = { pickImageLauncher.launch(it) },
                        onTakePhoto = { takePhotoLauncher.launch(it) }
                    )
                }
            }
        }
    }
}

@Composable
fun MainContent(
    modifier: Modifier = Modifier,
    onPickImage: (android.content.Intent) -> Unit,
    onTakePhoto: (android.content.Intent) -> Unit

) {

    val navigationController = rememberNavController()
    NavHost(navController = navigationController, startDestination = Routes.Registrarse.route) {
        composable(Routes.Registrarse.route) { RegistrarPersonaScreen(
            navigationController,
            onPickImage = onPickImage,
            onTakePhoto = onTakePhoto
        ) }
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