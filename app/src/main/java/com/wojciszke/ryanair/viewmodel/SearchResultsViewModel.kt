package com.wojciszke.ryanair.viewmodel

import androidx.lifecycle.*
import com.wojciszke.ryanair.networking.FlightsRepository
import com.wojciszke.ryanair.model.SearchFormData
import com.wojciszke.ryanair.model.SearchResult
import com.wojciszke.ryanair.model.fromFlights
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class SearchResultsViewModel(private val flightsRepository: FlightsRepository) : ViewModel() {
    // could be replaced with viewModelScope from androidx.lifecycle:*:2.2.0-alpha01
    // so I'm not extracting it to some common place
    private val viewModelJob = SupervisorJob()
    private val ioScope = CoroutineScope(Dispatchers.IO + viewModelJob)

    private val availabilityMutable = MutableLiveData<com.wojciszke.core.model.flights.FlightsAvailability?>()

    val searchResults = Transformations.map(availabilityMutable) {
        it?.let { fromFlights(it) }
    }

    private val focusedFlightMutable = MutableLiveData<SearchResult>()

    fun onSearchFormChanged(searchFormData: SearchFormData?) {
        if (searchFormData != null) {
            ioScope.launch {
                availabilityMutable.postValue(flightsRepository.getFlights(
                        searchFormData.origin,
                        searchFormData.destination,
                        searchFormData.dateOut,
                        searchFormData.adults,
                        searchFormData.teens,
                        searchFormData.children))
            }
        } else {
            availabilityMutable.value = null
        }
    }

    fun onFocusedFlightChanged(searchResult: SearchResult) {
        focusedFlightMutable.value = searchResult
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}

@Suppress("UNCHECKED_CAST")
class SearchResultsViewModelFactory(private val flightsRepository: FlightsRepository) :
        ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchResultsViewModel::class.java)) {
            return SearchResultsViewModel(flightsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}