package com.uam.egresadosuam.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.uam.egresadosuam.activities.LoginActivity
import kotlinx.serialization.Serializable

@Serializable
object Login

@Composable
fun AppNavigation(scaffoldPaddingValues: PaddingValues, snackbarHostState: SnackbarHostState) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Login) {
        composable<Login> {
            LoginActivity(paddingValues = scaffoldPaddingValues, snackbarHostState, navController)
        }
    }
}