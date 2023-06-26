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
    override fun pauseNetworkPolling() {
        plotInfoRemoteDataSource.pauseNetworkPolling()
    }

    override fun resumeNetworkPolling() {
        plotInfoRemoteDataSource.resumeNetworkPolling()
    }

    override suspend fun getPlotInfo(): Flow<Result<PlotInfo>> {
        return plotInfoRemoteDataSource.
            sensorsData.map{
            it.map{
                mapper.mapPlotInfo(it)
            }
        }
    }
}

interface PlotInfoRemoteDataSource {
    fun pauseNetworkPolling()
    fun resumeNetworkPolling()

    val sensorsData: Flow<Result<List<SensorDataDto>>>
}
