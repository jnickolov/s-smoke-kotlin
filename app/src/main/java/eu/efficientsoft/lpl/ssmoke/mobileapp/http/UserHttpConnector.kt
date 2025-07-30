package eu.efficientsoft.lpl.ssmoke.mobileapp.http

import eu.efficientsoft.lpl.ssmoke.mobileapp.domain.SSmokeUserState
import io.ktor.client.HttpClient
import io.ktor.client.request.head
import io.ktor.client.request.header
import io.ktor.client.request.setBody
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.serialization.SerializationException
import eu.efficientsoft.lpl.ssmoke.mobileapp.util.*
import io.ktor.client.call.body
import io.ktor.client.request.post
import kotlinx.serialization.Serializable

class UserHttpConnector()  : SSmokeHttpClientBase () {

    suspend fun usernameExists (username: String): Result<Boolean, NetworkError> {
        val response = try {
            val urlString = "$serverHost$userPath"
            httpClient.head (urlString) {
                setBody (UsernameDto (username))
                header("Authorization", "Bearer s-smoke")
                header("Content-type", "application/json")
            }

        } catch(_: UnresolvedAddressException) {
            return Result.Error(NetworkError.NO_INTERNET)
        } catch(e: SerializationException) {
            e.printStackTrace()
            return Result.Error(NetworkError.SERIALIZATION)
        }
        return when (response.status.value) {
            200 ->  Result.Success(true)
            404 ->  Result.Success(false)
            else -> Result.Error(NetworkError.UNAUTHORIZED)
        }
    }

    suspend fun loginAttempt (username: String, password: String): Result<SSmokeUserState?, NetworkError> {
        val response = try {
            val urlString = "$serverHost$loginPath"
            httpClient.post (urlString) {
                header("Content-type", "application/json")
                setBody (SignInDto (username, pwd=password))
            }

        } catch(_: UnresolvedAddressException) {
            return Result.Error(NetworkError.NO_INTERNET)
        } catch(e: SerializationException) {
            e.printStackTrace()
            return Result.Error(NetworkError.SERIALIZATION)
        }
        return when (response.status.value) {
            200 ->  Result.Success(response.body())
            404 ->  Result.Success(null)
            else -> Result.Error(NetworkError.UNAUTHORIZED)
        }
    }
}

@Serializable
data class UsernameDto (val username: String)

@Serializable
data class SignInDto (
    val unm: String,
    val pwd: String
)
