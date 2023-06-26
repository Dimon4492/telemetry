package com.lexx.presentation.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.text.DecimalFormat
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UtilModule {

    @Provides
    @Singleton
    fun provideDecimalFormat(): DecimalFormat {
        return DecimalFormat("#.###")
    }
}