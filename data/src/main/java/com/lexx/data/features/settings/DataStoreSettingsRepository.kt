package com.lexx.data.features.settings

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.Preferences.Key
import androidx.datastore.preferences.core.edit
import com.lexx.data.BuildConfig
import com.lexx.domain.features.settings.SettingsRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class DataStoreSettingsRepository @Inject constructor(
    private val dataStorePreferences: DataStore<Preferences>
) : SettingsRepository {
    private val serverAddressPreferencesKey = stringPreferencesKey(name = "server_address")

    override suspend fun getServerAddress(): String {
        return dataStorePreferences.data.first()[serverAddressPreferencesKey] ?: BuildConfig.DEFAULT_BASE_URL
    }

    override suspend fun setServerAddress(serverAddress: String) {
        dataStorePreferences.edit { it ->
            it[serverAddressPreferencesKey] = serverAddress
        }
    }
}
