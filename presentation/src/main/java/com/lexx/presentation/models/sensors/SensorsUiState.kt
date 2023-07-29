package com.lexx.presentation.models.sensors

data class SensorsUiState(
    val sensors: List<SensorUiInfo> = listOf(),
    val connectionError: Boolean = false,
    val noSensorsError: Boolean = true,
    val selectedSensorInfo: SensorUiInfo = SensorUiInfo(),
    val showEditor: Boolean = false,
    val errorMessage: String = "",
)
