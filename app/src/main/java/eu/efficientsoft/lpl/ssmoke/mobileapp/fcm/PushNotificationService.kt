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
    }

    override fun onMessageReceived(msg: RemoteMessage) {
        super.onMessageReceived(msg)
        val s = "${msg.data.get("title")}\n${msg.data.get("message")}"
        val data = msg.data;
        val eventCode = data["code"]?.toInt() ?: 0
        val commandCode = if (eventCode == 100) 101 else 0
        val detectorId = data["detectorId"]?.toInt() ?: 0
        ToastManager.showToast(s, false, commandCode = commandCode, detectorId =detectorId);
        Log.v("FCM", "Message from: ${msg.toString()}")
        Log.v("FCM", "DATA length: ${data.size}")
        data.forEach { k, v ->
            Log.v("", "    $k -> $v")
        }


    }
}