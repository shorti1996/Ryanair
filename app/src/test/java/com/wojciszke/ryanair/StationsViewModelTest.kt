package com.wojciszke.ryanair

import androidx.lifecycle.Observer
import com.wojciszke.core.model.stations.Stations
import com.wojciszke.networking.NetworkSuccess
import com.wojciszke.ryanair.repository.StationsRepository
import com.wojciszke.ryanair.viewmodel.StationsViewModel
import io.mockk.*
import org.junit.Test

class StationsViewModelTest : LiveDataTest {
    private val lifeCycleOwner = mockkLifeCycleOwner()

    private val stationsRepository = spyk(StationsRepository(mockk())).apply {
        coEvery { getStations() } returns NetworkSuccess(Stations())
    }

    @Test
    fun `should fetch stations when awake`() {
        // GIVEN
        val stationsViewModel = StationsViewModel(stationsRepository)
        val observer: Observer<Stations?> = spyk(Observer { })
        stationsViewModel.apply {
            stations.observe(lifeCycleOwner, observer)
        }

        // THEN
        verify(exactly = 1) { observer.onChanged(any()) }
    }
}
