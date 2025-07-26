package eu.efficientsoft.lpl.ssmoke.mobileapp.app

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import kotlinx.coroutines.delay
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
    val pageRoute: MutableState<Any> = remember { mutableStateOf (Login) }

    val i18nViewModel = viewModel<SSmokeI18nViewModel>()
    val i18n by i18nViewModel.i18n.collectAsState()
    val lang = i18nViewModel.lang

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    LaunchedEffect(lang) {
        Log.d("LAUNCH EFFECT", "Lang: $lang, wait 3000")
        if (lang != null) {
            //i18nViewModel.saveInDataStore()
            i18nViewModel.loadI18n(lang)
        }
    }

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
                Log.v("NAV", "navigate to: ${pageRoute.value.toString()}")

                SSmokeNavigationScreen(pageRoute.value, lang, i18n, action = { lng ->
                    i18nViewModel.loadI18n (lng)
                })
            }
        }
    }
}

@Composable fun SSmokeNavigationScreen (route: Any, lang: String?, i18n: I18n?, action: (String) -> Unit ) {
    val navController = rememberNavController()

    fun toLoginScreen() {
        Log.v("NAV", "to login screen")
        navController.navigate(Login)
    }

    NavHost(navController = navController, startDestination = Events) {
        composable<Home> { HomeScreen(changeI18n = action) }
        composable<Devices> { DevicesScreen(i18n = i18n) }
        composable<Notifications> { NotificationsScreen(i18n = i18n) }
        composable<Events> { EventsScreen(i18n = i18n) }
        composable<Settings> { SettingsScreen(i18n = i18n) }
        composable<Help> { HelpScreen(i18n = i18n) }
        composable<Login> { LoginScreen(i18n = i18n, onLogin = {},
            onNewAccount = { navController.navigate(NewAccount) }) }
        composable<Profile> { UserProfileScreen(i18n = i18n) }
        composable<NewAccount> { NewAccountScreen(i18n = i18n) }
    }

    Log.v("NAV", "Initial navigation: lang = $lang, i18n: ${i18n?.code} or nulllllll")
    if (lang == null)
        navController.navigate(Home)
//    if (route == Home && lang != null)
//        navController.navigate(Login)
    else
        navController.navigate(route)
}
