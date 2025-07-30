package eu.efficientsoft.lpl.ssmoke.mobileapp.http

import android.util.Log
import eu.efficientsoft.lpl.ssmoke.mobileapp.screens.I18n
import eu.efficientsoft.lpl.ssmoke.mobileapp.util.Result
import io.ktor.client.call.body
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.serialization.SerializationException

//private const val serverUrl: String = "http://s-smoke.com:8088"




class I18nHttpConnector : SSmokeHttpClientBase () {
    //val httpClient = createHttpClient()

    var accessToken = "s-smoke"

    suspend fun getI18n (
        lang: String,
        onSuccess: (status: Int, i18n: I18n) -> Unit) {

            Log.v("CONNECTOR: ","INSIDE LAUNCH")
            try {
                val response = httpClient.get(
                    urlString = "$serverHost$i18nPath/$lang"
                ) {
                    setAttributes {
                        bearerAuth(accessToken)
                    }
                }
                Log.v("HTTP:GET", "finished")
                // update accessToken for next call
                accessToken = response.headers["X-Ssmoke-Token"] ?: "s-smoke"

                when (response.status.value) {
                    in 200..299 -> {
                        //Result.Success(response.body())
                        onSuccess(response.status.value, response.body())
                    }
                    //401 -> onError(401)//Result.Error(NetworkError.UNAUTHORIZED)
                    else -> onError(response.status.value)//Result.Error(NetworkError.BAD_REQUEST)
                }

            } catch (e: InterruptedException) {
                throw e
            } catch (e: UnresolvedAddressException) {
                Result.Error(NetworkError.NO_INTERNET)
                // TODO:
            } catch (e: SerializationException) {
                // TODO
                onError(500)
                Result.Error(NetworkError.SERIALIZATION)
            }
        //}  // end launch
    }

    fun onError(status: Int) {
        if (status == 401) {
            Log.v("HTTP", "Must re-login")
        } else {
            Log.v("HTTP", "ERROR, status $status")
        }
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



//
//    fun loadI18n (langCode: String, action: (Result<I18n, NetworkError>) -> Unit) {
//
//    }
}
