package com.lexx.data.di

import com.lexx.data.features.plot.PlotInfoRemoteDataSource
import com.lexx.data.features.plot.PlotRemoteRepository
import com.lexx.data.features.plot.remote.WebServicePlotInfoRemoteDataSource
import com.lexx.data.features.sensors.SensorsInfoRemoteDataSource
import com.lexx.data.features.sensors.SensorsInfoRepository
import com.lexx.data.features.sensors.remote.WebserviceSensorsInfoRemoteDataSource
import com.lexx.data.features.settings.DataStoreSettingsRepository
import com.lexx.domain.features.plot.PlotRepository
import com.lexx.domain.features.sensors.SensorsRepository
import com.lexx.domain.features.settings.SettingsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Singleton
    @Binds
    abstract fun bindUserPreferencesRepository(dataStoreUserPreferencesRepository: DataStoreSettingsRepository): SettingsRepository

    @Singleton
    @Binds
    abstract fun bindRemoteSensorsRepository(sensorsInfoRepository: SensorsInfoRepository): SensorsRepository

    @Singleton
    @Binds
    abstract fun bindRemoteSensorsDataSource(webserviceSensorsRemoteDataSource: WebserviceSensorsInfoRemoteDataSource): SensorsInfoRemoteDataSource

    @Singleton
    @Binds
    abstract fun PlotRemoteRepository(plotRemoteRepository: PlotRemoteRepository): PlotRepository

    @Singleton
    @Binds
    abstract fun WebServicePlotInfoRemoteDataSource(webServicePlotInfoRemoteDataSource: WebServicePlotInfoRemoteDataSource): PlotInfoRemoteDataSource
}
