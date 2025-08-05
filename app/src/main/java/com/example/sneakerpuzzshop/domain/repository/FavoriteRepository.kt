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
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoriteRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val firebaseAuth: FirebaseAuth
) {
    private val dataStoreCache = ConcurrentHashMap<String, DataStore<Preferences>>()
    private val FAVORITE_KEY = stringSetPreferencesKey("favorite_products")

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
            getDataStoreForUid(uid).data.map { prefs ->
                prefs[FAVORITE_KEY] ?: emptySet()
            }
        }
    }

    suspend fun toggleFavorite(productId: String) {
        val uid = firebaseAuth.currentUser?.uid ?: return // Nếu chưa đăng nhập, không làm gì cả
        getDataStoreForUid(uid).edit { prefs ->
            val currentFavorites = prefs[FAVORITE_KEY] ?: emptySet()
            if (productId in currentFavorites) {
                prefs[FAVORITE_KEY] = currentFavorites - productId
            } else {
                prefs[FAVORITE_KEY] = currentFavorites + productId
            }
        }
    }

    private fun getDataStoreForUid(uid: String): DataStore<Preferences> {
        return dataStoreCache.getOrPut(uid) {
            // 1. Nó sẽ tìm trong cache xem có DataStore cho UID này chưa.
            // 2. Nếu CÓ, nó trả về instance đã lưu.
            // 3. Nếu KHÔNG, nó sẽ thực thi khối lambda để tạo một instance mới,
            //    lưu vào cache, và trả về instance mới đó.
            // Điều này đảm bảo mỗi UID chỉ có MỘT instance DataStore được tạo ra.
            context.createDataStore("user_favorites_$uid")
        }
    }

    private fun Context.createDataStore(name: String): DataStore<Preferences> {
        return preferencesDataStore(name = name).getValue(this, ::_dataStore)
    }

    companion object {
        private val _dataStore: Any? = null
    }
}