package com.wojciszke.ryanair.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wojciszke.ryanair.utils.ViewModelExtensions

class SearchFormViewModel : ViewModel() {
    private val originCodeMutable = MutableLiveData<String>()
    private val originCode: LiveData<String> = originCodeMutable

    private val destinationCodeMutable = MutableLiveData<String>()
    private val destinationCode: LiveData<String> = destinationCodeMutable

    private val flightDateMutable = MutableLiveData<String>()

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

    fun onDateChanged(dateString: String?) {
        flightDateMutable.value = dateString
    }


    private fun calculateCanTriggerSearch() = originCode.value != null && destinationCode.value != null && isDateValid()
    // TODO do it properly 🙃
    private fun isDateValid() = flightDateMutable.value?.matches(Regex("^\\d{8}\$")) ?: false
}