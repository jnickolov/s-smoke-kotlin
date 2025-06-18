package eu.tutorials.mvvm_demo

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map

class DataStoreRepository(
    private val dataStore: DataStore<Preferences>
) {
    companion object {
        val COUNTER_KEY = intPreferencesKey(name = "saved_counter")
    }

    suspend fun saveCounter(cnt: Int): Boolean =
        try {
            dataStore.edit { preferences ->
                preferences.set(key = AppSettings.COUNTER_KEY, value = cnt)
            }
            true
        } catch (e: Exception) {
            println("saveTimestamp() Error: $e")
            false
        }

    fun readCounter(): Flow<Int> =
        dataStore.data
            .catch { emptyFlow<Int>() }
            .map { preferences ->
                preferences[COUNTER_KEY] ?: 0
            }
}