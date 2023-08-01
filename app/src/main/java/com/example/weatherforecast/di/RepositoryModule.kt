package com.example.weatherforecast.di

import com.example.weatherforecast.data.repository.WeatherRepository
import com.example.weatherforecast.data.repository.WeatherRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun provideWeatherRepository(weatherRepositoryImpl: WeatherRepositoryImpl) :WeatherRepository
}