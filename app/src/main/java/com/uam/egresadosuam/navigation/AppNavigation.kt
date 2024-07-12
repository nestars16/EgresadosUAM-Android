package com.uam.egresadosuam.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.uam.egresadosuam.activities.FormAnswerActivity
import com.uam.egresadosuam.activities.FormListActivity
import com.uam.egresadosuam.activities.LoginActivity
import kotlinx.serialization.Serializable

@Serializable
object Login

@Serializable
object FormList

@Serializable
data class FormAnswer(val id: String)

@Composable
fun AppNavigation(snackbarHostState: SnackbarHostState) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Login) {

        composable<Login> {
            LoginActivity(
                snackbarHostState = snackbarHostState,
                navController = navController
            )
        }
        composable<FormAnswer> {
            val form = it.toRoute<FormAnswer>()

            FormAnswerActivity(
                navController = navController,
                formId = form.id,
                snackbarHostState = snackbarHostState,
            )

        }

        composable<FormList> {
            FormListActivity(navController = navController, snackbarHostState = snackbarHostState)
        }
    }
}