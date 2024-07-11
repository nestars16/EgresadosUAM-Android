package com.uam.egresadosuam.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.uam.egresadosuam.activities.FormAnswerActivity
import com.uam.egresadosuam.activities.LoginActivity
import kotlinx.serialization.Serializable

@Serializable
object Login

@Serializable
data class FormCreate(val id: String)

@Composable
fun AppNavigation(snackbarHostState: SnackbarHostState) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = FormCreate) {
        composable<Login> {
            LoginActivity(
                snackbarHostState = snackbarHostState,
                navController = navController
            )
        }
        composable<FormCreate> {
            val form = it.toRoute<FormCreate>()
            FormAnswerActivity(
                navController = navController,
                formId = form.id,
                snackbarHostState = snackbarHostState,
            )
        }
    }
}