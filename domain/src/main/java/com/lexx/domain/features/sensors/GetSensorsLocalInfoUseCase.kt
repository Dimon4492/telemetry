package com.lexx.domain.features.sensors

import com.lexx.domain.features.sensors.local.SensorLocalInfo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSensorsLocalInfoUseCase @Inject constructor(
    private val localRepository: SensorsLocalRepository,
) {
    operator fun invoke() : Flow<List<SensorLocalInfo>> = localRepository.getSensorsInfo()
}
