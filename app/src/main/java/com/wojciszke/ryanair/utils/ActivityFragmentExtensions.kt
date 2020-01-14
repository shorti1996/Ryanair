package com.wojciszke.ryanair.utils

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

inline fun FragmentManager.inTransaction(block: FragmentTransaction.() -> Unit) =
        with(beginTransaction()) {
            block()
            commit()
        }
