package com.uam.egresadosuam.activities

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.uam.egresadosuam.R
import com.uam.egresadosuam.model.RequestState
import com.uam.egresadosuam.navigation.FormList
import com.uam.egresadosuam.ui.theme.LibreFranklin
import com.uam.egresadosuam.ui.theme.UAMPrimary
import com.uam.egresadosuam.ui.theme.UAMPrimaryForeground
import com.uam.egresadosuam.ui.theme.UAMPrimaryForegroundMuted
import com.uam.egresadosuam.viewmodel.LoginViewModel
import com.uam.egresadosuam.viewmodel.LoginViewModelFactory

@Composable
fun LoginActivity(
    snackbarHostState: SnackbarHostState,
    navController: NavController
) {

    val context = LocalContext.current
    val loginViewModel: LoginViewModel =
        viewModel(LoginViewModel::class.java, factory = LoginViewModelFactory(context))

    val state = loginViewModel.state

    var passwordVisible by remember {
        mutableStateOf(false)
    }

    val image = if (passwordVisible)
        painterResource(id = R.drawable.visibility)
    else
        painterResource(id = R.drawable.visibility_off)

    val successMessage = stringResource(id = R.string.loginSuccess)

    LaunchedEffect(key1 = state.status) {
        when (state.status) {
            RequestState.Success -> {
                navController.navigate(FormList)
                snackbarHostState.showSnackbar(
                    message = successMessage,
                )
            }

            RequestState.Failure -> {
                snackbarHostState.showSnackbar(
                    message = "${state.message.orEmpty()}",
                )
                loginViewModel.restart()
            }

            else -> {
                loginViewModel.restart()
            }
        }

    }
    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        contentWindowInsets = WindowInsets.statusBars,
        modifier = Modifier
            .fillMaxSize()
            .background(UAMPrimaryForeground),
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .background(UAMPrimary)
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(15.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Egresados",
                    color = Color(0xFFFFFFFF),
                    fontFamily = LibreFranklin,
                    fontWeight = FontWeight.Bold,
                    fontSize = 8.em,
                )
                Image(
                    painter = painterResource(
                        id = R.drawable.universidad_americana_2020
                    ),
                    contentDescription = "Logo de universidad americana",
                    modifier = Modifier
                        .height(150.dp)
                        .width(150.dp)
                )
            }
            TextField(
                value = state.req.email,
                onValueChange = { loginViewModel.onEmail(it) },
                label = {
                    Text(stringResource(id = R.string.usernameField))
                },
            )

            Spacer(
                modifier = Modifier.height(30.dp)
            )
            TextField(
                value = state.req.password,
                modifier = Modifier.padding(5.dp),
                onValueChange = { loginViewModel.onPassword(it) },
                label = { Text(stringResource(id = R.string.password)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    val descripcion = if (passwordVisible) "Password oculto" else "Mostrar Password"
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(painter = image, descripcion)
                    }
                },
                visualTransformation =
                if (passwordVisible) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                }
            )
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = {
                    loginViewModel.onLogin()
                },
                enabled = state.status != RequestState.Pending,
                colors = ButtonDefaults.buttonColors(
                    containerColor = UAMPrimaryForeground,
                    disabledContainerColor = UAMPrimaryForegroundMuted,
                )
            ) {
                when (state.status) {
                    RequestState.Pending -> CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.height(30.dp)
                    )

                    else -> Text(stringResource(id = R.string.loginButtonConfirm))
                }
            }
        }
    }
}