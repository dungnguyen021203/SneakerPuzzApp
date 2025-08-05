package com.example.sneakerpuzzshop.data.datastore

// Use same data store

//import android.content.Context
//import androidx.datastore.preferences.core.booleanPreferencesKey
//import androidx.datastore.preferences.core.edit
//import androidx.datastore.preferences.core.stringSetPreferencesKey
//import androidx.datastore.preferences.preferencesDataStore
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.flow.map
//import javax.inject.Inject
//
//val Context.dataStore by preferencesDataStore(name = "user_preferences")
//
//class PreferenceManager @Inject constructor(private val context: Context) {
//    companion object {
//        private val FAVORITE_KEY = stringSetPreferencesKey("favorite_products")
//    }
//
//    val favoritesFlow: Flow<Set<String>> = context.dataStore.data.map { prefs ->
//        prefs[FAVORITE_KEY] ?: emptySet()
//    }
//
//    suspend fun toggleFavorite(productId: String) {
//        context.dataStore.edit { prefs ->
//            val current = prefs[FAVORITE_KEY] ?: emptySet()
//            prefs[FAVORITE_KEY] = if (productId in current) current - productId else current + productId
//        }
//    }
//}