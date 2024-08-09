package com.valher.resilink

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import com.valher.resilink.common.sector.presentation.ui.SectorDropDown
import com.valher.resilink.feature.registro.presentation.ui.RegistrarPersonaScreen
import com.valher.resilink.routes.Routes

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ResilinkTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainContent(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun MainContent(modifier: Modifier = Modifier) {
    /*
    Column(modifier = modifier.fillMaxSize()) {

    }
    */
    val navigationController = rememberNavController()
    NavHost(navController = navigationController, startDestination = Routes.Registrarse.route) {
        composable(Routes.Registrarse.route) { RegistrarPersonaScreen(navigationController) }
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