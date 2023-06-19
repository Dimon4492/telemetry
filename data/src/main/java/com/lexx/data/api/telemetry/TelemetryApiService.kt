package com.lexx.data.api.telemetry

import com.lexx.data.api.telemetry.models.SensorDataDto
import com.lexx.data.api.telemetry.models.SensorInfoDto
import retrofit2.http.GET
import retrofit2.http.Url

interface TelemetryApiService {
    @GET
    suspend fun getSensorsInfo(@Url url: String): List<SensorInfoDto>

    @GET
    suspend fun getSensorsData(@Url url: String): List<SensorDataDto>
}
