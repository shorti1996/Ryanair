package com.wojciszke.ryanair.di.module.vievmodel

import androidx.lifecycle.ViewModel
import com.wojciszke.ryanair.viewmodel.StationsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class StationsModule {
    @Binds
    @IntoMap
    @ViewModelKey(StationsViewModel::class)
    abstract fun bindViewModel(stationsViewModel: StationsViewModel): ViewModel
}