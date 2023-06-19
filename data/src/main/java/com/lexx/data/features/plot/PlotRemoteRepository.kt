package com.lexx.data.features.plot

import com.lexx.data.api.telemetry.TelemetryApiService
import com.lexx.data.api.telemetry.models.SensorDataDto
import com.lexx.data.mappers.WebServiceDataMapper
import com.lexx.domain.features.plot.PlotRepository
import com.lexx.domain.features.settings.SettingsRepository
import com.lexx.domain.models.PlotInfo
import java.net.UnknownHostException
import javax.inject.Inject

class PlotRemoteRepository @Inject constructor(
    private val plotInfoRemoteDataSource: PlotInfoRemoteDataSource,
    private val mapper: WebServiceDataMapper,
) : PlotRepository {
    override suspend fun getPlotInfo(): PlotInfo {
        try {
            return mapper.mapPlotInfo(plotInfoRemoteDataSource.getSensorsData())
        } catch (e: UnknownHostException) {
            return PlotInfo(errorMessage = e.localizedMessage ?: "")
        }
    }
}

interface PlotInfoRemoteDataSource {
    suspend fun getSensorsData(): List<SensorDataDto>
}
