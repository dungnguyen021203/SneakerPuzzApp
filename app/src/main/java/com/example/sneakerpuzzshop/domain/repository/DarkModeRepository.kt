package com.example.sneakerpuzzshop.domain.repository

import com.example.sneakerpuzzshop.data.datastore.PreferenceManager
import com.example.sneakerpuzzshop.data.datastore.SettingsManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DarkModeRepository @Inject constructor(
    private val settingsManager: SettingsManager
) {
    val isDarkMode: Flow<Boolean> = settingsManager.darkModeFlow

    suspend fun setDarkMode(isDark: Boolean) {
        settingsManager.setDarkMode(isDark)
    }
}