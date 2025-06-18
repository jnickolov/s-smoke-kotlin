package eu.efficientsoft.lpl.ssmoke.mobileapp.features

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable fun HomeScreen (i18n: I18n?, changeI18n: (code: String)-> Unit) {
    Log.i("Home Screen", "Box")

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = i18n?.prop("m_mnu_detector")?:"NEMA TAKOVA")
        Button(onClick = {
            //viewModel.loadI18n("bg")
            changeI18n("bg")

        }) {
            Text("Български")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            //viewModel.loadI18n("en")
            changeI18n("en")
        }) {
            Text("English")
        }
    }
}
