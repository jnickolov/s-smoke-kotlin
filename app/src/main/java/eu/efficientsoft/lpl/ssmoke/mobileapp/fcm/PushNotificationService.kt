package eu.efficientsoft.lpl.ssmoke.mobileapp.fcm

import android.util.Log
import android.widget.Toast
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class PushNotificationService : FirebaseMessagingService () {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.v("FCM", "New token: $token")
        Toast.makeText(
            applicationContext,
            "Token received: $token",
            Toast.LENGTH_LONG)
            .show()
    }

    override fun onMessageReceived(msg: RemoteMessage) {
        super.onMessageReceived(msg)
//        Toast.makeText(
//            applicationContext,
//            msg.toString(),
//            //msg.title+"\n"+msg.notification?.body,
//            Toast.LENGTH_LONG)
//            .show()
        Log.v("FCM", "Message from: ${msg.toString()}")


    }
}