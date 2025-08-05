package com.example.sneakerpuzzshop.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.settingsDataStore by preferencesDataStore(name = "settings_prefs")

class SettingsManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        private val IS_DARK_MODE = booleanPreferencesKey("is_dark_mode")
    }

    val darkModeFlow: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[IS_DARK_MODE] == true
    }

    suspend fun setDarkMode(isDarK: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[IS_DARK_MODE] = isDarK
        }
    }
}