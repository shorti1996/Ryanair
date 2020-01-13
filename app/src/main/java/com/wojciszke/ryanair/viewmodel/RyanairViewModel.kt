package com.wojciszke.ryanair.viewmodel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wojciszke.ryanair.data.RyanairRepository
import com.wojciszke.ryanair.data.model.Stations
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class RyanairViewModel(val ryanairRepository: RyanairRepository) : ViewModel() {

    private val viewModelJob = SupervisorJob()
    private val ioScope = CoroutineScope(Dispatchers.IO + viewModelJob)

    val stations = MediatorLiveData<Stations>().apply {
        ioScope.launch {
            postValue(ryanairRepository.getStations())
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}

@Suppress("UNCHECKED_CAST")
class RyanairViewModelFactory(private val ryanairRepository: RyanairRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RyanairViewModel::class.java)) {
            return RyanairViewModel(ryanairRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}