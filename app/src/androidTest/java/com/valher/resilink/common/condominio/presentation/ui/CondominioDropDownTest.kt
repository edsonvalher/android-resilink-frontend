package com.valher.resilink.common.condominio.presentation.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.valher.resilink.TestActivity
import com.valher.resilink.common.condominio.data.model.Condominio
import com.valher.resilink.common.condominio.presentation.viewmodel.CondominioViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.MockKAnnotations
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class CondominioDropDownTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    //se debe utilizar otro activity registrado en el AndroidManifest.xml para que no intervenga en el uso del MainActivity
    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<TestActivity>()

    private lateinit var viewModel: CondominioViewModel

    private val mockCondominios = listOf(
        Condominio(id = "1", nombre = "Condominio 1", version = "1.0", activo = true),
        Condominio(id = "2", nombre = "Condominio 2", version = "1.0", activo = true)
    )

    // Utilizar instancias reales de MutableStateFlow
    private val isLoadingFlow = MutableStateFlow(false)
    private val errorMessageFlow = MutableStateFlow<String?>(null)
    private val condominiosFlow = MutableStateFlow(mockCondominios)

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        hiltRule.inject()

        // Crear una implementación personalizada del ViewModel
        viewModel = object : CondominioViewModel(mockk(relaxed = true)) {
            override val isLoading = isLoadingFlow
            override val errorMessage = errorMessageFlow
            override val condominios = condominiosFlow
        }
    }

    @Test
    fun muestraProgressIndicatorCuandoLoadingEsTrue() {
        // Simula el estado de carga
        isLoadingFlow.value = true

        // Establecer el contenido con el ViewModel simulado
        composeTestRule.setContent {
            CondominioDropDown(viewModel = viewModel)
        }

        // Verifica que se está mostrando el CircularProgressIndicator
        composeTestRule.onNodeWithTag("ProgressIndicator").assertExists().assertIsDisplayed()
    }

    @Test
    fun muestraMensajeDeErrorCuandoErrorMessageNoEsNull() {
        // Simula el estado de error
        errorMessageFlow.value = "No fue posible cargar el control"

        // Establecer el contenido con el ViewModel simulado
        composeTestRule.setContent {
            CondominioDropDown(viewModel = viewModel)
        }

        // Verifica que se está mostrando el mensaje de error
        composeTestRule.onNodeWithTag("ErrorMessage").assertExists().assertIsDisplayed()
    }

    @Test
    fun muestraDropdownMenuCuandoCondominiosEstanCargados() {
        // Simula el estado cargado
        isLoadingFlow.value = false
        errorMessageFlow.value = null

        // Establecer el contenido con el ViewModel simulado
        composeTestRule.setContent {
            CondominioDropDown(viewModel = viewModel)
        }

        // Verifica que los elementos del menú desplegable están visibles
        composeTestRule.onNodeWithTag("DropdownMenu").assertExists().assertIsDisplayed()
    }
}
