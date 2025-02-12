package eu.efficientsoft.lpl.ssmoke.mobileapp.app

import SSmokeApp
import android.graphics.drawable.Icon
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import eu.efficientsoft.lpl.ssmoke.mobileapp.ui.theme.LPLBlue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

private data class NavigationData (val label: String, val description: String? = null, val icon: Icon? = null, var selected: Boolean = false, val onClick: () -> Unit = {})


private val navItems = listOf(
    NavigationData (label = "Събития", description = "Информация за събития в усторйствата и системата с възможност за филтрация", onClick = { Log.i("Navigation click","Events")}),
    //null,
    NavigationData (label = "Детектори", description = "Присъединяване и управление на детекторите", onClick = { Log.i("Navigation click","Detectors")}),
    NavigationData (label = "Съобщения", description = "Управление на съобщенията от детекторите и системата", onClick = { Log.i("Navigation click","Messages")}),
    //null,
    NavigationData (label = "Профил", description = "Управление на персоналните данни", onClick = { Log.i("Navigation click","Profile")}),
    //null,
    NavigationData (label = "Помощ", description = "Пълно описание. За описание на конкретна тема, използвайте бутона в десния горен ъгъл", onClick = { Log.i("Navigation click","Help") }),
    //null,
    NavigationData (label = "Отписване / Logout", description = "Излизане от профила\nЩе бъдете насочени към екрана за свързване", onClick = {}),
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
//private fun SSmokeDrawerItemLabel(nav: NavigationData) : AnnotatedString {
private fun SSmokeDrawerItemLabel(nav: NavigationData) {
    val hintFontSize = LocalTextStyle.current.fontSize * 0.7
    val titleFontSize = LocalTextStyle.current.fontSize * 1.0

    val descr = nav.description ?: "";
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
                append("${nav.label}\n")
            }
            withStyle(
                style = SpanStyle(
                    fontSize = hintFontSize,
                    color = Color(0xFF808080)
                )
            ) {
                append("$descr")
            }
        }
    }
    Text (text = s) // ne pomaga: Modifier.height(IntrinsicSize.Max))
}

//    Column(
//        modifier = Modifier.fillMaxSize().padding(10.dp),
//        //.requiredHeight(100.dp),
//        ) {
//        Text(text = nav.label, fontSize = titleFontSize)
//
//        val descr = nav.description ?: ""
//        Text(text = descr,
//            modifier = Modifier.fillMaxHeight(),
//            fontSize = hintFontSize,
//            style = TextStyle.Default.copy(lineBreak = LineBreak.Simple),
//            maxLines = 3,
//            overflow = TextOverflow.Ellipsis)
//    }
//}

@Composable
fun SSmokeDrawer (drawerState: DrawerState, scope: CoroutineScope) {
    var selectedItem by remember { mutableStateOf(navItems[0]) }
    ModalNavigationDrawer(
        modifier = Modifier.padding(0.dp, 64.dp, 0.dp, 0.dp),
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                SSmokeDrawerSheet(drawerState, selectedItem, scope)
            }
        },
        content = {
             SSmokeApp()
        }
    )
}

@Composable
private fun SSmokeDrawerSheet(drawerState: DrawerState?, selectedItem: NavigationData?, scope: CoroutineScope?) {
    Column(
        //modifier = Modifier.verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top,

    ) {
        Spacer(Modifier.height(12.dp))
        navItems.forEach {
            if (it == null) {
                Divider(thickness = 1.dp, modifier = Modifier.padding(0.dp, 10.dp))
            } else {
                //val lbl = () -> SSmokeDrawerItemLabel(it);
                NavigationDrawerItem(
                    // ne pomaga modifier = Modifier.height(IntrinsicSize.Max),
                    //label = { Text(text = SSmokeDrawerItemLabel(it), minLines = 2, maxLines = 4) },
                    label = { SSmokeDrawerItemLabel(it) },
                    selected = it.selected,
                    onClick = {
                        selectedItem?.selected = false;
                        it.selected = true
                        scope?.launch {drawerState?.close()}
                        it.onClick()
                    }
                )
            }
        }
    }
}

@Composable
fun testDrawerSheet () {
    //SSmokeDrawerSheet(null, null, null)
    SSmokeDrawerItemLabel(nav = navItems[0]!!)
}

