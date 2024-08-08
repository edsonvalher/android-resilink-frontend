package com.valher.resilink.common.sector.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.valher.core.model.NetworkResponse
import com.valher.resilink.common.sector.data.model.Sector
import com.valher.resilink.common.sector.data.repository.SectorRepository
import com.valher.resilink.common.sector.domain.usecase.GetSectoresUseCase
import com.valher.resilink.shared.SharedDataCondominioSector
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
class SectorViewModelTest{
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: SectorViewModel
    private val repository = mock<SectorRepository>()
    private val useCase = GetSectoresUseCase(repository)
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup(){
        Dispatchers.setMain(testDispatcher)
        SharedDataCondominioSector.selectCondominio("1")
    }

    @Test
    fun `Deberia actualizar el state flow con la lista de sectores cuando este exitoso`() = runTest{
        // GIVEN
        val mockCondominioId = "1"
        val mockSectores = listOf(
            Sector(id = "1", nombre = "Sector 1", activo = true),
            Sector(id = "2", nombre = "Sector 2", activo = true)
        )
        whenever(useCase.invoke(mockCondominioId)).thenReturn(NetworkResponse(data = mockSectores, mensaje = null, estado = true))
        // WHEN
        viewModel = SectorViewModel(useCase)
        advanceUntilIdle()
        // THEN
        assertEquals(mockSectores, viewModel.sectores.first())
        assertEquals(null, viewModel.errorMessage.first())

    }

}