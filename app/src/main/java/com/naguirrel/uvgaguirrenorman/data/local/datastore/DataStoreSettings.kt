package com.naguirrel.uvgaguirrenorman.data.local.datastore

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.settingsDataStore by preferencesDataStore(name = "crypto_settings")

object SettingsKeys {
    val LAST_UPDATE_TIMESTAMP = longPreferencesKey("last_update_timestamp")
}

class SettingsRepository(private val context: Context) {

    val lastUpdateFlow: Flow<Long?> =
        context.settingsDataStore.data.map { prefs: Preferences ->
            prefs[SettingsKeys.LAST_UPDATE_TIMESTAMP]
        }

    suspend fun setLastUpdate(timestamp: Long) {
        context.settingsDataStore.edit { prefs ->
            prefs[SettingsKeys.LAST_UPDATE_TIMESTAMP] = timestamp
        }
    }
}
