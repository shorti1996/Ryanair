package com.wojciszke.ryanair

import androidx.lifecycle.Observer
import com.wojciszke.ryanair.viewmodel.CurrentScreen
import com.wojciszke.ryanair.viewmodel.MainViewModel
import com.wojciszke.ryanair.viewmodel.SearchForm
import io.mockk.spyk
import org.junit.Test

import org.junit.Assert.*

class MainViewModelTest : LiveDataTest {

    private val lifeCycleOwner = mockkLifeCycleOwner()

    @Test
    fun `should show search form by default`() {
        // GIVEN
        val mainViewModel = MainViewModel()
        val observer: Observer<CurrentScreen> = spyk(Observer { })
        mainViewModel.apply {
            currentScreen.observe(lifeCycleOwner, observer)
        }

        // THEN
        assertEquals(mainViewModel.currentScreen.value, SearchForm)
    }
}
