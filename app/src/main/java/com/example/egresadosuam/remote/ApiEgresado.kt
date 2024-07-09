package com.example.egresadosuam.remote

data class LoginRequest(
    val email: String,
    val password: String,
)

interface ApiEgresado