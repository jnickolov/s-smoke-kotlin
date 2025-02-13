package eu.efficientsoft.lpl.ssmoke.mobileapp.app

import android.util.Log
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import eu.efficientsoft.lpl.ssmoke.mobileapp.Devices
import eu.efficientsoft.lpl.ssmoke.mobileapp.Events
import eu.efficientsoft.lpl.ssmoke.mobileapp.Help
import eu.efficientsoft.lpl.ssmoke.mobileapp.Home
import eu.efficientsoft.lpl.ssmoke.mobileapp.Login
import eu.efficientsoft.lpl.ssmoke.mobileapp.Notifications
import eu.efficientsoft.lpl.ssmoke.mobileapp.Profile
import eu.efficientsoft.lpl.ssmoke.mobileapp.Settings
import eu.efficientsoft.lpl.ssmoke.mobileapp.features.DevicesScreen
import eu.efficientsoft.lpl.ssmoke.mobileapp.features.EventsScreen
import eu.efficientsoft.lpl.ssmoke.mobileapp.features.HelpScreen
import eu.efficientsoft.lpl.ssmoke.mobileapp.features.HomeScreen
import eu.efficientsoft.lpl.ssmoke.mobileapp.features.LoginScreen
import eu.efficientsoft.lpl.ssmoke.mobileapp.features.NotificationsScreen
import eu.efficientsoft.lpl.ssmoke.mobileapp.features.SettingsScreen
import eu.efficientsoft.lpl.ssmoke.mobileapp.features.UserProfileScreen


@Composable
fun SSmokeApp(modifier: Modifier = Modifier, pageRoute: Any) {
    Log.i("IN SSMOKE APP", pageRoute.toString())

    val navController = rememberNavController()

    NavHost(navController, startDestination = Home) {
        composable<Home> { HomeScreen() }
        composable<Devices> { DevicesScreen() }
        composable<Notifications> { NotificationsScreen() }
        composable<Events> { EventsScreen() }
        composable<Settings> { SettingsScreen() }
        composable<Help> { HelpScreen() }
        composable<Login> { LoginScreen() }
        composable<Profile> { UserProfileScreen() }
    }

    navController.navigate(pageRoute)
}

