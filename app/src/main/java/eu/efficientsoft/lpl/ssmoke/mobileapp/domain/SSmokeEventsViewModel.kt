package eu.efficientsoft.lpl.ssmoke.mobileapp.domain

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eu.efficientsoft.lpl.ssmoke.mobileapp.http.EventHttpConnector
import eu.efficientsoft.lpl.ssmoke.mobileapp.http.EventNamingDto
import eu.efficientsoft.lpl.ssmoke.mobileapp.screens.LoginScreen
import eu.efficientsoft.lpl.ssmoke.mobileapp.util.ToastManager
import eu.efficientsoft.lpl.ssmoke.mobileapp.util.onError
import eu.efficientsoft.lpl.ssmoke.mobileapp.util.onSuccess
import kotlinx.coroutines.launch


class SSmokeEventsViewModel: ViewModel() {
    val eventHttpConnector = EventHttpConnector()

    val loadEvents = mutableStateOf (true)
    val loading = mutableStateOf (false)
    private var _eventListState = mutableStateOf(listOf<EventNamingDto>())

    val events = _eventListState;



    fun loadEvents (username: String) {
        viewModelScope.launch {
            loading.value = true;
            eventHttpConnector.loadEventsForUser(username)
                .onSuccess { it ->
                    events.value = it
                    loading.value = false;
                    Log.v("", "LOADED EVENTS: ${it.size}")
                    //it.forEach {e-> Log.v("", "Event: ${e}") }
                }
                .onError {
                    loading.value = false;
                    ToastManager.showToast(
                        message = "Проблем с връзката. Опитайте отново.",
                        isSuccess = false
                    )
                }
        }
    }

    fun loadFireEvents(username: String) {
        viewModelScope.launch {
            eventHttpConnector.loadFireAlarmsForUser(username)
                .onSuccess { it ->
                    events.value = it
                }
                .onError {
                    ToastManager.showToast(
                        message = "Проблем с връзката. Опитайте отново.",
                        isSuccess = false
                    )
                }
        }
    }

}