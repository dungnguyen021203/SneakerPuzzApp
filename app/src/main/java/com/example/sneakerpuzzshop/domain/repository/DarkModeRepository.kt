package com.example.sneakerpuzzshop.domain.repository

import com.example.sneakerpuzzshop.data.datastore.PreferenceManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DarkModeRepository @Inject constructor(
    private val preferenceManager: PreferenceManager
) {
    val isDarkMode: Flow<Boolean> = preferenceManager.darkModeFlow

    suspend fun setDarkMode(isDark: Boolean) {
        preferenceManager.setDarkMode(isDark)
    }
}