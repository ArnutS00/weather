package com.example.weatherforecast.di

import com.example.weatherforecast.BuildConfig
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    companion object{
        const val API_KEY = "API_KEY"
        const val WEATHER_BASE_URL = "WEATHER_BASE_URL"
    }

    @Provides
    @Named(API_KEY)
    fun provideApiKey() = BuildConfig.ApiKey

    @Provides
    @Named(WEATHER_BASE_URL)
    fun provideBaseUrl() = BuildConfig.WeatherBaseURL

    @Singleton
    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder().build()


}