package eu.efficientsoft.lpl.ssmoke.mobileapp.features

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable fun DevicesScreen () {
    
    Surface (modifier = Modifier.fillMaxSize()){
        Log.i("Home screen", "Surface")
        Text("Devices Screen")
    }
}
