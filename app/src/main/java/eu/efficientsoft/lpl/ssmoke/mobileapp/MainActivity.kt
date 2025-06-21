package eu.efficientsoft.lpl.ssmoke.mobileapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging

import eu.efficientsoft.lpl.ssmoke.mobileapp.app.SSmokeApp
import eu.efficientsoft.lpl.ssmoke.mobileapp.data.DataStoreRepository
import eu.efficientsoft.lpl.ssmoke.mobileapp.data.createDataStore
import eu.efficientsoft.lpl.ssmoke.mobileapp.data.dataStoreRepository
import eu.efficientsoft.lpl.ssmoke.mobileapp.domain.SSmokeI18nViewModel
import eu.efficientsoft.lpl.ssmoke.mobileapp.domain.SSmokeUserViewModel
import eu.efficientsoft.lpl.ssmoke.mobileapp.ui.theme.SSmokeMobileAppTheme
import kotlin.getValue


class MainActivity : ComponentActivity() {
    //val dataStoreRepository = DataStoreRepository(createDataStore(this))

    val i18nViewModel: SSmokeI18nViewModel by viewModels()
    val userViewModel: SSmokeUserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

         dataStoreRepository = DataStoreRepository (createDataStore(this))


        Firebase.messaging.subscribeToTopic("gogo-common")
            .addOnCompleteListener{ task ->
                val msg = if(task.isSuccessful) "Subscribed" else "Subscription failed"
                Log.v("FCM", msg)
                Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
            }

        setContent {
            SSmokeMobileAppTheme {
                LaunchedEffect(i18nViewModel.lang) {
                    //i18nViewModel.loadI18n()
                    Log.v("LAUNCHED EFFECT", "I18N key changed: ${i18nViewModel.lang}")
                }
                SSmokeApp()
            }

        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        i18nViewModel.saveInBundle(outState)
        userViewModel.saveInBundle (outState)


    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        i18nViewModel.loadFromBundle(savedInstanceState)

    }
}
