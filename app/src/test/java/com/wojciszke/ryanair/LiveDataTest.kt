package com.wojciszke.ryanair

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import io.mockk.every
import io.mockk.mockk
import org.junit.Rule

/**
 * Some people don't like such things in tests but it's super convenient if there are multiple tests
 * of [LiveData]
 */
interface LiveDataTest {
    @get: Rule
    val liveDataRule
        get() = InstantTaskExecutorRule()

    fun mockkLifeCycleOwner() = mockk<LifecycleOwner>().apply {
        every { lifecycle } returns LifecycleRegistry(this).also {
            it.handleLifecycleEvent(Lifecycle.Event.ON_START)
        }
    }
}