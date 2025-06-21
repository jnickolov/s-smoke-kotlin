package eu.efficientsoft.lpl.ssmoke.mobileapp.http.repository

import android.util.Log
import eu.efficientsoft.lpl.ssmoke.mobileapp.features.I18n
import eu.efficientsoft.lpl.ssmoke.mobileapp.http.serverConnector

object I18nRepository {
    //private val i18nEndpoint = "$mobileApiUrl/i18n"
    private const val i18nEndpoint = "/i18n"

    suspend fun loadI18n (
        lang: String,
        action: (i18n: I18n) -> Unit
    ) {
        Log.v("I18N", "Loading........ lang = $lang")
        serverConnector.getI18n (lang)  { status, res ->
                Log.v("I18nREPO", "loading status: $status")
                if (status == 200)
                    action (res)
            }

        }
//        val result = serverConnector.get<I18nResponseDao>(url = "$i18nEndpoint/$lang", request = I18nRequestDao(lang))
//        when (result) {
//            is Result.Success -> action (result.data.i18n)on
//            is Result.Error -> {
//                Log.i("I18nRepository::loadi18n", "Error: ${result.error.name}")
//                // TODO
//            }
//        }
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
//                        }
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
//    }

}
