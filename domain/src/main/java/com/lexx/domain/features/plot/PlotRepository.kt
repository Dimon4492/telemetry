package com.lexx.domain.features.plot

import com.lexx.domain.models.PlotInfo

interface PlotRepository {
    suspend fun getPlotInfo(): PlotInfo
}
