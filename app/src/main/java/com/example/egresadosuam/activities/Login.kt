package com.example.egresadosuam.activities

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.egresadosuam.R
import com.example.egresadosuam.viewmodel.LoginViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun Login(paddingValues: PaddingValues){

    val loginViewModel : LoginViewModel = viewModel()
    val state = loginViewModel.state;

    var passwordVisible by remember {
        mutableStateOf(false)
    }

    val image = if (passwordVisible)
        painterResource(id = R.drawable.visibility)
    else
        painterResource(id = R.drawable.visibility_off)
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ){

        TextField(value = state.email, onValueChange = {loginViewModel.onEmail(it)}, label = { Text(stringResource(id = R.string.usernameField))})

        Spacer(modifier = Modifier.height(20.dp))

        TextField(
           value=state.password,
            onValueChange = { loginViewModel.onPassword( it)},
            label= {Text(stringResource(id = R.string.password))},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val descripcion = if (passwordVisible) "Password oculto" else "Mostrar Password"
                IconButton(onClick = {passwordVisible = !passwordVisible }) {
                    Icon(painter= image, descripcion)
                }
            },
            visualTransformation =
            if (passwordVisible) {
                VisualTransformation.None
            }
            else {
                PasswordVisualTransformation()
            }
        )

    }
}