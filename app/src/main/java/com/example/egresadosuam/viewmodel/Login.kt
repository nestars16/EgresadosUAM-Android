package com.example.egresadosuam.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.egresadosuam.model.RequestState
import kotlinx.coroutines.launch


class LoginViewModel : ViewModel() {
    var state by mutableStateOf(LoginState())

    fun restart() {
        state = state.copy(password = "", status = null, message = null)
    }

    fun onEmail(email: String) {
        state = state.copy(email = email)
    }

    fun onPassword(password: String){
        state = state.copy(password = password)
    }

    fun onLogin() {
        viewModelScope.launch {

        }
    }

    data class LoginState(
        val email: String = "",
        val password :String = "",
        val status : RequestState? = null,
        val message : String? = null
    )
}