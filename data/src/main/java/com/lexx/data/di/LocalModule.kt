package com.lexx.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.lexx.data.api.telemetry.util.RemoteDbTimeFormatterBuilder
import com.lexx.data.db.room.LocalDatabase
import com.lexx.data.features.sensors.local.LocalSensorDataDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.time.format.DateTimeFormatter
import javax.inject.Singleton

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {
    @Provides
    @Singleton
    fun providePreferencesDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.dataStore
    }

    @Provides
    @Singleton
    fun provideDataFormatter(): DateTimeFormatter {
        return RemoteDbTimeFormatterBuilder()
            .build()
    }

    @Provides
    @Singleton
    fun provideLocalDatabase(@ApplicationContext context: Context): LocalDatabase {
        return Room.databaseBuilder(
            context,
            LocalDatabase::class.java,
            "sensors"
        ).build()
    }

    @Provides
    @Singleton
    fun provideLocalSensorDataDao(
        localDatabase: LocalDatabase
    ) : LocalSensorDataDao {
        return localDatabase.sensorsLocalInfo()
    }
}
