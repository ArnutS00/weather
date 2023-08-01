package com.example.weatherforecast.di

import com.example.weatherforecast.base.provider.CoroutineDispatchersProvider
import com.example.weatherforecast.base.provider.CoroutineDispatchersProviderImpl
import dagger.Binds
import dagger.Module
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface BaseModule {

    @Reusable
    @Binds
    fun bindCoroutineDispatchersProvider(
        coroutineDispatchersProviderImpl: CoroutineDispatchersProviderImpl
    ) : CoroutineDispatchersProvider
}