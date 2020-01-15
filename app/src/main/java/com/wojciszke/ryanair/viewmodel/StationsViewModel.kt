package com.wojciszke.ryanair.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.wojciszke.ryanair.networking.StationsRepository
import com.wojciszke.ryanair.data.model.stations.Stations
import com.wojciszke.ryanair.utils.NETWORKING_TAG
import com.wojciszke.ryanair.utils.NetworkFail
import com.wojciszke.ryanair.utils.NetworkSuccess
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class StationsViewModel(private val stationsRepository: StationsRepository) : ViewModel() {

    // could be replaced with viewModelScope from androidx.lifecycle:*:2.2.0-alpha01
    // so I'm not extracting it to some common place
    private val viewModelJob = SupervisorJob()
    private val ioScope = CoroutineScope(Dispatchers.IO + viewModelJob)

    private val stationsMutable: MutableLiveData<Stations?> = MutableLiveData<Stations?>().apply {
        reloadStations()
    }

    fun reloadStations() {
        ioScope.launch {
            when (val result = stationsRepository.getStations()) {
                is NetworkSuccess<*> -> stationsMutable.postValue(result.obj as Stations?)
                is NetworkFail -> Log.e(NETWORKING_TAG, result.message)
            }
        }
    }

    val stations: LiveData<Stations?> = stationsMutable

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}

@Suppress("UNCHECKED_CAST")
class StationsViewModelFactory(private val stationsRepository: StationsRepository) :
        ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StationsViewModel::class.java)) {
            return StationsViewModel(stationsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}