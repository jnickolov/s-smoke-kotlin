package eu.efficientsoft.lpl.ssmoke.mobileapp.app

import android.graphics.drawable.Icon
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DrawerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import eu.efficientsoft.lpl.ssmoke.mobileapp.screens.I18n
import eu.efficientsoft.lpl.ssmoke.mobileapp.ui.theme.LPLBlue

private data class NavigationData (val id: Any, val label: String, val description: String? = null, val icon: Icon? = null, var selected: Boolean = false, val onClick: () -> Unit = {})


private val navItems = listOf(
    NavigationData (Events, label = "m_mnu_reports", description = "m_mnu_reports_hint", onClick = { Log.i("Navigation click","Events"); }),
    NavigationData (FireAlarms, label = "m_mnu_fire_alarms", description = "m_mnu_fire_alarms", onClick = { Log.i("Navigation click","Fire alarms"); }),
    //null,
    //NavigationData (Devices, label = "m_mnu_detector", description = "m_mnu_detector_hint", onClick = { Log.i("Navigation click","Detectors")}),
    NavigationData (Notifications, label = "m_mnu_fcm", description = "m_mnu_fcm_hint", onClick = { Log.i("Navigation click","Messages")}),
    //null,
    //NavigationData (Profile, label = "m_mnu_user", description = "m_mnu_user_hint", onClick = { Log.i("Navigation click","Profile")}),
    //NavigationData (Settings, label = "m_mnu_config", description = "m_mnu_config_hint", onClick = { Log.i("Navigation click","Profile")}),
    //null,
    //NavigationData (Help, label = "m_mnu_help", description = "m_mnu_help_hint", onClick = { Log.i("Navigation click","Help") }),
    //null,
    NavigationData (Login, label = "m_mnu_logout", description = "m_mnu_logout_hint", onClick = {}),
)

@Composable
private fun SSmokeDrawerItemLabel(nav: NavigationData, i18n: I18n?) {
    val hintFontSize = LocalTextStyle.current.fontSize * 0.7
    val titleFontSize = LocalTextStyle.current.fontSize * 1.0

    if (i18n == null) {
        Log.i("S-SMOKE DRAWER", "I18N IS NULLLLLLL")
    }

    val label = i18n?.prop(nav.label)
    val descr = i18n?.prop(nav.description ?: "")
    val s = buildAnnotatedString {
        withStyle(
            style = ParagraphStyle(
                lineHeight = 14.sp,
                lineBreak = LineBreak.Simple,
                )
        ) {
            withStyle(
                style = SpanStyle(
                    fontSize = titleFontSize,
                    color = LPLBlue
                )
            ) {
                append("$label\n")
            }
            withStyle(
                style = SpanStyle(
                    fontSize = hintFontSize,
                    color = Color(0xFF808080)
                )
            ) {
                append(descr?:"")
            }
        }
    }
    Text (text = s)
}

@Composable
fun SSmokeDrawer (
    i18n: I18n?,
    drawerState: DrawerState,
    onSelect: (navId: Any) -> Unit,
    content: @Composable () -> Unit,
) {

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                SSmokeDrawerSheet(i18n, onSelect)
            }
        },
        content = content
    )
}

@Composable
private fun SSmokeDrawerSheet(
    i18n: I18n?,
    onClick: (navId: Any) -> Unit
) {
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top,

    ) {
        Spacer(Modifier.height(12.dp))
        navItems.forEach {
            NavigationDrawerItem(
                label = { SSmokeDrawerItemLabel(i18n = i18n, nav = it) },
                selected = false,
                onClick = {onClick (it.id)}
            )
        }
    }
}

