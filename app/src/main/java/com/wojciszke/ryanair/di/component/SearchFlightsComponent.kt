package com.wojciszke.ryanair.di.component

import com.wojciszke.ryanair.SearchFormFragment
import com.wojciszke.ryanair.SearchResultsFragment
import com.wojciszke.ryanair.data.StationsRepository
import com.wojciszke.ryanair.di.module.RyanairRepositioryModule
import dagger.Component
import javax.inject.Singleton

@Component(modules = [RyanairRepositioryModule::class])
@Singleton
interface SearchFlightsComponent {
    fun inject(searchFormFragment: SearchFormFragment)
    fun inject(searchResultsFragment: SearchResultsFragment)

    val stationsRepository: StationsRepository
}