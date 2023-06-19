package com.lexx.data.features.plot.remote

import com.lexx.data.api.telemetry.TelemetryApiService
import com.lexx.data.api.telemetry.models.SensorDataDto
import com.lexx.data.features.plot.PlotInfoRemoteDataSource
import com.lexx.domain.features.settings.SettingsRepository
import javax.inject.Inject

class WebServicePlotInfoRemoteDataSource @Inject constructor(
    private val telemetryApiService: TelemetryApiService,
    private val settingsRepository: SettingsRepository,
) : PlotInfoRemoteDataSource {
    override suspend fun getSensorsData(): List<SensorDataDto> {
        val serverAddress: String = settingsRepository.getServerAddress()
        return telemetryApiService.getSensorsData("http://$serverAddress/data")
    }
}
