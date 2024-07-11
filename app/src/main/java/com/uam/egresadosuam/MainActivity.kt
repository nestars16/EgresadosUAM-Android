package com.uam.egresadosuam


import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.google.protobuf.InvalidProtocolBufferException
import com.uam.egresadosuam.navigation.AppNavigation
import com.uam.egresadosuam.ui.theme.EgresadosUAMTheme
import java.io.InputStream
import java.io.OutputStream

object UserCredentialsSerializer :
    Serializer<com.uam.egresadosuam.model.DataStore.UserCredentials> {
    override val defaultValue =
        com.uam.egresadosuam.model.DataStore.UserCredentials.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): com.uam.egresadosuam.model.DataStore.UserCredentials {
        try {
            return com.uam.egresadosuam.model.DataStore.UserCredentials.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(
        t: com.uam.egresadosuam.model.DataStore.UserCredentials,
        output: OutputStream
    ) = t.writeTo(output)
}

val Context.dataStore: DataStore<com.uam.egresadosuam.model.DataStore.UserCredentials> by dataStore(
    fileName = "dataStore.pb", serializer = UserCredentialsSerializer
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EgresadosUAMTheme {
                val snackbarHostState = remember { SnackbarHostState() }
                AppNavigation(snackbarHostState)
            }
        }
    }
}