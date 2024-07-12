package com.uam.egresadosuam.remote

import com.uam.egresadosuam.model.Form
import com.uam.egresadosuam.model.RequestResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiForm {
    @GET("/form/{id}")
    suspend fun getForm(@Path("id") id: String): RequestResponse<Form>

    @GET("/form/all/unanswered")
    suspend fun getAllUnansweredForms(): RequestResponse<List<Form>>

    @PUT("/form/save")
    suspend fun updateForm(@Body form: Form)
}