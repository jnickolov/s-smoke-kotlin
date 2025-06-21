package eu.efficientsoft.lpl.ssmoke.mobileapp.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import eu.efficientsoft.lpl.ssmoke.mobileapp.util.AppSettings

internal const val dataStoreFileName = "ssmoke_pro.preferences_pb"

lateinit var dataStoreRepository: DataStoreRepository


fun createDataStore(context: Context): DataStore<Preferences> {
    return AppSettings.getDataStore(
        producePath = {
            context.filesDir
                .resolve(dataStoreFileName)
                .absolutePath
        }
    )
}