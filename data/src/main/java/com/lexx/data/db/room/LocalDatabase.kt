package com.lexx.data.db.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lexx.data.db.room.models.LocalSensorDataDto
import com.lexx.data.features.sensors.local.LocalSensorDataDao

@Database(
    entities = [LocalSensorDataDto::class],
    version = 1,
    //exportSchema = true,
)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun sensorsLocalInfo() : LocalSensorDataDao
}
