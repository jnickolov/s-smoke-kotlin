package eu.efficientsoft.lpl.ssmoke.mobileapp.screens
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import eu.efficientsoft.lpl.ssmoke.mobileapp.R
import eu.efficientsoft.lpl.ssmoke.mobileapp.domain.SSmokeEventsViewModel
import eu.efficientsoft.lpl.ssmoke.mobileapp.domain.SSmokeUserState
import eu.efficientsoft.lpl.ssmoke.mobileapp.http.EventNamingDto
import java.util.Date

data class SSmokeEvent (val timestamp: Date, val name: String, val missed: Boolean, val fireAlarm: Boolean, val rfLevel: Double, val batVoltage: Double, val opened: Boolean = false) {
    val isBatteryLow = batVoltage < 2.4
    val isRfLow = rfLevel < 30.0
    val hasAlarm = isBatteryLow || isRfLow || fireAlarm || missed
    val isNormal = ! hasAlarm
}

private val data = mutableStateListOf<SSmokeEvent>(
    SSmokeEvent(Date(2024, 10, 13, 12, 35, 10), "Кухня", false, false, 36.0, 2.89),
    SSmokeEvent(Date(2024, 10, 14, 12, 35, 20), "Таван", true, false, 36.0, 2.89),
    SSmokeEvent(Date(2024, 11, 15, 12, 35, 30), "Гараж", false, true, 36.0, 2.89),
    SSmokeEvent(Date(2024, 11, 26, 12, 35, 40), "Мазе", false, true, 36.0, 2.19),
    SSmokeEvent(Date(2024, 11, 27, 12, 35, 50), "Спалня", false, false, 26.0, 2.89),
    SSmokeEvent(Date(2024, 11, 22, 12, 35, 10), "Кучешка колибка", false, true, 26.0, 1.89),
)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OpenedEventCard (e: SSmokeEvent) {
    Card(
        border = BorderStroke(1.dp, color= Color.LightGray),
        modifier = Modifier.fillMaxWidth().padding(8.dp, 4.dp),
        onClick = {
            val i = data.indexOf(e)
            data[i] = e.copy(opened = !e.opened)
        }
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            val sdate = e.timestamp.date.toString() + "." + e.timestamp.month.toString() + "." + e.timestamp.year.toString() +
                    " " + e.timestamp.hours.toString() + ":" + e.timestamp.minutes.toString() + ":" + e.timestamp.seconds.toString()
            Text("${sdate}: ${e.name}", fontWeight = FontWeight.Bold)
            if (e.missed) {
                Row {
                    Image(
                        painter = painterResource(id = R.drawable.orphan_24px),
                        contentDescription = "",
                    )
                    Text("Пропусната комуникация")
                }
            } else {
                if (e.fireAlarm) {
                    Row {
                        Image(
                            painter = painterResource(id = R.drawable.fire_24px),
                            contentDescription = "",
                        )
                        Text("Аларма пожар")
                    }
                }
                if (e.isBatteryLow) {
                    Row {
                        Image(
                            painter = painterResource(id = R.drawable.battery_1_bar_24px),
                            contentDescription = "",
                        )
                        Text("Изтощена батерия: ${e.batVoltage} V")
                    }
                }
                if (e.isRfLow) {
                    Row {
                        Image(
                            painter = painterResource(id = R.drawable.network_wifi_1_bar_24px),
                            contentDescription = "",
                        )
                        Text("Слаба WiFi мрежа: ${e.rfLevel} dB")
                    }
                }
                Text("      Батерия: ${e.batVoltage} V")
                Text("      RF мрежа: ${e.rfLevel} dB")
            }
        }
    }
}

@Composable
fun ClosedEventCard (e: SSmokeEvent) {
    Card(
        border = BorderStroke(1.dp, color= Color.LightGray),
        modifier = Modifier.fillMaxWidth().padding(8.dp, 4.dp),
        onClick = {
            val i = data.indexOf(e)
            data[i] = e.copy(opened = !e.opened)
        }
    ) {
        Row (
            modifier = Modifier.padding(8.dp)
        ) {
            val sdate = e.timestamp.date.toString() + "." + e.timestamp.month.toString() + "." + e.timestamp.year.toString()
            Text("${sdate}: ${e.name}", fontWeight = FontWeight.Bold, modifier = Modifier.padding(0.dp, 0.dp, 4.dp, 0.dp))
            if (e.missed) {
                Row {
                    Image(
                        painter = painterResource(id = R.drawable.orphan_24px),
                        contentDescription = "",
                    )
                    //Text("Пропусната комуникация")
                }
            } else {
                if (e.fireAlarm) {
                    Image(
                        painter = painterResource(id = R.drawable.fire_24px),
                        contentDescription = "",
                    )
                }
                if (e.isBatteryLow) {
                    Image(
                        painter = painterResource(id = R.drawable.battery_1_bar_24px),
                        contentDescription = "",
                    )
                }
                if (e.isRfLow) {
                    Image(
                        painter = painterResource(id = R.drawable.network_wifi_1_bar_24px),
                        contentDescription = "",
                    )
                }
            }
        }
    }
}

@Composable fun EventsScreen(
    i18n: I18n?,
    eventsVM: SSmokeEventsViewModel,
    user: SSmokeUserState
) {
    //val events by remember { eventsVM.events }
    val events by eventsVM.events
    //val reloadEvents by remember { eventsVM.loadEvents }

    LaunchedEffect(user) {
        user.username?.let {
            eventsVM.loadEvents(user.username)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
        .padding(0.dp)
        .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
    ) {
        if (eventsVM.loadingEvents.value) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator()
            }
        } else {
            events.forEach { e->
                EventView (e)
            }
        }
    }
}

@Composable fun EventView (dto: EventNamingDto) {
    Card (
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 4.dp)
            .border(1.dp, Color.Gray)
    ) {
        Row(modifier = Modifier.fillMaxWidth().padding(0.dp)) {
            Text (dto.timestamp, modifier = Modifier.padding(12.dp, 8.dp))
            Text (dto.eventName, fontWeight = FontWeight.Bold, modifier = Modifier.padding(8.dp))
        }
        Text(dto.detectorName, modifier = Modifier.padding(12.dp, 8.dp))
        Text(dto.sensorName, modifier = Modifier.padding(12.dp, 8.dp))
    }
}