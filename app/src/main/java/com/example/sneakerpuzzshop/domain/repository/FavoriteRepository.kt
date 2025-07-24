package com.example.sneakerpuzzshop.domain.repository

import com.example.sneakerpuzzshop.data.datastore.PreferenceManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoriteRepository @Inject constructor(
    private val preferenceManager: PreferenceManager
) {
    val favorites: Flow<Set<String>> = preferenceManager.favoritesFlow

    suspend fun toggleFavorite(productId: String) {
        preferenceManager.toggleFavorite(productId)
    }
}