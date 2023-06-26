package com.lexx.domain.features.plot

import com.lexx.domain.models.PlotInfo
import kotlinx.coroutines.flow.Flow

interface PlotRepository {
    fun pauseNetworkPolling()
    fun resumeNetworkPolling()

    suspend fun getPlotInfo(): Flow<Result<PlotInfo>>
}
