package com.lexx.domain.features.sensors

import com.lexx.domain.models.SensorInfo
import kotlinx.coroutines.flow.Flow

interface SensorsRepository {
    fun getSensorsInfo(): Flow<Result<List<SensorInfo>>>
}
