package com.wojciszke.ryanair.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    private val currentScreenMutable = MutableLiveData<CurrentScreen>().apply {
        value = CurrentScreen.SEARCH_FORM
    }
    val currentScreen: LiveData<CurrentScreen> = currentScreenMutable

    fun onCurrentScreenChanged(newScreen: CurrentScreen){
        currentScreenMutable.value = newScreen
    }
}

enum class CurrentScreen {
    SEARCH_FORM,
    SEARCH_RESULTS
}