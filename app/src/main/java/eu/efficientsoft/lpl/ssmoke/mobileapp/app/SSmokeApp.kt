package eu.efficientsoft.lpl.ssmoke.mobileapp.app

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import eu.efficientsoft.lpl.ssmoke.mobileapp.domain.I18nEvent
import eu.efficientsoft.lpl.ssmoke.mobileapp.domain.SSmokeI18nViewModel
import eu.efficientsoft.lpl.ssmoke.mobileapp.features.DevicesScreen
import eu.efficientsoft.lpl.ssmoke.mobileapp.features.EventsScreen
import eu.efficientsoft.lpl.ssmoke.mobileapp.features.HelpScreen
import eu.efficientsoft.lpl.ssmoke.mobileapp.features.HomeScreen
import eu.efficientsoft.lpl.ssmoke.mobileapp.features.I18n
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
    val pageRoute: MutableState<Any> = remember { mutableStateOf (Home) }

    val i18nViewModel = viewModel<SSmokeI18nViewModel>()
    val i18n by i18nViewModel.state.collectAsState()

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

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
                i18n = i18n,
                drawerState = drawerState,
                onSelect = { navId ->
                    scope.launch {drawerState.close()}
                    pageRoute.value = navId
                }
            ) {
                //Log.i("SSmokeDrawer---------------", "------------SSmokeDrawer: code = ${i18n?.code}")
                SSmokeNavigationScreen(pageRoute.value, i18n, action = i18nViewModel::loadI18n)
            }
        }
    }
}

@Composable fun SSmokeNavigationScreen (route: Any, i18n: I18n?, action: (String) -> Unit ) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Home) {
        composable<Home> { HomeScreen(i18n = i18n, changeI18n = action) }
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
