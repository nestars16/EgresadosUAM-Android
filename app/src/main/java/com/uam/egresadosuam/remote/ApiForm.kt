package com.uam.egresadosuam.remote

import com.uam.egresadosuam.model.Form
import com.uam.egresadosuam.model.RequestResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiForm {
    @GET("/form/{id}")
    suspend fun getForm(@Path("id") id: String): RequestResponse<Form>

    @GET("/form/all")
    suspend fun getAllForms(): RequestResponse<List<Form>>
}