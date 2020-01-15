package com.wojciszke.ryanair.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wojciszke.ryanair.model.SearchFormData
import com.wojciszke.ryanair.model.SearchResult

class MainViewModel : ViewModel() {
    private val currentScreenMutable = MutableLiveData<CurrentScreen>().apply {
        value = SearchForm
    }
    val currentScreen: LiveData<CurrentScreen> = currentScreenMutable

    fun onCurrentScreenChanged(newScreen: CurrentScreen) {
        currentScreenMutable.value = newScreen
    }
}

sealed class CurrentScreen
object SearchForm : CurrentScreen()
class SearchResults(val searchFormData: SearchFormData) : CurrentScreen()
class FlightDetails(val searchResult: SearchResult) : CurrentScreen()