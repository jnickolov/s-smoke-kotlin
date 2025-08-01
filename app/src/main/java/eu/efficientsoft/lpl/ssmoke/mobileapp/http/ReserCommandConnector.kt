package eu.efficientsoft.lpl.ssmoke.mobileapp.http

import android.util.Log
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlinx.coroutines.runBlocking

object resetCommandConnector {
    val connector = ResetCommandConnector()
    fun sendReset (detectorId: Int) {
        runBlocking {
            connector.sendResetCommand(detectorId);
        }
    }
}

class ResetCommandConnector()  : SSmokeHttpClientBase () {

    suspend fun sendResetCommand(detId: Int) {
        //delay(1000L);

        val response = try {
            httpClient.post(urlString = "$serverHost/api/pro/detectors/v1/$detId/command") {
                header("Authorization", "Bearer s-smoke")
                header("Content-type", "application/json")
                setBody(CommandDto(command_id = 101))
            }
        } catch (e: Exception) {
            Log.d("", "Send reset: ${e.message}")
        }
    }
}
