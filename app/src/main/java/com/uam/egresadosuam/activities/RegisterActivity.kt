package com.uam.egresadosuam.activities

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun RegisterScreen() {
    var firstName by remember { mutableStateOf("") }
    var secondName by remember { mutableStateOf("") }
    var firstLastName by remember { mutableStateOf("") }
    var secondLastName by remember { mutableStateOf("") }
    var birthday by remember { mutableStateOf("") }
    var endOfStudy by remember { mutableStateOf("") }
    var currentPos by remember { mutableStateOf("") }
    var currentPosStartDate by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var phones by remember { mutableStateOf(listOf("")) }
    var emails by remember { mutableStateOf(listOf("")) }
    var cif by remember { mutableStateOf("") }
    var logInEmail by remember { mutableStateOf("") }
    var trabajos by remember { mutableStateOf(listOf("")) }
    var etnia by remember { mutableStateOf("") }
    var carrera by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Registro de Egresados",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = firstName,
            onValueChange = { firstName = it },
            label = { Text("Primer Nombre") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = secondName,
            onValueChange = { secondName = it },
            label = { Text("Segundo Nombre") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = firstLastName,
            onValueChange = { firstLastName = it },
            label = { Text("Primer Apellido") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = secondLastName,
            onValueChange = { secondLastName = it },
            label = { Text("Segundo Apellido") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = birthday,
            onValueChange = { birthday = it },
            label = { Text("Fecha de Nacimiento") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = endOfStudy,
            onValueChange = { endOfStudy = it },
            label = { Text("Fecha de Graduación") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = currentPos,
            onValueChange = { currentPos = it },
            label = { Text("Posición Actual") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = currentPosStartDate,
            onValueChange = { currentPosStartDate = it },
            label = { Text("Fecha de Inicio Posición Actual") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Se puede agregar la lógica para manejar múltiples teléfonos y correos electrónicos.
        OutlinedTextField(
            value = phones.joinToString(", "),
            onValueChange = { phones = it.split(", ").toMutableList() },
            label = { Text("Teléfonos") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = emails.joinToString(", "),
            onValueChange = { emails = it.split(", ").toMutableList() },
            label = { Text("Correos Electrónicos") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = cif,
            onValueChange = { cif = it },
            label = { Text("CIF") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = logInEmail,
            onValueChange = { logInEmail = it },
            label = { Text("Correo de Inicio de Sesión") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = trabajos.joinToString(", "),
            onValueChange = { trabajos = it.split(", ").toMutableList() },
            label = { Text("Trabajos") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = etnia,
            onValueChange = { etnia = it },
            label = { Text("Etnia") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = carrera,
            onValueChange = { carrera = it },
            label = { Text("Carrera") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
        }) {
            Text("Registrar")
        }
    }
}