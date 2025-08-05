package com.example.sneakerpuzzshop.domain.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.favoriteDataStore: DataStore<Preferences> by preferencesDataStore(name = "favorites")

@Singleton
class FavoriteRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val firebaseAuth: FirebaseAuth
) {
    // lắng nghe sự thay đổi trạng thái đăng nhập từ FirebaseAuth
    // Flow này sẽ phát ra UID mới mỗi khi người dùng đăng nhập, hoặc null khi họ đăng xuất.
    private val userUidFlow: Flow<String?> = callbackFlow {
        val authStateListener = FirebaseAuth.AuthStateListener { auth ->
            trySend(auth.currentUser?.uid)
        }
        firebaseAuth.addAuthStateListener(authStateListener)
        awaitClose { firebaseAuth.removeAuthStateListener(authStateListener) }
    }

    // hủy bỏ flow cũ (của người dùng trước đó hoặc flow rỗng)
    // chuyển sang DataStore của người dùng mới.
    // (đăng xuất), nó sẽ trả về một flow chỉ chứa danh sách rỗng (flowOf(emptySet())).
    @OptIn(ExperimentalCoroutinesApi::class)
    val favorites: Flow<Set<String>> = userUidFlow.flatMapLatest { uid ->
        if (uid == null) {
            flowOf(emptySet())
        } else {
            val key = stringSetPreferencesKey("favorites_$uid")
            context.favoriteDataStore.data.map { prefs ->
                prefs[key] ?: emptySet()
            }
        }
    }

    suspend fun toggleFavorite(productId: String) {
        val uid = firebaseAuth.currentUser?.uid ?: return // Nếu chưa đăng nhập, không làm gì cả
        val key = stringSetPreferencesKey("favorites_$uid")

        context.favoriteDataStore.edit  { prefs ->
            val currentFavorites = prefs[key] ?: emptySet()
            if (productId in currentFavorites) {
                prefs[key] = currentFavorites - productId
            } else {
                prefs[key] = currentFavorites + productId
            }
        }
    }
}