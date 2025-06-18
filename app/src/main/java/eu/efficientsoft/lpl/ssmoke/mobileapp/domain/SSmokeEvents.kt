package eu.efficientsoft.lpl.ssmoke.mobileapp.domain

sealed interface I18nEvent {
    data class OnLangSelected (val lang: String): I18nEvent
}

sealed interface LoginEvent {
    data class OnLogin(val unsername: String, val passowrd: String): LoginEvent
    data object OnLogout: LoginEvent

    data object OnForgotPasswordTransition: LoginEvent
    data object OnChangePasswordTransition: LoginEvent  // move to change password screen
    data class OnChangePassword (val unsername: String, val password: String): LoginEvent

    data class OnRelogin (val password: String): LoginEvent
}

