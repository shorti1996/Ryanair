package com.wojciszke.ryanair.utils

import android.app.Activity
import com.wojciszke.ryanair.R

/**
 * Sets Action bar title to the title specified or to [R.string.app_name] is it's null.
 */
fun Activity.setActionBarTitleOrDefault(title: String?) {
    if (title == null) setTitle(getString(R.string.app_name))
    else setTitle(title)
}