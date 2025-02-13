package eu.efficientsoft.lpl.ssmoke.mobileapp.app

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import eu.efficientsoft.lpl.ssmoke.mobileapp.features.DevicesScreen
import eu.efficientsoft.lpl.ssmoke.mobileapp.features.EventsScreen
import eu.efficientsoft.lpl.ssmoke.mobileapp.features.HelpScreen
import eu.efficientsoft.lpl.ssmoke.mobileapp.features.HomeScreen
import eu.efficientsoft.lpl.ssmoke.mobileapp.features.LoginScreen
import eu.efficientsoft.lpl.ssmoke.mobileapp.features.NewAccountScreen
import eu.efficientsoft.lpl.ssmoke.mobileapp.features.NotificationsScreen
import eu.efficientsoft.lpl.ssmoke.mobileapp.features.SettingsScreen
import eu.efficientsoft.lpl.ssmoke.mobileapp.features.UserProfileScreen
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
@Serializable
object NewAccount


@Composable
fun SSmokeApp(pageRoute: Any) {
   // Log.i("IN SSMOKE APP", pageRoute.toString())

    val navController = rememberNavController()

    NavHost(navController, startDestination = Home) {
        composable<Home> { HomeScreen() }
        composable<Devices> { DevicesScreen() }
        composable<Notifications> { NotificationsScreen() }
        composable<Events> { EventsScreen() }
        composable<Settings> { SettingsScreen() }
        composable<Help> { HelpScreen() }
        composable<Login> { LoginScreen (onNewAccount = { navController.navigate(NewAccount) })}
        composable<Profile> { UserProfileScreen() }
        composable<NewAccount> { NewAccountScreen() }
    }

    navController.navigate(pageRoute)
}

