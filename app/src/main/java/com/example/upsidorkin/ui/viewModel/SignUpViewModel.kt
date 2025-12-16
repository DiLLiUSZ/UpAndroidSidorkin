package com.example.upsidorkin.ui.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.upsidorkin.data.RetrofitInstance
import com.example.upsidorkin.data.model.SignUpRequest
import kotlinx.coroutines.launch

class SignUpViewModel : ViewModel() {

    val isLoading = mutableStateOf(false)
    val errorMessage = mutableStateOf<String?>(null)

    fun signUp(
        email: String,
        password: String,
        navController: NavHostController
    ) {
        viewModelScope.launch {
            try {
                isLoading.value = true
                errorMessage.value = null

                val response = RetrofitInstance.userManagementService
                    .signUp(SignUpRequest(email, password))

                if (response.isSuccessful) {
                    // тут супабейс отправит письмо с кодом
                    navController.navigate("verifyOTP")
                } else {
                    errorMessage.value = "Ошибка регистрации: ${response.code()}"
                }
            } catch (e: Exception) {
                errorMessage.value = "Ошибка сети: ${e.message}"
            } finally {
                isLoading.value = false
            }
        }
    }
}
