package com.lexx.presentation.models

import com.lexx.domain.models.PlotInfo

data class PlotUiState (
    val plotInfo: PlotInfo = PlotInfo(),
    val xAxisLabels: List<String> = listOf(),
    val yAxisLabels: List<String> = listOf(),
)
