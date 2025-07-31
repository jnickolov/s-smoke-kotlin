package eu.efficientsoft.lpl.ssmoke.mobileapp.http

import eu.efficientsoft.lpl.ssmoke.mobileapp.util.Result
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.head
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.setBody
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException

class EventHttpConnector()  : SSmokeHttpClientBase () {

    //http://localhost:8088/api/pro/detector-naming/v1/events?user=jojo
    suspend fun loadEventsForUser (username: String) : Result<List<EventNamingDto>, NetworkError> {
        val response = try {
            val urlString = "$serverHost$namingPath/events"
            httpClient.get(urlString) {
                header("Authorization", "Bearer s-smoke")
                header("Content-type", "application/json")
                parameter("user", username)
            }
        } catch (_: UnresolvedAddressException) {
            return Result.Error(NetworkError.NO_INTERNET)
        } catch (e: SerializationException) {
            e.printStackTrace()
            return Result.Error(NetworkError.SERIALIZATION)
        }

        return resolveResult (response.status.value, response.body())
    }
    //http://localhost:8088/api/pro/detector-naming/v1/infire?user=jojo

    suspend fun loadFireAlarmsForUser (username: String) : Result<List<DetectorGroupNamingDto>, NetworkError> {
        val response = try {
            val urlString = "$serverHost$namingPath/infire"
            httpClient.get(urlString) {
                header("Authorization", "Bearer s-smoke")
                header("Content-type", "application/json")
                parameter("user", username)
            }
        } catch (_: UnresolvedAddressException) {
            return Result.Error(NetworkError.NO_INTERNET)
        } catch (e: SerializationException) {
            e.printStackTrace()
            return Result.Error(NetworkError.SERIALIZATION)
        }

        return resolveResult (response.status.value, response.body())
    }


}

@Serializable
data class EventNamingDto (
    val eventName: String,
    val detectorName: String,val sensorName: String,
    val timestamp: String
)

@Serializable
data class DetectorEventReportDto (
    val eventName: String,
    val detectorName: String,
    val sensorName: String,
    val timestamp: String
)
