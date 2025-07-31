package eu.efficientsoft.lpl.ssmoke.mobileapp.app

import android.util.Log
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import eu.efficientsoft.lpl.ssmoke.mobileapp.domain.SSmokeEventsViewModel
import eu.efficientsoft.lpl.ssmoke.mobileapp.domain.SSmokeI18nViewModel
import eu.efficientsoft.lpl.ssmoke.mobileapp.domain.SSmokeUserViewModel
import eu.efficientsoft.lpl.ssmoke.mobileapp.util.ToastMessage
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
fun SSmokeApp(
    navController: NavController,
    i18nViewModel: SSmokeI18nViewModel,
    userViewModel: SSmokeUserViewModel,
    eventsViewModel: SSmokeEventsViewModel,
    onPageSelected: (navController: NavController, page: Any) -> Unit
    ) {
    val pageRoute: MutableState<Any> = remember { mutableStateOf(Home) }

    val i18n by i18nViewModel.i18n.collectAsState()
    val lang = i18nViewModel.lang

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()



    LaunchedEffect(lang) {
        //Log.d("LAUNCH EFFECT", "Lang: $lang, wait 3000")
        //TODO: select real language
//        if (lang != null) {
//            //i18nViewModel.saveInDataStore()
//            i18nViewModel.loadI18n(lang)
//        }
    }

    Scaffold(
        topBar = {
            SSmokeTopBar(
                onMenuClick = {
                    Log.i("Navigation Menu Button", "Opening drawer")
                    scope.launch { drawerState.open() }
                },
                onActionClick = {}
            )
        },
    ) {
        Surface(modifier = Modifier.fillMaxSize().padding(it)) {
            SSmokeDrawer(
                i18n = i18n,
                drawerState = drawerState,
                onSelect = { navId ->
                    Log.d(
                        "Drawer navigation:",
                        "Drawer navigation: id = ${navId}, ${navId.toString()}"
                    )
                    scope.launch {
                        drawerState.close()
                    }
                    // TOKA NE STAVA? onPageSelected(navId)
                    pageRoute.value = navId
                }
            ) {
                //Log.i("SSmokeDrawer---------------", "------------SSmokeDrawer: code = ${i18n?.code}")
                Log.v("NAV", "navigate to: ${pageRoute.value.toString()}")

                SSmokeNavigationScreen(
                    navController,
                    route = pageRoute.value,
                    lang = lang,
                    i18n = i18n,
                    i18nViewModel,
                    userViewModel,
                    eventsViewModel,

                    {
                        pageRoute.value = it;
                        onPageSelected(navController, it);
                    }
                )
            }
            ToastMessage(modifier = Modifier.fillMaxWidth().padding(12.dp)
                //.wrapContentWidth(Alignment.CenterHorizontally)
                .wrapContentHeight(Alignment.Bottom))
        }
    }

}



