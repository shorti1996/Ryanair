package com.wojciszke.ryanair.di.component

import com.wojciszke.ryanair.MainActivity
import com.wojciszke.ryanair.data.StationsRepository
import com.wojciszke.ryanair.di.module.RyanairRepositioryModule
import dagger.Component
import javax.inject.Singleton

@Component(modules = [RyanairRepositioryModule::class])
@Singleton
interface SearchFlightsComponent {
    fun inject(mainActivity: MainActivity)

    val stationsRepository: StationsRepository
}