package eu.efficientsoft.lpl.ssmoke.mobileapp.features

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable fun HomeScreen () {
    Log.i("Home Screen", "Box")
    Box (modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        Text("SSMOKE Home Screen")
    }
}
