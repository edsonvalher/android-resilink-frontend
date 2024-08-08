package com.valher.resilink.common.condominio.presentation.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.valher.resilink.common.condominio.data.model.Condominio
import com.valher.resilink.common.condominio.presentation.viewmodel.CondominioViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever



class CondominioDropDownKtTest{
    @get:Rule
    val composeTestRule = createComposeRule()

    @Mock
    lateinit var viewModel: CondominioViewModel

    private val mockCondominios = listOf(
        Condominio(id = "1", nombre = "Condominio 1", version = "1.0", activo = true),
        Condominio(id = "2", nombre = "Condominio 2", version = "1.0", activo = true)
    )
    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)

        // Simula los flujos de estado en el ViewModel
        whenever(viewModel.isLoading).thenReturn(MutableStateFlow(false))
        whenever(viewModel.errorMessage).thenReturn(MutableStateFlow(null))
        whenever(viewModel.condominios).thenReturn(MutableStateFlow(mockCondominios))
    }

    @Test
    fun testLoadingState() {
        // Simula el estado de carga
        whenever(viewModel.isLoading).thenReturn(MutableStateFlow(true))

        composeTestRule.setContent {
            CondominioDropDown()
        }
        // Verifica que se está mostrando el CircularProgressIndicator
        composeTestRule.onNodeWithTag("ProgressIndicator").assertExists()
    }
}