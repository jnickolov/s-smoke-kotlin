package eu.efficientsoft.lpl.ssmoke.mobileapp.http

import eu.efficientsoft.lpl.ssmoke.mobileapp.util.ErrorTag

enum class NetworkError : ErrorTag {
    REQUEST_TIMEOUT,
    BAD_REQUEST,
    UNAUTHORIZED,
    CONFLICT,
    TOO_MANY_REQUESTS,
    NO_INTERNET,
    PAYLOAD_TOO_LARGE,
    SERVER_ERROR,
    SERIALIZATION,
    UNKNOWN;
}
