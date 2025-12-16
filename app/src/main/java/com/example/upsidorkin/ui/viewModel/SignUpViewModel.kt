package com.example.upsidorkin.ui.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.upsidorkin.data.RetrofitInstance
import com.example.upsidorkin.data.model.SignUpRequest
import kotlinx.coroutines.launch

class SignUpViewModel : ViewModel() {
    val isLoading = mutableStateOf(false)
    val errorMessage = mutableStateOf<String?>(null)

    fun signUp(email: String, password: String, navController: NavController) {
        viewModelScope.launch {
            try {
                isLoading.value = true
                errorMessage.value = null

                val response = RetrofitInstance.userManagementService
                    .signUp(SignUpRequest(email, password))

                if (response.isSuccessful) {
                    // Успех! Идем вводить код, передаем email
                    navController.navigate("verifyOTP/$email")
                } else {
                    errorMessage.value = "Ошибка: ${response.code()}"
                }
            } catch (e: Exception) {
                errorMessage.value = "Ошибка сети: ${e.message}"
            } finally {
                isLoading.value = false
            }
        }
    }
}
