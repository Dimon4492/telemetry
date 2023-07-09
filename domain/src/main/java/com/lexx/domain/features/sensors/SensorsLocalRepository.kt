package com.lexx.domain.features.sensors

import com.lexx.domain.features.sensors.local.SensorLocalInfo
import kotlinx.coroutines.flow.Flow

interface SensorsLocalRepository {
    fun getSensorsInfo(): Flow<List<SensorLocalInfo>>
    fun setSensorInfo(sensorLocalInfo: SensorLocalInfo)
}
