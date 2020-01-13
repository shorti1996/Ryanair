package com.wojciszke.ryanair.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

fun <T> LiveData<T>.observe(owner: LifecycleOwner, observer: (T) -> Unit) = observe(owner, Observer(observer))

fun <T> MutableLiveData<T>.setValueIfDiffers(newValue: T?, onSet: ((newValue: T?) -> Unit)? = null, onNotSet: (() -> Unit)? = null) {
    if (value != newValue) {
        value = newValue
        onSet?.invoke(newValue)
    } else {
        onNotSet?.invoke()
    }
}

fun <T> MediatorLiveData<T>.addManySources(vararg sources: LiveData<*>, calculate: () -> T?, setValueOnlyIfDiffers: Boolean = false) {
    sources.forEach {
        addSource(it) {
            val newValue = calculate()
            if (setValueOnlyIfDiffers) {
                setValueIfDiffers(newValue)
            } else {
                value = newValue
            }
        }
    }
}

object ViewModelExtensions {
    fun <T> createMediatorLiveDataForSources(
            vararg sources: LiveData<*>,
            initialValue: T? = null,
            calculate: () -> T?,
            setValueOnlyIfDiffers: Boolean = false
    ) = MediatorLiveData<T>().apply {
        addManySources(sources = *sources, calculate = calculate, setValueOnlyIfDiffers = setValueOnlyIfDiffers)
        if (initialValue != null) value = initialValue
    }
}