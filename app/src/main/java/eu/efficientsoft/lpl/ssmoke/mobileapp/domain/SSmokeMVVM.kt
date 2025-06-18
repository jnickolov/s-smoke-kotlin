package eu.efficientsoft.lpl.ssmoke.mobileapp.domain

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import eu.efficientsoft.lpl.ssmoke.mobileapp.features.I18n
import eu.efficientsoft.lpl.ssmoke.mobileapp.http.serverConnector
import eu.efficientsoft.lpl.ssmoke.mobileapp.util.onError
import eu.efficientsoft.lpl.ssmoke.mobileapp.util.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.Instant

//data class SSmokeI18nState(
//    val i18n: I18n? = null,
//) {
//    fun lang(): String? = i18n?.code
//}

class SSmokeI18nViewModel: ViewModel() {
    private val _state = MutableStateFlow<I18n?> (null)
    val state = _state.asStateFlow()

    fun setI18n (newI18n: I18n) {
        _state.update { newI18n }
    }

    fun loadI18n (langCode: String) {
        serverConnector.loadI18n(langCode) {
            it.onSuccess { result ->
                Log.i("URAAAAAAAA Zaredih i18n", "Code: ${result.code}")
                setI18n(result)
            }
                .onError { e ->
                    Log.i("Failed loading i18n", "NetworkError: ${e}")
                }
        }
    }

    fun onEvent (event: I18nEvent) {
        Log.i("SSMOKEI18nViewModel : onEvent (lang = $event.lang", "*******************************")
        when (event) {
            is I18nEvent.OnLangSelected -> {
                loadI18n(event.lang)
                Log.i("SSMOKEI18nViewModel : onEvent (lang = $event.lang", "finished loading")
            }
        }
    }
}

data class SSmokeUserState (
    val loggedUser: String? = null,
    val lastlogin: Instant? = null,
)

class SSmokeUserViewModel: ViewModel() {
    private val _state = MutableStateFlow(SSmokeUserState())
    val state = _state.asStateFlow()

    var refreshToken: String = "s-smoke"

    fun onLoginEvent (event: LoginEvent) {
        Log.i("SSMOKEUserViewModel : onLoginEvent *********", "*******************************")
        when (event) {
            is LoginEvent.OnLogin -> {

            }

            LoginEvent.OnLogout -> {
                TODO()
            }
            is LoginEvent.OnRelogin -> {

            }
        }
    }
}

class SSmokeViewModel1 : ViewModel() {
    private var _lang = mutableStateOf<String?>(null)
    private var _i18n = mutableStateOf<I18n?>(null)
    private var _username = mutableStateOf<String?>(null)

    // No need of keeping refresh token here!
    //private var _refreshToken by mutableStateOf("s-smoke")

    private var _fcmToken = mutableStateOf<String?>(null)

    val i18n = _i18n
    val lang = _lang
    val username = _username
    //val refreshToken = _refreshToken
    val fcmToken = _fcmToken

    fun setLang (newLang: String) {
        _lang.value = newLang
    }
    fun setI18n (data: I18n) {
        _i18n.value = data
        setLang(data.code)
    }
    fun setUsername (un: String?) {
        _username.value = un
    }

//    fun setRefreshToken (token: String) {
//        _refreshToken = token
//    }

    fun setFcmToken (token: String?) {
        _fcmToken.value = token
    }


    init {
        Log.i ("S-Smoke MVVM", "*********initializing MVVM from bundle")
        //  load from Bundle
    }

}
