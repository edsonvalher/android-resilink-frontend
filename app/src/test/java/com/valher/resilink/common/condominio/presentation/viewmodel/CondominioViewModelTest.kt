package com.valher.resilink.common.condominio.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.valher.core.model.NetworkResponse
import com.valher.resilink.common.condominio.data.model.Condominio
import com.valher.resilink.common.condominio.data.repository.CondominioRepository
import com.valher.resilink.common.condominio.domain.usecase.GetCondominiosUseCase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever


@OptIn(ExperimentalCoroutinesApi::class)
class CondominioViewModelTest{
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: CondominioViewModel
    private val repository = mock<CondominioRepository>()
    private val useCase = GetCondominiosUseCase(repository)
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup(){
        Dispatchers.setMain(testDispatcher)
    }
    @Test
    fun `Deberia actualizar el state flow con la lista de Condominios cuando este exitoso`() = runTest{
        // GIVEN
        val mockCondominios = listOf(
            Condominio(id = "1", nombre = "Condominio 1", version = "1.0", activo = true),
            Condominio(id = "2", nombre = "Condominio 2", version = "1.0", activo = true)
        )
        whenever(useCase.invoke()).thenReturn(NetworkResponse(data = mockCondominios, mensaje = null, estado = true))
        // WHEN
        viewModel = CondominioViewModel(useCase)
        advanceUntilIdle()
        // THEN
        val uiState = viewModel.uiState.first()
        assert(uiState is CondominioUiState.Success)
        assertEquals(mockCondominios, (uiState as CondominioUiState.Success).condominios)

    }
    @Test
    fun `Deberia actualizar el uiState con un mensaje de error cuando la operacion falla`() = runTest {
        // GIVEN
        val errorMessage = "Error de red"
        whenever(useCase.invoke()).thenReturn(NetworkResponse(data = null, mensaje = errorMessage, estado = false))

        // WHEN
        viewModel = CondominioViewModel(useCase)
        advanceUntilIdle()

        // THEN
        val uiState = viewModel.uiState.first()
        assert(uiState is CondominioUiState.Error)
        assertEquals(errorMessage, (uiState as CondominioUiState.Error).errorMessage)
    }
    @Test
    fun `Deberia actualizar el uiState con Empty cuando la lista de Condominios es vacia`() = runTest {
        // GIVEN
        whenever(useCase.invoke()).thenReturn(NetworkResponse(data = emptyList(), mensaje = null, estado = true))

        // WHEN
        viewModel = CondominioViewModel(useCase)
        advanceUntilIdle()

        // THEN
        val uiState = viewModel.uiState.first()
        assert(uiState is CondominioUiState.Empty)
    }


}