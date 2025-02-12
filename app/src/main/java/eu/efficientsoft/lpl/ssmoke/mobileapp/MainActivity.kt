package eu.efficientsoft.lpl.ssmoke.mobileapp

import SSmokeApp
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import eu.efficientsoft.lpl.ssmoke.mobileapp.ui.theme.SSmokeMobileAppTheme

import android.util.Log
import androidx.compose.ui.unit.dp
import eu.efficientsoft.lpl.ssmoke.mobileapp.app.SSmokeTopBar

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SSmokeMobileAppTheme {
                Scaffold (
                    topBar = { SSmokeTopBar() },

                ) {
                    Log.i("tag", "padding values = $it")
                    //SSmokeApp (modifier = Modifier.padding(it))
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