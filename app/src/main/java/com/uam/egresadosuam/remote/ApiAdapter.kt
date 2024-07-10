package com.uam.egresadosuam.remote

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.uam.egresadosuam.model.RequestResponse
import com.uam.egresadosuam.repository.UserCredentialsRepository
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class AuthInterceptor(private val userCredentialsRepository: UserCredentialsRepository) :
    Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newRequestBuilder = request.newBuilder()

        // Fetch the JWT token from DataStore
        val jwtToken = runBlocking { userCredentialsRepository.jwt.firstOrNull() }

        // If the JWT token exists, add it to the Authorization header
        jwtToken?.let {
            newRequestBuilder.addHeader("Authorization", "Bearer $it")
        }

        return chain.proceed(newRequestBuilder.build())
    }
}

class RequestResponseDeserializer<T> : JsonDeserializer<RequestResponse<T>> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): RequestResponse<T> {
        val jsonObject = json.asJsonObject
        val status = jsonObject.get("status").asString
        return if (status == "success") {
            val dataElement = jsonObject.get("data")
            val dataType = (typeOfT as ParameterizedType).actualTypeArguments[0]
            val data: T = context.deserialize(dataElement, dataType)
            RequestResponse.Success(status, data)
        } else {
            val message = jsonObject.get("message").asString
            RequestResponse.Error(status, message)
        }
    }
}

object ApiAdapter {
    private const val BASE_URL = "http://10.0.1.3:8080"
    fun getInstance(userCredentialsRepository: UserCredentialsRepository): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(
                GsonConverterFactory.create(
                    provideRequestResponseGsonConverterAdapter()
                )
            )
            .client(provideOkHttpClient(userCredentialsRepository))
            .build()
    }

    private fun provideOkHttpClient(userCredentialsRepository: UserCredentialsRepository): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(userCredentialsRepository))
            .build()
    }

    private fun provideRequestResponseGsonConverterAdapter(): Gson {
        val gsonBuilder = GsonBuilder()

        gsonBuilder.registerTypeAdapter(
            RequestResponse::class.java,
            RequestResponseDeserializer<Any>()
        )
        return gsonBuilder.create()
    }
}