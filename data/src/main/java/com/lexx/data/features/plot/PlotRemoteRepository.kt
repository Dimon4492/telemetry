package com.lexx.data.features.plot

import com.lexx.data.api.telemetry.models.SensorDataDto
import com.lexx.data.mappers.WebServiceDataMapper
import com.lexx.domain.features.plot.PlotRepository
import com.lexx.domain.models.PlotInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PlotRemoteRepository @Inject constructor(
    private val plotInfoRemoteDataSource: PlotInfoRemoteDataSource,
    private val mapper: WebServiceDataMapper,
) : PlotRepository {
    override fun pauseNetworkPolling(pause: Boolean) {
        plotInfoRemoteDataSource.pauseNetworkPolling(pause)
    }

    override fun pauseHourPolling(pause: Boolean) {
        plotInfoRemoteDataSource.pauseHourPolling(pause)
    }

    override fun pauseSixHoursPolling(pause: Boolean) {
        plotInfoRemoteDataSource.pauseSixHoursPolling(pause)
    }

    override fun pauseDayPolling(pause: Boolean) {
        plotInfoRemoteDataSource.pauseDayPolling(pause)
    }

    override suspend fun getHourPlotInfo(): Flow<Result<PlotInfo>> {
        return plotInfoRemoteDataSource.hourSensorsData
    }
    override suspend fun getSixHoursPlotInfo(): Flow<Result<PlotInfo>> {
        return plotInfoRemoteDataSource.sixHoursSensorsData
    }
    override suspend fun getDayPlotInfo(): Flow<Result<PlotInfo>> {
        return plotInfoRemoteDataSource.daySensorsData
    }
}

interface PlotInfoRemoteDataSource {
    fun pauseNetworkPolling(pause: Boolean)
    fun pauseHourPolling(pause: Boolean)
    fun pauseSixHoursPolling(pause: Boolean)
    fun pauseDayPolling(pause: Boolean)

    val hourSensorsData: Flow<Result<PlotInfo>>
    val sixHoursSensorsData: Flow<Result<PlotInfo>>
    val daySensorsData: Flow<Result<PlotInfo>>
}
