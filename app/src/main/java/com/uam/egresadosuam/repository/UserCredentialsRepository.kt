package com.uam.egresadosuam.repository

import android.content.Context
import com.uam.egresadosuam.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserCredentialsRepository(private val context: Context) {
    val jwt: Flow<String> = context.dataStore.data.map { it.jwt }
    suspend fun save(newToken: String) {
        context.dataStore.updateData {
            it.toBuilder().setJwt(newToken).build()
        }
    }
}