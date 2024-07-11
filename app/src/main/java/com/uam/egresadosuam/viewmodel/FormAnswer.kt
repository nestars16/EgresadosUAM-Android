package com.uam.egresadosuam.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.google.gson.Gson
import com.uam.egresadosuam.model.Form
import com.uam.egresadosuam.model.RequestResponse
import com.uam.egresadosuam.navigation.Login
import com.uam.egresadosuam.remote.ApiAdapter
import com.uam.egresadosuam.remote.ApiForm
import com.uam.egresadosuam.repository.UserCredentialsRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException


class FormAnswerViewModel(
    private val userCredentialsRepository: UserCredentialsRepository,
    id: String,
    navController: NavController,
    snackbarHostState: SnackbarHostState
) :
    ViewModel() {

    var form = mutableStateOf(Form())
    val apiForm: ApiForm by lazy {
        ApiAdapter.getInstance(userCredentialsRepository).create(ApiForm::class.java)
    }

    init {
        viewModelScope.launch {
            val response = try {
                apiForm.getForm(id)
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

            when (response) {
                is RequestResponse.Error -> {
                    navController.navigate(Login)
                    snackbarHostState.showSnackbar(
                        message = "Un error ocurrio, porfavor ingresar de nuevo",
                    )
                }

                is RequestResponse.Success<Form> -> {
                    form = mutableStateOf(response.data)
                }
            }
        }
    }

    fun onFormAnswer() {
        viewModelScope.launch {

        }
    }

}

class FormAnswerViewModelFactory(
    private val context: Context,
    private val id: String,
    private val navController: NavController,
    private val snackbarHostState: SnackbarHostState
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FormAnswerViewModel(
            UserCredentialsRepository(context),
            id,
            navController,
            snackbarHostState
        ) as T
    }
}