package eu.efficientsoft.lpl.ssmoke.mobileapp.screens

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import eu.efficientsoft.lpl.ssmoke.mobileapp.domain.SSmokeEventsViewModel
import eu.efficientsoft.lpl.ssmoke.mobileapp.domain.SSmokeUserViewModel
import eu.efficientsoft.lpl.ssmoke.mobileapp.http.DetectorGroupNamingDto
import eu.efficientsoft.lpl.ssmoke.mobileapp.http.resetCommandConnector

@Composable
fun FireAlarmScreen (
    eventsVM: SSmokeEventsViewModel,
    userVM: SSmokeUserViewModel
) {
    LaunchedEffect(userVM, eventsVM.fireEvents) {
        userVM.userState.value.username?.let {
            eventsVM.loadFireEvents(userVM.userState.value.username!!)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
            .padding(0.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (eventsVM.loadingFireAlarms.value) {
            Box(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator()
            }
        } else {
            ElevatedButton(onClick = {
                Log.v("CHECK AGAIN", "CHECK AGAIN")
                eventsVM.run { loadFireEvents(userVM.userState.value.username!!) }
            }) {
                Text(text ="Проверте отново",
                    textAlign = TextAlign.Right,
                    fontWeight = FontWeight.Bold, color = Color.Magenta, modifier = Modifier.padding(8.dp).fillMaxWidth(),
                )
            }

            if (eventsVM.fireEvents.value.size == 0) {
                Box (Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text (
                        text = "Няма пожарни събития",
                        color=Color.Green,
                        fontWeight = FontWeight.Bold)
                }
            } else {
                eventsVM.fireEvents.value.forEach { dto ->
                    FireAlarmView(dto = dto)
//                   onReset = {
//                       eventsVM.resetDetector(dto.detectorId)
//                   })
                }
            }
        }
    }
}

@Composable
fun FireAlarmView (dto: DetectorGroupNamingDto) {
    Card (
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 4.dp)
            .border(1.dp, Color.Gray)
    ) {
        Text ("${dto.detectorId}: ${dto.name}",
            fontWeight = FontWeight.Bold, modifier = Modifier.padding(8.dp))
        dto.sensors.forEach { sensorNamingDto ->
            Text(sensorNamingDto.name, modifier = Modifier.padding(12.dp, 8.dp))
        }
        ElevatedButton(onClick = {
            Log.v("RESET COORDINATOR", "COORDINATOR ID = ${dto.detectorId}")
            resetCommandConnector.sendReset(dto.detectorId)
        }) {
            Text(text ="RESET",
                fontWeight = FontWeight.Bold, color = Color.Red, modifier = Modifier.padding(8.dp)
            )
        }
    }
}