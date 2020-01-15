package com.wojciszke.ryanair.di.component

import com.wojciszke.ryanair.view.searchform.SearchFormFragment
import com.wojciszke.ryanair.view.searchresult.SearchResultsFragment
import com.wojciszke.ryanair.repository.StationsRepository
import com.wojciszke.ryanair.di.module.RyanairRepositioryModule
import com.wojciszke.ryanair.view.flightdetails.FlightDetailsFragment
import dagger.Component
import javax.inject.Singleton

@Component(modules = [RyanairRepositioryModule::class])
@Singleton
interface SearchFlightsComponent {
    fun inject(searchFormFragment: SearchFormFragment)
    fun inject(searchResultsFragment: SearchResultsFragment)

    val stationsRepository: StationsRepository
}