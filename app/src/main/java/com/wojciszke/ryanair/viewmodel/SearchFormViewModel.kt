package com.wojciszke.ryanair.viewmodel

import androidx.core.text.isDigitsOnly
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
    private val adultsCountMutable = MutableLiveData<String>()
    private val teensCountMutable = MutableLiveData<String>()
    private val childrenCountMutable = MutableLiveData<String>()

    val canTriggerSearch: MediatorLiveData<Boolean> = ViewModelExtensions.createMediatorLiveDataForSources(
            originCode,
            destinationCode,
            adultsCountMutable,
            teensCountMutable,
            childrenCountMutable,
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

    fun onAdultsChanged(count: String?) {
        if (isCountStringValid(count)) {
            adultsCountMutable.value = count
        }
    }

    fun onTeensChanged(count: String?) {
        if (isCountStringValid(count)) {
            teensCountMutable.value = count
        }
    }

    fun onChildrenChanged(count: String?) {
        if (isCountStringValid(count)) {
            childrenCountMutable.value = count
        }
    }

    private fun calculateCanTriggerSearch() = originCode.value != null
            && destinationCode.value != null
            && isDateValid()
            && isPassengerCountValid()

    // TODO do it properly (every validation here is a joke) 🙃
    private fun isDateValid() = flightDateMutable.value?.matches(Regex("^\\d{8}\$")) ?: false

    private fun isPassengerCountValid() = adultsCountMutable.value?.toInt()
            ?.plus(teensCountMutable.value?.toInt() ?: 0)
            ?.plus(childrenCountMutable.value?.toInt() ?: 0)?.let { it > 0 } ?: false

    private fun isCountStringValid(count: String?) = count?.isDigitsOnly()
            ?.and(!count.isNullOrEmpty()) ?: false
}