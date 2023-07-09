package com.lexx.data.mappers

import com.lexx.data.db.room.models.LocalSensorDataDto
import com.lexx.domain.features.sensors.local.SensorLocalInfo
import timber.log.Timber
import javax.inject.Inject

class RoomDataMapper @Inject constructor() {
    fun mapSensorsData(data: List<LocalSensorDataDto>): List<SensorLocalInfo> {
        return data.map {
            mapSensorData(it)
        }
    }

    private fun mapSensorData(data: LocalSensorDataDto): SensorLocalInfo {
        return with(data) {
            Timber.d("zzz room data mpper, sensor data dta $remoteSensorId, $color, $description, $enabled, $multiplier")
            SensorLocalInfo(
                remoteSensorId = remoteSensorId,
                color = color,
                description = description,
                enabled = enabled,
                multiplier = multiplier
            )
        }
    }
}
