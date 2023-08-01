package com.example.weatherforecast.di

import com.example.weatherforecast.BuildConfig
import com.example.weatherforecast.data.WeatherService
import com.example.weatherforecast.di.AppModule.Companion.WEATHER_BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {


    companion object{
        const val OK_HTTP = "OK_HTTP"
        const val JSON_HEADER = "JSON_HEADER"
        const val CONTENT_TYPE = "Content-Type"
        const val APPLICATION_JSON = "application/json"

        const val WEATHER_RETROFIT = "WEATHER_RETROFIT"

    }

    @Singleton
    @Provides
    fun provideHTTPLoggingInterceptor() = HttpLoggingInterceptor().apply {
        setLevel(
            when {
                BuildConfig.DEBUG -> HttpLoggingInterceptor.Level.BODY
                else -> HttpLoggingInterceptor.Level.NONE
            }
        )
    }

    @Singleton
    @Provides
    fun provideRxJava3CallAdapterFactory(): RxJava3CallAdapterFactory =
        RxJava3CallAdapterFactory.create()

    @Named(JSON_HEADER)
    @Singleton
    @Provides
    fun provideJsonHeaderInterceptor(): Interceptor = Interceptor { chain ->
        chain.proceed(
            chain.request().newBuilder()
                .addHeader(CONTENT_TYPE, APPLICATION_JSON)
                .build()
        )
    }

    @Named(OK_HTTP)
    @Singleton
    @Provides
    fun provideHttpClient(
        @Named(JSON_HEADER) headerInterceptor: Interceptor,
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            .addInterceptor(headerInterceptor)
            .addInterceptor(loggingInterceptor)
            .connectTimeout(45, TimeUnit.SECONDS) //Backend is really slow
            .writeTimeout(45, TimeUnit.SECONDS)
            .readTimeout(45, TimeUnit.SECONDS)
        return builder.build()
    }


    @Named(WEATHER_RETROFIT)
    @Singleton
    @Provides
    fun provideWeatherRetrofit(
        @Named(WEATHER_BASE_URL) endpoint: String,
        @Named(OK_HTTP) okHttpClient: OkHttpClient,
        rxJava3CallAdapterFactory: RxJava3CallAdapterFactory
    ): Retrofit = Retrofit.Builder()
        .addCallAdapterFactory(rxJava3CallAdapterFactory)
        .addConverterFactory(MoshiConverterFactory.create())
        .client(okHttpClient)
        .baseUrl(endpoint)
        .build()

    @Singleton
    @Provides
    fun provideWeatherService(@Named(WEATHER_RETROFIT) retrofit: Retrofit): WeatherService =
        retrofit.create(WeatherService::class.java)
}