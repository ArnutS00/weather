package com.example.weatherforecast.base.provider

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject


interface CoroutineDispatchersProvider{
    fun io(): CoroutineDispatcher = Dispatchers.IO

    fun default() : CoroutineDispatcher = Dispatchers.Default

    fun main() : CoroutineDispatcher = Dispatchers.Main

    fun unconfined() : CoroutineDispatcher = Dispatchers.Unconfined

}

class CoroutineDispatchersProviderImpl @Inject constructor() :CoroutineDispatchersProvider