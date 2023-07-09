package com.lexx.domain.models

data class PlotInfo (
    val values: List<PlotLineInfo> = listOf(),
    val errorMessage: String = ""
)
