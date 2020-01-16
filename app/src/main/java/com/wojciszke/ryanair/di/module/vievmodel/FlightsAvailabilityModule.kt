package com.wojciszke.ryanair.di.module.vievmodel

import androidx.lifecycle.ViewModel
import com.wojciszke.ryanair.viewmodel.SearchResultsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class FlightsAvailabilityModule {
    @Binds
    @IntoMap
    @ViewModelKey(SearchResultsViewModel::class)
    abstract fun bindViewModel(searchResultsViewModel: SearchResultsViewModel): ViewModel
}
