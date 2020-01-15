package com.wojciszke.ryanair.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest


const val NETWORKING_TAG = "networking"

fun Context.registerOnNetworkAvailableCallback(block: (Network) -> Unit): ConnectivityManager.NetworkCallback? =
        object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                block(network)
            }
        }.also { networkCallback ->
            (getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?)?.registerNetworkCallback(
                    NetworkRequest.Builder().build(),
                    networkCallback)
        }

fun Context.unregisterOnNetworkAvailableCallback(networkCallback: ConnectivityManager.NetworkCallback) =
        (getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?)?.unregisterNetworkCallback(networkCallback)