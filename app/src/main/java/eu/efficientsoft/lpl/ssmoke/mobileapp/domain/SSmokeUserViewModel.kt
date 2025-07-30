package eu.efficientsoft.lpl.ssmoke.mobileapp.domain

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.messaging.messaging
import eu.efficientsoft.lpl.ssmoke.mobileapp.data.dataStoreRepository
import eu.efficientsoft.lpl.ssmoke.mobileapp.http.UserHttpConnector
import eu.efficientsoft.lpl.ssmoke.mobileapp.util.ToastManager
import eu.efficientsoft.lpl.ssmoke.mobileapp.util.onError
import eu.efficientsoft.lpl.ssmoke.mobileapp.util.onSuccess
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import org.koin.core.context.GlobalContext

@Serializable
data class SSmokeUserState (
    val username: String? = null,
    val name: String? = null,
    val isLogged: Boolean = false,
    //val loginAttemptStatus: Boolean = true
)

class SSmokeUserViewModel: ViewModel(), SSmokeVMKeys {
    val userHttpConnector = UserHttpConnector();
    var userState = mutableStateOf(SSmokeUserState())
        private set

    val user by userState

    var alarmSubscription = mutableStateOf(true);

    var messageSubscription = mutableStateOf(true);

    fun login (username: String, password: String, onSuccessAction: () -> Unit) {
        viewModelScope.launch {
            userHttpConnector.loginAttempt(username, password)
                .onSuccess { it ->
                    if (it != null) {
                        userState.value = it.copy(isLogged = true)
                        Log.v("logged", "Logged as: ${userState.value}")
                        onSuccessAction()
                    } else {
                        Log.v("", "login failed")
                        //Toast.makeText(LocalContext.current, "aaa", Toast.LENGTH_LONG).show()
                        userState.value = userState.value.copy(isLogged = false)
                        ToastManager.showToast(message = "Неуспешен логин. Опитайте отново.", isSuccess = false)
                    }
                }
                .onError {
                    ToastManager.showToast(message = "Проблем с връзката. Опитайте отново.", isSuccess = false)
                }
        }
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



    fun registerForAlarms () {
        val topic = "${user.username}_alarm"
        Firebase.messaging.subscribeToTopic(topic)
            .addOnCompleteListener{ task ->
                val msg = if(task.isSuccessful) "Subscribed for " + topic
                else "Subscription failed"
                Log.v("FCM", msg)
                //Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
                ToastManager.showToast(msg, false)
            }
    }
    fun unregisterForAlarms () {
        val topic = "${user.username}_alarm"
        Firebase.messaging.unsubscribeFromTopic(topic);
        ToastManager.showToast("Unsubscribed", false)
    }
    fun registerForMessages () {
        val topic = "${user.username}_message"
        Firebase.messaging.subscribeToTopic(topic)
            .addOnCompleteListener{ task ->
                val msg = if(task.isSuccessful) "Subscribed for " + topic
                else "Subscription failed"
                Log.v("FCM", msg)
                //Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
                ToastManager.showToast(msg, true)
            }
    }
    fun unregisterForMessages () {
        val topic = "${user.username}_message"
        Firebase.messaging.unsubscribeFromTopic(topic);
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
