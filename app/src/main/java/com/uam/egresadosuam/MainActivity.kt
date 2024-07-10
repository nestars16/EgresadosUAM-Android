package com.uam.egresadosuam


import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.google.protobuf.InvalidProtocolBufferException
import com.uam.egresadosuam.model.DataStore.UserCredentials
import com.uam.egresadosuam.navigation.AppNavigation
import com.uam.egresadosuam.ui.theme.EgresadosUAMTheme
import java.io.InputStream
import java.io.OutputStream

object UserCredentialsSerializer : Serializer<UserCredentials> {
    override val defaultValue = UserCredentials.getDefaultInstance()
    override suspend fun readFrom(input: InputStream): UserCredentials {
        try {
            return UserCredentials.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(
        t: UserCredentials,
        output: OutputStream
    ) = t.writeTo(output)
}

val Context.dataStore: DataStore<UserCredentials> by dataStore(
    fileName = "dataStore.pb", serializer = UserCredentialsSerializer
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EgresadosUAMTheme {
                val snackbarHostState = remember { SnackbarHostState() }

                Scaffold(
                    snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
                    contentWindowInsets = WindowInsets.statusBars,
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    AppNavigation(scaffoldPaddingValues = innerPadding, snackbarHostState)
                }
            }
        }
    }
}