package com.lexx.domain.features.sensors

import com.lexx.domain.features.sensors.local.SensorLocalInfo
import javax.inject.Inject

class SetSensorLocalInfoUseCase @Inject constructor(
    private val localRepository: SensorsLocalRepository,
) {
    operator fun invoke(sensorLocalInfo: SensorLocalInfo) = localRepository.setSensorInfo(sensorLocalInfo)
}
