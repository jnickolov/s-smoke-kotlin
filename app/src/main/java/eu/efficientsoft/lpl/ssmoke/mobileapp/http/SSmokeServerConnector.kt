package eu.efficientsoft.lpl.ssmoke.mobileapp.http

import eu.efficientsoft.lpl.ssmoke.mobileapp.data.SSmokeDAO
import eu.efficientsoft.lpl.ssmoke.mobileapp.sSmokeCoroutineScope
import eu.efficientsoft.lpl.ssmoke.mobileapp.util.AppSettings.sSmokeCoroutineScope
import eu.efficientsoft.lpl.ssmoke.mobileapp.util.Result
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttpConfig
import io.ktor.client.engine.okhttp.OkHttpEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get
import io.ktor.client.request.setBody
import io.ktor.http.URLProtocol
import io.ktor.http.path
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.launch
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json

private const val serverUrl: String = "http://s-smoke.com:8088"
private const val mobileApiPath: String = "api/m/v1"



val serverConnector = SSmokeServerConnection()

class SSmokeServerConnection {
    val httpClient = createHttpClient()

    var accessToken = "s-smoke"

    inline fun <reified T>get (url: String, request: SSmokeDAO.SSmokeRequestDAO<T>): Result<T, NetworkError> {
        //val result: T

        var result: Result<T, NetworkError> = Result.Error(NetworkError.UNKNOWN)

        sSmokeCoroutineScope.launch {
            result = try {
                val response = httpClient.get(
                    urlString = url

                ) {
                    setAttributes {
                        bearerAuth(accessToken)
                    }
                    setBody (request.data)
                }

                // update accessToken for next call
                accessToken = response.headers.get("X-Ssmoke-Token") ?: "s-smoke"

                when (response.status.value) {
                    in 200..299 -> Result.Success(response.body<T>())
                    401 -> Result.Error(NetworkError.UNAUTHORIZED)
                    else -> Result.Error(NetworkError.BAD_REQUEST)
                }

            } catch (e: InterruptedException) {
                throw e
            } catch (e: UnresolvedAddressException) {
                Result.Error(NetworkError.NO_INTERNET)
            } catch (e: SerializationException) {
                Result.Error(NetworkError.SERIALIZATION)
            }
        }
        return result
    }

    //val httpClient = serverConnector.httpClient

//        sSmokeCoroutineScope.launch {
//            val res = withContext(Dispatchers.IO) {
//                val result: Result<I18n, NetworkError> = try {
//                    httpClient.get(
//                        urlString = i18nEndpoint + "/$lang"
//                    ) {
//                        setAttributes {
//                            bearerAuth (accessToken)
//                        }
//                    }.let { response ->
//                        when (response.status.value) {
//                            200 -> Result.Success(response.body<I18n>())
//                            400 -> Result.Error(NetworkError.BAD_REQUEST)
//                            else -> Result.Error(NetworkError.UNKNOWN)
//                       }
//                    }
//                } catch (e: UnresolvedAddressException) {
//                    Result.Error(NetworkError.NO_INTERNET)
//                } catch (e: SerializationException) {
//                    Result.Error(NetworkError.SERIALIZATION)
//                }
//
//                action(result)
//            }
//        }






    private fun createHttpClient (): HttpClient {
        val engine = OkHttpEngine(OkHttpConfig())
        return HttpClient (engine) {
            defaultRequest {
                url {
                    protocol = URLProtocol.HTTP
                    host = serverUrl
                    path(mobileApiPath)
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
        }
    }
}
