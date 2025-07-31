package eu.efficientsoft.lpl.ssmoke.mobileapp.domain

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eu.efficientsoft.lpl.ssmoke.mobileapp.http.DetectorGroupNamingDto
import eu.efficientsoft.lpl.ssmoke.mobileapp.http.EventHttpConnector
import eu.efficientsoft.lpl.ssmoke.mobileapp.http.EventNamingDto
import eu.efficientsoft.lpl.ssmoke.mobileapp.util.ToastManager
import eu.efficientsoft.lpl.ssmoke.mobileapp.util.onError
import eu.efficientsoft.lpl.ssmoke.mobileapp.util.onSuccess
import kotlinx.coroutines.launch


class SSmokeEventsViewModel: ViewModel() {
    val eventHttpConnector = EventHttpConnector()

    val loadingEvents = mutableStateOf(false)
    val loadingFireAlarms = mutableStateOf(false)
    private var _eventListState = mutableStateOf(listOf<EventNamingDto>())
    private var _eventFireAlarmsState = mutableStateOf(listOf<DetectorGroupNamingDto>())

    val events = _eventListState
    val fireEvents = _eventFireAlarmsState

    fun loadEvents(username: String) {
        viewModelScope.launch {
            loadingEvents.value = true;
            val res = eventHttpConnector.loadEventsForUser(username)
            loadingEvents.value = false;
            res.onSuccess { it ->
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

    fun loadFireEvents(username: String) {
        viewModelScope.launch {
            loadingFireAlarms.value = true;
            val res = eventHttpConnector.loadFireAlarmsForUser(username)
            loadingFireAlarms.value = false;
            res.onSuccess { it ->
                fireEvents.value = it
                Log.v("", "Fire events: size = ${it.size}")
                it.forEach { e-> Log.v("", "${e.toString()}") }
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