package eu.efficientsoft.lpl.ssmoke.mobileapp.screens

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.snapping.SnapPosition
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
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import eu.efficientsoft.lpl.ssmoke.mobileapp.domain.SSmokeEventsViewModel
import eu.efficientsoft.lpl.ssmoke.mobileapp.domain.SSmokeUserState
import eu.efficientsoft.lpl.ssmoke.mobileapp.domain.SSmokeUserViewModel
import eu.efficientsoft.lpl.ssmoke.mobileapp.http.DetectorGroupNamingDto

@Composable
fun HomeScreen (
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
    ) {

        Log.v("HomeScreen", "HomeScreen")
        if (eventsVM.loadingFireAlarms.value) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator()
            }
        } else {
            eventsVM.fireEvents.value.forEach { fe ->
               FireAlarmView(fe)
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

        }) {
            Text(text ="RESET",
                fontWeight = FontWeight.Bold, color = Color.Red, modifier = Modifier.padding(8.dp)
            )
        }
    }
}