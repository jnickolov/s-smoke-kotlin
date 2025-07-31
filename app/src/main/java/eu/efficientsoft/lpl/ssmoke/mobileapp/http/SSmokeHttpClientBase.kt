package eu.efficientsoft.lpl.ssmoke.mobileapp.http

import eu.efficientsoft.lpl.ssmoke.mobileapp.util.Result
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttpConfig
import io.ktor.client.engine.okhttp.OkHttpEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

//val serverHost = "http://s-smoke.com:8088"
val serverHost = "http://10.0.2.2:8088"

val userPath = "/api/m/v1/users"
val loginPath = "/api/m/v1/login"
val i18nPath = "/api/m/v1/i18n"
//val createAccountPath = "/api/m/v1/users"
val detectorPath = "/api/pro/m/v1/detectors"
val namingPath = "/api/pro/detector-naming/v1"
//val serverHost = "http://10.0.2.2:8088"
///api/pro/detectors/v1/$detId/command
///api/pro/detectors/v1/$detId/command

//val userHttpConnector = UserHttpConnector();
//val i18nHttpConnector = I18nHttpConnector();


//private fun createHttpClient (): HttpClient {
//    return SSmokeHttpClientBase.createHttpClient();
//}

open class SSmokeHttpClientBase {
    protected val httpClient = createHttpClient();

    protected fun <T>resolveResult (status: Int, body: T) : Result<T, NetworkError> {
        return when (status) {
            in 200..299 -> Result.Success(body)
            401 -> Result.Error(NetworkError.UNAUTHORIZED)
            409 -> Result.Error(NetworkError.CONFLICT)
            408 -> Result.Error(NetworkError.REQUEST_TIMEOUT)
            413 -> Result.Error(NetworkError.PAYLOAD_TOO_LARGE)
            in 500..599 -> Result.Error(NetworkError.SERVER_ERROR)
            else -> Result.Error(NetworkError.UNKNOWN)
        }

    }

    companion object
}

private fun SSmokeHttpClientBase.Companion.createHttpClient(): HttpClient {
    var ssmokeToken: String = "s-smoke"

    val engine = OkHttpEngine(OkHttpConfig())
    return HttpClient (engine) {
        defaultRequest {
            url {
                protocol = URLProtocol.HTTP
                header("Authorization", "Bearer $ssmokeToken")

                host = serverHost
            }
            //header("Autorization", "Bearer $accessToken")
        }
        install (Logging) {
            level = LogLevel.ALL
        }
        install (ContentNegotiation) {
            json(
                json = Json {
                    ignoreUnknownKeys = true
                }
            )
        }
        //...?????
    }
}



@Serializable
data class UserAccountDto(
    val username: String,
    val parentUsername: String? = null,
    val name: String,
    val email: String? = null,
    val language: String = "BG",
    val countryCode: String = "BG",
    val timezone: Int = 2,
    val password: String
)

@Serializable
data class DetectorNamingDto (
    val detectorId: Int,
    val name: String,
    val info: String
)

@Serializable
data class SensorNamingDto (
    val mac: String,
    val name: String,
    val info: String
)

@Serializable
data class DetectorGroupNamingDto (
    val detectorId: Int,
    val name: String,
    val info: String,
    val sensors: List<SensorNamingDto>
)

@Serializable
data class CommandDto (
    val command_id: Int
)

//@Serializable
//data class SensorNnamingDto {
//    var mac: String,
//    var name: String,
//    var info: String) {
//}

//@Serializable
//data class DetectorGroupNamingDto (
//    val detectorId: Int,
//    val name: String,
//    val info: String,
//    val sensors: MutableList<SensorNamingDto>
//)
