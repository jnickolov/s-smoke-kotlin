package eu.efficientsoft.lpl.ssmoke.mobileapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.Firebase
import com.google.firebase.messaging.messaging
import eu.efficientsoft.lpl.ssmoke.mobileapp.app.SSmokeApp
import eu.efficientsoft.lpl.ssmoke.mobileapp.data.DataStoreRepository
import eu.efficientsoft.lpl.ssmoke.mobileapp.data.createDataStore
import eu.efficientsoft.lpl.ssmoke.mobileapp.data.dataStoreRepository
import eu.efficientsoft.lpl.ssmoke.mobileapp.domain.SSmokeAppStatusViewModel
import eu.efficientsoft.lpl.ssmoke.mobileapp.domain.SSmokeEventsViewModel
import eu.efficientsoft.lpl.ssmoke.mobileapp.domain.SSmokeI18nViewModel
import eu.efficientsoft.lpl.ssmoke.mobileapp.domain.SSmokeUserViewModel
import eu.efficientsoft.lpl.ssmoke.mobileapp.ui.theme.SSmokeMobileAppTheme
import eu.efficientsoft.lpl.ssmoke.mobileapp.util.ToastManager


class MainActivity : ComponentActivity() {
    //val dataStoreRepository = DataStoreRepository(createDataStore(this))

    val i18nViewModel: SSmokeI18nViewModel by viewModels ()
    val userViewModel: SSmokeUserViewModel by viewModels()
    val eventsViewModel: SSmokeEventsViewModel by viewModels()
    //val appStatus: SSmokeAppStatusViewModel by viewModels();



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dataStoreRepository = DataStoreRepository (createDataStore(this))

//        Firebase.messaging.subscribeToTopic("test-alarm")
//            .addOnCompleteListener{ task ->
//                val msg = if(task.isSuccessful) "Subscribed" else "Subscription failed"
//                Log.v("FCM", msg)
//                //Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
//                ToastManager.showToast(msg, false)
//            }

        setContent {
            SSmokeMobileAppTheme {
                LaunchedEffect(i18nViewModel.lang) {
                    //i18nViewModel.loadI18n()
                    Log.v("LAUNCHED EFFECT", "I18N key changed: ${i18nViewModel.lang}")
                }
                SSmokeApp(
                    i18nViewModel,
                    userViewModel,
                    eventsViewModel)
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
