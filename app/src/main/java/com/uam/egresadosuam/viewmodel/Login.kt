package com.uam.egresadosuam.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.uam.egresadosuam.model.LoginData
import com.uam.egresadosuam.model.LoginRequest
import com.uam.egresadosuam.model.RequestResponse
import com.uam.egresadosuam.model.RequestState
import com.uam.egresadosuam.remote.ApiAdapter
import com.uam.egresadosuam.remote.ApiEgresado
import com.uam.egresadosuam.repository.UserCredentialsRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException


class LoginViewModel(private val userCredentialsRepository: UserCredentialsRepository) :
    ViewModel() {

    var state by mutableStateOf(LoginState())

    val apiEgresado: ApiEgresado by lazy {
        ApiAdapter.getInstance(userCredentialsRepository).create(ApiEgresado::class.java)
    }

    fun restart() {
        state = state.copy(req = LoginRequest(state.req.email, ""), status = null, message = null)
    }

    fun onEmail(email: String) {
        state = state.copy(req = LoginRequest(email, state.req.password))
    }

    fun onPassword(password: String) {
        state = state.copy(req = LoginRequest(state.req.email, password))
    }

    fun onLogin() {
        viewModelScope.launch {
            state = state.copy(status = RequestState.Pending)

            val response = try {
                apiEgresado.login(state.req)
            } catch (err: HttpException) {
                val rawError = err.response()?.errorBody()?.string()
                val errorResponse = rawError?.let {
                    Gson().fromJson(it, RequestResponse.Error::class.java)
                }
                errorResponse ?: RequestResponse.Error("error", "Hubo un error con su solicitud")
            } catch (ex: Exception) {
                Log.d("Error", "${ex.message}")
                RequestResponse.Error("error", "No se pudo conectar al servidor")
            }

            state = when (response) {
                is RequestResponse.Success<LoginData> -> {
                    userCredentialsRepository.save(response.data.token)
                    state.copy(status = RequestState.Success, message = "")
                }

                is RequestResponse.Error -> state.copy(
                    status = RequestState.Failure,
                    message = response.message
                )
            }
        }
    }

    data class LoginState(
        val req: LoginRequest = LoginRequest("", ""),
        val status: RequestState? = null,
        val message: String? = null
    )
}

class LoginViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoginViewModel(UserCredentialsRepository(context)) as T
    }
}