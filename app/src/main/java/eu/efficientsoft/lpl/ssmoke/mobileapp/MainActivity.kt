package eu.efficientsoft.lpl.ssmoke.mobileapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import eu.efficientsoft.lpl.ssmoke.mobileapp.app.SSmokeApp
import eu.efficientsoft.lpl.ssmoke.mobileapp.app.SSmokeTopBar
import eu.efficientsoft.lpl.ssmoke.mobileapp.features.DevicesScreen
import eu.efficientsoft.lpl.ssmoke.mobileapp.features.EventsScreen
import eu.efficientsoft.lpl.ssmoke.mobileapp.features.HelpScreen
import eu.efficientsoft.lpl.ssmoke.mobileapp.features.HomeScreen
import eu.efficientsoft.lpl.ssmoke.mobileapp.features.LoginScreen
import eu.efficientsoft.lpl.ssmoke.mobileapp.features.NotificationsScreen
import eu.efficientsoft.lpl.ssmoke.mobileapp.features.SettingsScreen
import eu.efficientsoft.lpl.ssmoke.mobileapp.features.UserProfileScreen
import eu.efficientsoft.lpl.ssmoke.mobileapp.ui.theme.SSmokeMobileAppTheme
import kotlinx.serialization.Serializable

@Serializable
object Home
@Serializable
object Devices
@Serializable
object Notifications
@Serializable
object Events
@Serializable
object Profile
@Serializable
object Settings
@Serializable
object Login
@Serializable
object Help

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val drawerState = rememberDrawerState(DrawerValue.Closed)

            SSmokeMobileAppTheme {
                Scaffold (
                    topBar = { SSmokeTopBar(drawerState) },
                    ) {
                    SSmokeApp (modifier = Modifier.padding(it), pageRoute = Home)
                }
            }

        }
    }
}

//@Composable
//fun Greeting(name: String, modifier: Modifier = Modifier) {
//    Text(
//        text = "Hello $name!",
//        modifier = modifier
//    )
//    Button(onClick = { /*TODO*/ }) {
//        Text("OpenDrawer")
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    SSmokeMobileAppTheme {
//        Greeting("Android")
//    }
//}