package com.lexx.domain.features.plot

import com.lexx.domain.models.PlotInfo
import kotlinx.coroutines.flow.Flow

interface PlotRepository {
    fun pauseNetworkPolling(pause: Boolean)
    fun pauseHourPolling(pause: Boolean)
    fun pauseSixHoursPolling(pause: Boolean)
    fun pauseDayPolling(pause: Boolean)

    suspend fun getHourPlotInfo(): Flow<Result<PlotInfo>>
    suspend fun getSixHoursPlotInfo(): Flow<Result<PlotInfo>>
    suspend fun getDayPlotInfo(): Flow<Result<PlotInfo>>
}
