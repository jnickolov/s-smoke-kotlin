package eu.efficientsoft.lpl.ssmoke.mobileapp.app

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
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
import kotlinx.coroutines.launch
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
fun SSmokeApp() {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val pageRoute: MutableState<Any> = remember { mutableStateOf (Home) }


    Scaffold (
        topBar = { SSmokeTopBar (
            onMenuClick = {
                Log.i("Navigation Menu Button", "Opening drawer")
                scope.launch { drawerState.open() }
            },
            onActionClick = {}
        ) },
    ) {
        Surface(modifier = Modifier.padding(it)) {
            SSmokeDrawer(
                drawerState = drawerState,
                onSelect = { navId ->
                    scope.launch {drawerState.close()}
                    pageRoute.value = navId
                }
            ) {
                SSmokeNavigationScreen(pageRoute.value)
            }
        }
    }
}

@Composable fun SSmokeNavigationScreen (route: Any) {
    val navController = rememberNavController()

    NavHost(navController, startDestination = Home) {
        composable<Home> { HomeScreen() }
        composable<Devices> { DevicesScreen() }
        composable<Notifications> { NotificationsScreen() }
        composable<Events> { EventsScreen() }
        composable<Settings> { SettingsScreen() }
        composable<Help> { HelpScreen() }
        composable<Login> { LoginScreen(onNewAccount = { navController.navigate(NewAccount) }) }
        composable<Profile> { UserProfileScreen() }
        composable<NewAccount> { NewAccountScreen() }
    }

    navController.navigate(route)
}
