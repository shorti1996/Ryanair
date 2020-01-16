package com.wojciszke.ryanair.viewmodel

import androidx.lifecycle.*
import com.wojciszke.core.model.flights.FlightsAvailability
import com.wojciszke.ryanair.model.SearchFormData
import com.wojciszke.ryanair.model.SearchResult
import com.wojciszke.ryanair.model.fromAvailability
import com.wojciszke.ryanair.repository.FlightsRepository
import com.wojciszke.ryanair.utils.ViewModelExtensions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchResultsViewModel @Inject constructor(private val flightsRepository: FlightsRepository) : ViewModel() {
    // could be replaced with viewModelScope from androidx.lifecycle:*:2.2.0-alpha01
    // so I'm not extracting it to some common place
    private val viewModelJob = SupervisorJob()
    private val ioScope = CoroutineScope(Dispatchers.IO + viewModelJob)

    private val availabilityMutable = MutableLiveData<FlightsAvailability?>()

    private val focusedFlightMutable = MutableLiveData<SearchResult>()

    val originName = Transformations.map(focusedFlightMutable) {
        it?.originName
    }

    val destinationName = Transformations.map(focusedFlightMutable) {
        it?.destinationName
    }

    private val maxPriceMutable = MutableLiveData<Int>()

    val searchResults = ViewModelExtensions.createMediatorLiveDataForSources(
            availabilityMutable,
            maxPriceMutable,
            calculate = ::calculateSearchResultsToShow
    )

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

    fun onFocusedFlightChanged(searchResult: SearchResult?) {
        focusedFlightMutable.value = searchResult
    }

    fun onMaxPriceChanged(newPrice: Int) {
        maxPriceMutable.value = newPrice
    }

    private fun calculateSearchResultsToShow(): List<SearchResult> =
            availabilityMutable.value?.let { flightsAvailability ->
                fromAvailability(flightsAvailability).filter { searchResult ->
                    searchResult.regularFarePrice <= maxPriceMutable.value ?: Int.MAX_VALUE // TODO yeah, it's ugly but it's 23:54
                }
            } ?: listOf()

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