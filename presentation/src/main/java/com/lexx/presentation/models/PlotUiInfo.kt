package com.lexx.presentation.models


data class PlotUiInfo (
    val values: List<PlotLineUiInfo> = listOf(),
    val minValue: Float = Float.MAX_VALUE,
    val maxValue: Float = Float.MIN_VALUE,
    val minTimestamp: Long = Long.MAX_VALUE,
    val maxTimestamp: Long = Long.MIN_VALUE,
    val noDataError: Boolean = true,
    val connectionError: Boolean = false,
    val errorMessage: String = "",
)
