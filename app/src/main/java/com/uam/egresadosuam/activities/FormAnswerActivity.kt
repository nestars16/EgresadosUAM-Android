package com.uam.egresadosuam.activities

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.uam.egresadosuam.R
import com.uam.egresadosuam.ui.theme.UAMPrimary
import com.uam.egresadosuam.ui.theme.UAMPrimaryForeground
import com.uam.egresadosuam.viewmodel.FormAnswerViewModel
import com.uam.egresadosuam.viewmodel.FormAnswerViewModelFactory

@Composable
fun FormAnswerActivity(
    navController: NavController,
    formId: String,
    snackbarHostState: SnackbarHostState
) {

    val context = LocalContext.current
    val formAnswerViewModel = viewModel(
        modelClass = FormAnswerViewModel::class.java,
        factory = FormAnswerViewModelFactory(context, formId, navController, snackbarHostState)
    )
    Scaffold(
        contentWindowInsets = WindowInsets.navigationBars,
        modifier = Modifier
            .fillMaxSize()
            .background(UAMPrimaryForeground),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {},
                containerColor = UAMPrimaryForeground,
                contentColor = Color.White
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.Send,
                    contentDescription = stringResource(id = R.string.addQuestion)
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(UAMPrimary),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {

        }
    }
}