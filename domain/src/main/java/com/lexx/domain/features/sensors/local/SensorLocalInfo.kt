package com.lexx.domain.features.sensors.local

data class SensorLocalInfo(
    val remoteSensorId: Int,
    val color: String,
    val description: String,
    val enabled: Boolean,
    val multiplier: Float
)
