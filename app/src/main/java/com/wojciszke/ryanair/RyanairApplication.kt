package com.wojciszke.ryanair

import android.app.Application
import com.wojciszke.ryanair.di.component.DaggerRyanairRepositoryComponent
import com.wojciszke.ryanair.di.component.RyanairRepositoryComponent

class RyanairApplication : Application() {
    val ryanairRepositoryComponent: RyanairRepositoryComponent by lazy { DaggerRyanairRepositoryComponent.create() }

    override fun onCreate() {
        super.onCreate()

        ryanairRepositoryComponent.inject(this)
    }
}