package eu.efficientsoft.lpl.ssmoke.mobileapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import eu.efficientsoft.lpl.ssmoke.mobileapp.app.SSmokeApp
import eu.efficientsoft.lpl.ssmoke.mobileapp.ui.theme.SSmokeMobileAppTheme


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SSmokeMobileAppTheme {
                SSmokeApp()
            }

        }
    }
}
