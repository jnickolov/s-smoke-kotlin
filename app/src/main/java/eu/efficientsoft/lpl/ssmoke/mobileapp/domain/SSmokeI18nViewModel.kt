package eu.efficientsoft.lpl.ssmoke.mobileapp.domain

import android.os.Bundle
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eu.efficientsoft.lpl.ssmoke.mobileapp.data.dataStoreRepository
import eu.efficientsoft.lpl.ssmoke.mobileapp.screens.I18n
import eu.efficientsoft.lpl.ssmoke.mobileapp.screens.i18nBg_preloaded
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class SSmokeI18nViewModel(): ViewModel(), SSmokeVMKeys  {

    private val _i18n = MutableStateFlow<I18n?> (null)
    val i18n = _i18n.asStateFlow()

    //private val _lang: MutableState<String?> = mutableStateOf(null)// NE STAVA TAKA mutableStateOf(i18n.value?.code)
        //NE STAVA: derivedStateOf { _i18n.value?.code }

    private val _lang  = mutableStateOf<String?>(null)
    val lang by _lang

    init {
        viewModelScope.launch (Dispatchers.IO){
            loadFromDataStore()
        }
    }

    fun setI18n (newI18n: I18n) {
        //_lang.value = newI18n.code
        Log.v("MVVM", "saving in datastore: lang = ${newI18n.code}")
        _i18n.update { newI18n }
        _lang.value = newI18n.code
        Log.v("MVVM", "After updating-++++++: lang = $lang, i18n.code = ${i18n.value?.code}")
        saveInDataStore()
    }

    fun loadI18n (langCode: String) {
        // TODO: load from server
        Log.v("", "SETING BG PRELOADED LANG")
        setI18n(i18nBg_preloaded)
//        viewModelScope.launch(Dispatchers.IO) {
//            I18nRepository.loadI18n(langCode, ::setI18n)
//        }
    }

    fun saveInBundle (bundle: Bundle) {
        bundle.run {
            Log.v("MVVM", "CVM: saving value to Bundle: $lang ")
            putString (i18nLangKey, lang)
        }

    }

    fun loadFromBundle (bundle: Bundle) {
        val savedLang = bundle.getString(i18nLangKey)
        if (savedLang.isValidSSmokeLang() && savedLang != i18n.value?.code) {
            savedLang?.let {
                loadI18n (savedLang)
                _lang.value = savedLang
            }
        }
        Log.v("MVVM", "CVM: loading value from Bundle: $lang ")
    }
    suspend fun loadFromDataStore () {//dataStoreRepository: DataStoreRepository) {
        Log.v("MVVM", "CVM: loading user value from DataStore: ")
        dataStoreRepository.loadLang { newLang ->
            newLang.let {
                if (newLang.isValidSSmokeLang() && newLang != lang) {
                    loadI18n(newLang)
                    _lang.value = newLang
                }
            }
        }
    }

    fun saveInDataStore() {//dataStoreRepository: DataStoreRepository) {
        Log.v("MVVM", "CVM: Saving value in DataStoreyyy: $lang")

        lang?.let {
            viewModelScope.launch {
                dataStoreRepository.saveLang(lang!!)
            }
        }
    }
}

fun String?.isValidSSmokeLang (): Boolean = validLanguages.contains(this)

private val validLanguages = listOf("bg", "en")

// -------------------------------------------------------------

