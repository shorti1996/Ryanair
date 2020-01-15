package com.wojciszke.ryanair.di.module

import dagger.Module

@Module(includes = [NetworkModule::class, StationsModule::class, FlightsModule::class])
class RyanairRepositioryModule