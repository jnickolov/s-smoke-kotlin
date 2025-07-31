package eu.efficientsoft.lpl.ssmoke.mobileapp.app

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import eu.efficientsoft.lpl.ssmoke.mobileapp.domain.SSmokeEventsViewModel
import eu.efficientsoft.lpl.ssmoke.mobileapp.domain.SSmokeI18nViewModel
import eu.efficientsoft.lpl.ssmoke.mobileapp.domain.SSmokeUserViewModel
import eu.efficientsoft.lpl.ssmoke.mobileapp.screens.DevicesScreen
import eu.efficientsoft.lpl.ssmoke.mobileapp.screens.EventsScreen
import eu.efficientsoft.lpl.ssmoke.mobileapp.screens.HelpScreen
import eu.efficientsoft.lpl.ssmoke.mobileapp.screens.HomeScreen
import eu.efficientsoft.lpl.ssmoke.mobileapp.screens.I18n
import eu.efficientsoft.lpl.ssmoke.mobileapp.screens.LoginScreen
import eu.efficientsoft.lpl.ssmoke.mobileapp.screens.NewAccountScreen
import eu.efficientsoft.lpl.ssmoke.mobileapp.screens.NotificationsScreen
import eu.efficientsoft.lpl.ssmoke.mobileapp.screens.SettingsScreen
import eu.efficientsoft.lpl.ssmoke.mobileapp.screens.UserProfileScreen
import eu.efficientsoft.lpl.ssmoke.mobileapp.util.ToastManager

@Composable
fun SSmokeNavigationScreen (
    route: Any,
    lang: String?,
    i18n: I18n?,
    i18nViewModel: SSmokeI18nViewModel,
    userViewModel: SSmokeUserViewModel,
    eventsViewModel: SSmokeEventsViewModel,
    onRouteChange: (Any) -> Unit
    //loginAttempt: (user: String, pass: String, onSuccess: ()->Unit, onFail: @Composable ()->Unit)->Unit
) {
    val navController = rememberNavController()

//    fun toLoginScreen() {
//        Log.v("NAV", "to login screen")
//        navController.navigate(Login)
//    }
//
    fun navigateTo(toPage: Any) {
        navController.navigate(toPage) {
            //popUpTo(Home)
        }
        onRouteChange(toPage)
    }

    @Composable
    fun showToast(string: String) {
        //Toast.makeText(LocalContext.current, "Неуспешен логин. Опитайте пак", Toast.LENGTH_LONG).show()
        ToastManager.showToast("Неуспешен логин. Опитайте пак", false)
    }

    NavHost(navController = navController, startDestination = Home) {
        composable<Home> { HomeScreen(eventsVM = eventsViewModel, userVM = userViewModel) }
        composable<Devices> { DevicesScreen(i18n = i18n) }
        composable<Notifications> { NotificationsScreen(i18n = i18n, userVM = userViewModel,
            onAlarmSelected = { userViewModel.registerForAlarms() },
            onAlarmDeselected = { userViewModel.unregisterForAlarms() },
            onMessageSelected = { userViewModel.registerForMessages() },
            onMessageDeselected = { userViewModel.unregisterForMessages() },
            ) }
        composable<Events> { EventsScreen(i18n = i18n, user = userViewModel.user, eventsVM = eventsViewModel) }
        composable<Settings> { SettingsScreen(i18n = i18n) }
        composable<Help> { HelpScreen(i18n = i18n) }
        composable<Login> {
//            userViewModel.userState.value = userViewModel.userState.value.copy(isLogged = false)
            LoginScreen(
                i18n = i18n,
                onLogin = { unm, pass ->
                    Log.v("1111 Login...", "$unm/$pass")
                    userViewModel.login(
                        unm,
                        pass,
                        {
                            val b = navController.popBackStack()
                            Log.v("usermodel.login:"," navcontroller: popbackstack: ${b}")
                            Log.v("", "After success: user = $userViewModel.userState.value")
                        })
                },
                onNewAccount = { navController.navigate(NewAccount) })
        }
        composable<Profile> { UserProfileScreen(i18n = i18n) }
        composable<NewAccount> { NewAccountScreen(i18n = i18n) }
    }

    Log.d("Nav. controller", "Is user logged: ${userViewModel.userState.value.isLogged}")
    if (! userViewModel.userState.value.isLogged) {
        navController.navigate(Login)
    } else {
        navController.clearBackStack(Home)  //?????
        navController.navigate (route)
    }


}

