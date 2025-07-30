package eu.efficientsoft.lpl.ssmoke.mobileapp.fcm

import android.util.Log
import android.widget.Toast
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import eu.efficientsoft.lpl.ssmoke.mobileapp.util.ToastManager

class PushNotificationService : FirebaseMessagingService () {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.v("FCM", "New token: $token")
//        Toast.makeText(
//            applicationContext,
//            "Token received: $token",
//            Toast.LENGTH_LONG)
//            .show()
    }

    override fun onMessageReceived(msg: RemoteMessage) {
        super.onMessageReceived(msg)
        val s = "${msg.data.get("title")}\n${msg.data.get("message")}"
        ToastManager.showToast(s, false);
        Log.v("FCM", "Message from: ${msg.toString()}")
        val data = msg.data;
        Log.v("FCM", "DATA length: ${data.size}")
        data.forEach { k, v ->
            Log.v("", "    $k -> $v")
        }


    }
}