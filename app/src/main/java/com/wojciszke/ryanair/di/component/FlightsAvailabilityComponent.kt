package com.wojciszke.ryanair.di.component

import com.wojciszke.ryanair.di.module.vievmodel.FlightsAvailabilityModule
import com.wojciszke.ryanair.di.module.vievmodel.ViewModelFactoryModule
import com.wojciszke.ryanair.di.scope.FlightsAvailabilityScope
import com.wojciszke.ryanair.view.searchresult.SearchResultsFragment
import dagger.Component

@FlightsAvailabilityScope
@Component(dependencies = [RyanairRepositoryComponent::class], modules = [ViewModelFactoryModule::class, FlightsAvailabilityModule::class])
interface FlightsAvailabilityComponent {
    fun inject(searchResultsFragment: SearchResultsFragment)
}