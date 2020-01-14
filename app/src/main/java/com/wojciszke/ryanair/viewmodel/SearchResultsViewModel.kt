package com.wojciszke.ryanair.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wojciszke.ryanair.data.FlightsRepository
import com.wojciszke.ryanair.data.model.flights.Flights
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class SearchResultsViewModel(private val flightsRepository: FlightsRepository) : ViewModel() {
    // could be replaced with viewModelScope from androidx.lifecycle:*:2.2.0-alpha01
    // so I'm not extracting it to some common place
    private val viewModelJob = SupervisorJob()
    private val ioScope = CoroutineScope(Dispatchers.IO + viewModelJob)

    private val flightSearchResultMutable: MutableLiveData<Flights?> by lazy {
        MutableLiveData<Flights?>().apply {
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
    val stations: LiveData<Flights?> = flightSearchResultMutable

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