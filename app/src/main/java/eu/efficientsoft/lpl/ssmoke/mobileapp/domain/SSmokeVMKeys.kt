package eu.efficientsoft.lpl.ssmoke.mobileapp.domain

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey

interface SSmokeVMKeys {
    val i18nLangKey: String
        get() = "i18n-lang"

    val i18nLangDBKey: Preferences.Key<String>
        get() = stringPreferencesKey(i18nLangKey)

    val userKey: String
        get() = "user"

    val userDBKey: Preferences.Key<String>
        get() = stringPreferencesKey(userKey)

    val usernameKey: String
        get() = "username"

    val usernameDBKey: Preferences.Key<String>
        get() = stringPreferencesKey(userKey)

}