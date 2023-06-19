package com.lexx.domain.features.sensors

import com.lexx.domain.models.SensorInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetSensorsInfoUseCase @Inject constructor(
    private val repository: SensorsRepository,
) {
    operator fun invoke() : Flow<Result<List<SensorInfo>>> = repository.getSensorsInfo()
}
