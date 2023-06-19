package com.lexx.data.di

import com.lexx.data.BuildConfig
import com.lexx.data.api.telemetry.TelemetryApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TelemetryApiModule {

    @Provides
    fun providesBaseUrl() : String = BuildConfig.DEFAULT_BASE_URL

    @Provides
    @Singleton
    fun provideRetrofit(BASE_URL : String) : Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()

    @Provides
    @Singleton
    fun provideTelemetryApiService(retrofit: Retrofit) : TelemetryApiService = retrofit.create(TelemetryApiService::class.java)
}
