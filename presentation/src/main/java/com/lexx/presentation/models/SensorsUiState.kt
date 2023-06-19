package com.lexx.presentation.models

import com.lexx.domain.models.SensorInfo

data class SensorsUiState(
    val sensors: List<SensorInfo> = listOf(),
    val connectionError: Boolean = false
)
