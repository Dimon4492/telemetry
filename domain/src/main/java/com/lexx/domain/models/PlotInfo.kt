package com.lexx.domain.models

import java.time.LocalDateTime

data class PlotInfo (
    val values: List<PlotLineInfo> = listOf(),
    val minValue: Float = Float.MAX_VALUE,
    val maxValue: Float = Float.MIN_VALUE,
    val minTimestamp: Long = Long.MAX_VALUE,
    val maxTimestamp: Long = Long.MIN_VALUE,
    val errorMessage: String = ""
)
