package com.uam.egresadosuam.model

data class LoginRequest(
    val email: String,
    val password: String,
)


sealed class RequestResponse<out T> {
    data class Success<out T>(val status: String, val data: T) : RequestResponse<T>()
    data class Error(val status: String, val message: String) : RequestResponse<Nothing>()
}

enum class RequestState {
    Success,
    Failure,
    Pending,
}

data class LoginData(val token: String, val expiresIn: Int)