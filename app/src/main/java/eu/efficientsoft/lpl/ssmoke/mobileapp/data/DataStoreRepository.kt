package eu.efficientsoft.lpl.ssmoke.mobileapp.data

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import eu.efficientsoft.lpl.ssmoke.mobileapp.domain.SSmokeVMKeys
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class DataStoreRepository(
    private val dataStore: DataStore<Preferences>
) : SSmokeVMKeys {
    private suspend fun saveString(key: Preferences.Key<String>, value: String): Boolean =
        try {
            dataStore.edit { preferences ->
                preferences[key] = value
            }
            true
        } catch (e: Exception) {
            Log.d("DATASTORE", "Error saveString(${key.name}) Error: $e")
            false
        }

    private fun readString(key: Preferences.Key<String>): Flow<String> =
        dataStore.data
            .catch { emptyFlow<String>() }
            .map { preferences ->
                preferences[key] ?: ""  // default to ""
            }

    //----------------------------------

    suspend fun saveLang(lang: String): Boolean = saveString(key = i18nLangDBKey, value = lang)

    suspend fun loadLang (action: (String) -> Unit) {
        withContext(Dispatchers.IO) {
            dataStoreRepository.readString(i18nLangDBKey)
                .catch { }
                .collectLatest { newLang ->
                    newLang?.let {
                        action(newLang)
                    }
                }
        }
    }

    suspend fun saveUser(name: String, username: String) {
        saveString(key = userDBKey, value = name)
        saveString(key = usernameDBKey, value = username)
    }

    suspend fun loadUser(action: (String, String) -> Unit) {
        //dataStoreRepository: DataStoreRepository) {
        Log.v("MVVM", "CVM: loading value from DataStore: ")
        var newName: String? = null
        var newUsername: String? = null

        withContext (Dispatchers.IO) {
           dataStoreRepository.readString(userDBKey)
                .collectLatest {
                    newName = it
                }

            dataStoreRepository.readString(usernameDBKey)
                .collectLatest {
                    newUsername = it
                }

            newName?.let {
                newUsername?.let{
                    action (newUsername, newName)
                }
            }
        }
    }
}