package eu.efficientsoft.lpl.ssmoke.mobileapp.app

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import eu.efficientsoft.lpl.ssmoke.mobileapp.R
import eu.efficientsoft.lpl.ssmoke.mobileapp.ui.theme.LPLBlue
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SSmokeTopBar() {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()


    val ssmokeBlue = LPLBlue; //Color(0x78, 0x96, 0xcc)
    val ssmokeColors  = TopAppBarDefaults.topAppBarColors(
        containerColor = ssmokeBlue,
        navigationIconContentColor = Color.White,
        actionIconContentColor = Color.White,
        titleContentColor = Color.White,
    )

    TopAppBar(
        title = { Text(fontWeight = FontWeight.Bold, text = "  S-Smoke") },
        colors = ssmokeColors,
        navigationIcon = {
            IconButton(onClick = {
                Log.i("Navigation Menu Button", "Opening drawer")
                scope.launch { drawerState.open() }
            }) {
                Icon(imageVector = Icons.Filled.Menu, contentDescription = "Menu")
            }
        },
        actions = {
            IconButton(onClick = { /*TODO*/ }) {
                Image(painter = painterResource(id = R.drawable.baseline_help_outline_24),"Help")
            }
        }
    )
    SSmokeDrawer(drawerState, scope)
}
