package com.wojciszke.ryanair.viewmodel

import androidx.lifecycle.*
import com.wojciszke.ryanair.data.StationsRepository
import com.wojciszke.ryanair.data.model.api.Stations
import com.wojciszke.ryanair.utils.ViewModelExtensions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class FlightSearchViewModel(private val stationsRepository: StationsRepository) : ViewModel() {

    private val viewModelJob = SupervisorJob()
    private val ioScope = CoroutineScope(Dispatchers.IO + viewModelJob)

    private val stationsMutable: MutableLiveData<Stations?> = MutableLiveData<Stations?>().apply {
        ioScope.launch {
            postValue(stationsRepository.getStations())
        }
    }
    val stations: LiveData<Stations?> = stationsMutable

    private val originCodeMutable = MutableLiveData<String>()
    private val originCode: LiveData<String> = originCodeMutable

    private val destinationCodeMutable = MutableLiveData<String>()
    private val destinationCode: LiveData<String> = destinationCodeMutable

    val canTriggerSearch: MediatorLiveData<Boolean> = ViewModelExtensions.createMediatorLiveDataForSources(
            originCode,
            destinationCode,
            initialValue = false,
            calculate = ::calculateCanTriggerSearch
    )

    fun onOriginSelected(stationCode: String) {
        originCodeMutable.value = stationCode
    }

    fun onDestinationSelected(stationCode: String) {
        destinationCodeMutable.value = stationCode
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private fun calculateCanTriggerSearch() = originCode.value != null && destinationCode.value != null
}

@Suppress("UNCHECKED_CAST")
class FlightSearchViewModelFactory(private val stationsRepository: StationsRepository) :
        ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FlightSearchViewModel::class.java)) {
            return FlightSearchViewModel(stationsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}