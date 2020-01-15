package com.wojciszke.networking

sealed class NetworkResult
class NetworkFail(val message: String) : NetworkResult()
class NetworkSuccess<T>(val obj: T) : NetworkResult()