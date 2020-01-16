package com.wojciszke.ryanair.di.component

import com.wojciszke.ryanair.di.module.vievmodel.StationsModule
import com.wojciszke.ryanair.di.module.vievmodel.ViewModelFactoryModule
import com.wojciszke.ryanair.di.scope.StationsScope
import com.wojciszke.ryanair.view.searchform.SearchFormFragment
import dagger.Component

@StationsScope
@Component(dependencies = [RyanairRepositoryComponent::class], modules = [ViewModelFactoryModule::class, StationsModule::class])
interface StationsComponent {
    fun inject(searchFormFragment: SearchFormFragment)
}