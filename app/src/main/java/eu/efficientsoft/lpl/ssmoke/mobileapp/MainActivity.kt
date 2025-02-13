package eu.efficientsoft.lpl.ssmoke.mobileapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDrawerState
import androidx.compose.ui.Modifier
import eu.efficientsoft.lpl.ssmoke.mobileapp.app.Home
import eu.efficientsoft.lpl.ssmoke.mobileapp.app.SSmokeApp
import eu.efficientsoft.lpl.ssmoke.mobileapp.app.SSmokeTopBar
import eu.efficientsoft.lpl.ssmoke.mobileapp.ui.theme.SSmokeMobileAppTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val drawerState = rememberDrawerState(DrawerValue.Closed)

            SSmokeMobileAppTheme {
                Scaffold (
                    topBar = { SSmokeTopBar (drawerState) },
                    ) {
                    Surface(modifier = Modifier.padding(it)) {
                        SSmokeApp (pageRoute = Home)
                    }
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