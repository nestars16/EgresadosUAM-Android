package com.example.egresadosuam.activities

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.egresadosuam.R
import com.example.egresadosuam.ui.theme.LibreFranklin
import com.example.egresadosuam.ui.theme.UAMPrimary
import com.example.egresadosuam.ui.theme.UAMPrimaryForeground
import com.example.egresadosuam.viewmodel.LoginViewModel

@Composable
fun Login(paddingValues: PaddingValues) {
    val loginViewModel: LoginViewModel = viewModel()
    val state = loginViewModel.state
    var passwordVisible by remember {
        mutableStateOf(false)
    }

    val image = if (passwordVisible)
        painterResource(id = R.drawable.visibility)
    else
        painterResource(id = R.drawable.visibility_off)

    Column(
        modifier = Modifier
            .background(UAMPrimary)
            .padding(paddingValues)
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
            value = state.email,
            onValueChange = { loginViewModel.onEmail(it) },
            label = {
                Text(stringResource(id = R.string.usernameField))
            },
            colors = TextFieldDefaults.colors(
            ),
        )

        Spacer(
            modifier = Modifier.height(30.dp)
        )

        TextField(
            value = state.password,
            modifier = Modifier.padding(5.dp),
            onValueChange = { loginViewModel.onPassword(it) },
            label = { Text(stringResource(id = R.string.password)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            colors = TextFieldDefaults.colors(
            ),
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
            },
            colors = ButtonDefaults.buttonColors(containerColor = UAMPrimaryForeground)
        ) {
            Text(stringResource(id = R.string.loginButtonConfirm))
        }
    }
}