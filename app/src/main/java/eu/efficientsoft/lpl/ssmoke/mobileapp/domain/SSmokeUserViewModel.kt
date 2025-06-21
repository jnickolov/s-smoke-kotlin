package eu.efficientsoft.lpl.ssmoke.mobileapp.domain

import android.os.Bundle
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eu.efficientsoft.lpl.ssmoke.mobileapp.data.dataStoreRepository
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@Serializable
data class SSmokeUserState (
    val username: String? = null,
    val name: String? = null,
    val isLogged: Boolean = false
)

class SSmokeUserViewModel: ViewModel(), SSmokeVMKeys {
    var userState = mutableStateOf(SSmokeUserState())
        private set

    val user by userState


    fun login (username: String, password: String, onLogin: () -> Unit, onError: () -> Unit) {
        // TODO
    }

    fun saveInBundle(bundle: Bundle) {
        bundle.run {
            putString (usernameKey, user.username)
            putString (userKey, user.name)
        }
    }

    fun loadFromBundle (bundle: Bundle) {
        val newUser: String? = bundle.getString (userKey)
        val newUsername : String? = bundle.getString (usernameKey)

        userState.value = SSmokeUserState (newUsername, newUser, user?.isLogged ?: false)
    }

    suspend fun loadFromDataStore () {
        dataStoreRepository.loadUser { newUsername, newName ->
            userState.value = SSmokeUserState (newUsername, newName, user?.isLogged ?: false)
        }
    }

    fun saveInDataStore() {//dataStoreRepository: DataStoreRepository) {
        Log.v("MVVM", "CVM: Saving value in DataStore : name: ${user.name}, username: ${user.username}")

        viewModelScope.launch {
            if (user != null && user.name != null && user.username != null) {
                dataStoreRepository.run { saveUser (name = user.name!!, username = user.username!!) }
            }
        }
    }
    //var refreshToken: String = "s-smoke"

//    fun onLoginEvent (event: LoginEvent) {
//        Log.i("SSMOKEUserViewModel : onLoginEvent *********", "*******************************")
//        when (event) {
//            is LoginEvent.OnLogin -> {
//
//            }
//
//            is LoginEvent.OnRelogin -> {
//
//            }
//            else -> {}
//        }
//    }
}
