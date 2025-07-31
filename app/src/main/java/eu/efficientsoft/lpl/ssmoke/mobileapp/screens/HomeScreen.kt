package eu.efficientsoft.lpl.ssmoke.mobileapp.screens

import android.util.Log
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import eu.efficientsoft.lpl.ssmoke.mobileapp.domain.SSmokeUserViewModel

@Composable
fun HomeScreen (
    userViewModel: SSmokeUserViewModel,
    //tryExit: Boolean = false,
    onLogin: () -> Unit) {

    if (! userViewModel.isUserLogged()) {
        Log.v("HONE SCREEN","GOTO LOGIN")
        onLogin()
    }



    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        //Log.v("HomeScreen", "HomeScreen")
        Text("Home")

        // Shpould be empty?
//        if (tryExit) {
////            Text("Наистина ли искате да излезете от приложението?")
//        } else {
//            onNavigate();
//        }
    }
}