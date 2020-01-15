package com.wojciszke.ryanair.viewmodel

import androidx.lifecycle.*
import com.wojciszke.ryanair.data.FlightsRepository
import com.wojciszke.ryanair.data.model.app.fromFlights
import com.wojciszke.ryanair.data.model.flights.FlightsAvailability
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class SearchResultsViewModel(private val flightsRepository: FlightsRepository) : ViewModel() {
    // could be replaced with viewModelScope from androidx.lifecycle:*:2.2.0-alpha01
    // so I'm not extracting it to some common place
    private val viewModelJob = SupervisorJob()
    private val ioScope = CoroutineScope(Dispatchers.IO + viewModelJob)

    private val availabilityMutable: MutableLiveData<FlightsAvailability?> by lazy {
        MutableLiveData<FlightsAvailability?>().apply {
            ioScope.launch {
                postValue(flightsRepository.getFlights(
                        "SXF",
                        "LIS",
                        "2020-02-20",
                        1,
                        0,
                        0))
            }
        }
    }
    val availability: LiveData<FlightsAvailability?> = availabilityMutable

    val searchResults = Transformations.map(availability) {
        it?.let { fromFlights(it) }
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