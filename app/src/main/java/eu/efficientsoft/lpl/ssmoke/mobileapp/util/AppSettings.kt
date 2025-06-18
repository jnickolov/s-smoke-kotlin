package eu.efficientsoft.lpl.ssmoke.mobileapp.util


import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.SupervisorJob

import kotlinx.coroutines.internal.SynchronizedObject
import okio.Path.Companion.toPath



object AppSettings {
    val sSmokeCoroutineScope = CoroutineScope (Dispatchers.Default + SupervisorJob())

    private lateinit var dataStore: DataStore<Preferences>

    val COUNTER_KEY_NAME = "saved_counter"
    val COUNTER_KEY = intPreferencesKey(name = COUNTER_KEY_NAME)

    @OptIn(InternalCoroutinesApi::class)
    private val lock = SynchronizedObject()

    @OptIn(InternalCoroutinesApi::class)
    fun getDataStore(producePath: () -> String): DataStore<Preferences> {
        return synchronized(lock) {
            if (::dataStore.isInitialized) {
                dataStore
            } else {
                PreferenceDataStoreFactory.createWithPath(
                    produceFile = { producePath().toPath() }
                ).also { dataStore = it }
            }
        }
    }
}