package com.lexx.data.features.sensors

import com.lexx.data.api.telemetry.models.SensorInfoDto
import com.lexx.data.mappers.WebServiceDataMapper
import com.lexx.domain.features.sensors.SensorsRepository
import com.lexx.domain.models.SensorInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SensorsInfoRepository @Inject constructor(
    private val sensorsInfoRemoteDataSource: SensorsInfoRemoteDataSource,
    private val mapper: WebServiceDataMapper,
) : SensorsRepository {
    override fun getSensorsInfo(): Flow<Result<List<SensorInfo>>> {
        return sensorsInfoRemoteDataSource.sensorsInfo.map {
            it.map {
                mapper.mapSensors(it)
            }
        }
    }

    override fun pauseNetworkPolling(pause: Boolean) {
        sensorsInfoRemoteDataSource.pauseNetworkPolling(pause)
    }

    override fun pauseSensorPolling(pause: Boolean) {
        sensorsInfoRemoteDataSource.pauseSensorPolling(pause)
    }
}

interface SensorsInfoRemoteDataSource {
    fun pauseNetworkPolling(pause: Boolean)
    fun pauseSensorPolling(pause: Boolean)

    val sensorsInfo: Flow<Result<List<SensorInfoDto>>>
}
