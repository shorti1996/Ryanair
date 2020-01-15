package com.wojciszke.ryanair.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wojciszke.core.model.stations.Stations
import com.wojciszke.networking.NetworkFail
import com.wojciszke.networking.NetworkSuccess
import com.wojciszke.ryanair.repository.StationsRepository
import com.wojciszke.ryanair.utils.NETWORKING_LOG_TAG
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
        fetchStations()
    }

    fun fetchStations() {
        ioScope.launch {
            when (val result = stationsRepository.getStations()) {
                is NetworkSuccess<*> -> stationsMutable.postValue(result.obj as Stations?)
                is NetworkFail -> Log.e(NETWORKING_LOG_TAG, result.message)
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