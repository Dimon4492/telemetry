package com.lexx.data.features.sensors.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lexx.data.db.room.models.LocalSensorDataDto
import kotlinx.coroutines.flow.Flow

@Dao
abstract class LocalSensorDataDao {
    @Query("SELECT * FROM sensors")
    abstract fun getAllSensors(): Flow<List<LocalSensorDataDto>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun setSensorInfo(sensorInfo: LocalSensorDataDto)
}
