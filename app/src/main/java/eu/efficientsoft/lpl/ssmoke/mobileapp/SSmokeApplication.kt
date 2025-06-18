package eu.efficientsoft.lpl.ssmoke.mobileapp

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob


class SSmokeApplication : Application() {
        override fun onCreate() {
            super.onCreate()
//            startKoin {
//                // Log Koin into Android logger
//                androidLogger()
//                // Reference Android context
//                androidContext(this@MainApplication)
//                //Load Modules
//                modules(interactionModule, networkModule, presentationModule, repositoryModule)
//            }
        }
}
