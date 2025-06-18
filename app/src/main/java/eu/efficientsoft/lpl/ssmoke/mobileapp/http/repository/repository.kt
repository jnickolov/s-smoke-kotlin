package eu.efficientsoft.lpl.ssmoke.mobileapp.http.repository

import android.util.Log
import eu.efficientsoft.lpl.ssmoke.mobileapp.data.SSmokeRequestDao
import eu.efficientsoft.lpl.ssmoke.mobileapp.data.SSmokeResponseDao
import eu.efficientsoft.lpl.ssmoke.mobileapp.features.I18n
import eu.efficientsoft.lpl.ssmoke.mobileapp.http.serverConnector
import eu.efficientsoft.lpl.ssmoke.mobileapp.util.Result

class I18nRepository() {
    //private val i18nEndpoint = "$mobileApiUrl/i18n"
    private val i18nEndpoint = "/i18n"




    fun loadI18n (
        lang: String,
        action: (i18n: I18n) -> Unit
    ) {

        val result = serverConnector.get<I18nResponseDao>(url = "$i18nEndpoint/$lang", request = I18nRequestDAO(lang))
        when (result) {
            is Result.Success -> action (result.data.i18n)
            is Result.Error -> {
                Log.i("I18nRepository::loadi18n", "Error: ${result.error.name}")
                // TODO
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
    }

}

typealias I18nRequestDao = SSmokeRequestDao<String>
typealias I18nResponseDao = SSmokeResponseDao<I18n>