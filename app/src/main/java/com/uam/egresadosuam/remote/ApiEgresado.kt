package com.uam.egresadosuam.remote

import com.uam.egresadosuam.model.LoginData
import com.uam.egresadosuam.model.LoginRequest
import com.uam.egresadosuam.model.RequestResponse
import retrofit2.http.Body
import retrofit2.http.POST


interface ApiEgresado {
    @POST("/egresado/login")
    suspend fun login(@Body body: LoginRequest): RequestResponse<LoginData>
}