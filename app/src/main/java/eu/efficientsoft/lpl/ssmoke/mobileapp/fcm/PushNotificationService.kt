package eu.efficientsoft.lpl.ssmoke.mobileapp.fcm

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class PushNotificationService : FirebaseMessagingService () {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.v("FCM", "New token: $token")
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.v("FCM", "Message: ${message.data["from"]}")
    }
}