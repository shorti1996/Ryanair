package com.wojciszke.ryanair.di.module

import dagger.Module

@Module(includes = [NetworkModule::class, StationsApiModule::class, FlightsApiModule::class])
class RyanairRepositioryModule