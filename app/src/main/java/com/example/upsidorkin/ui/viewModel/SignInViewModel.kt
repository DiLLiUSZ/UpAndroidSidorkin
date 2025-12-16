package com.example.upsidorkin.ui.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.upsidorkin.data.RetrofitInstance
import com.example.upsidorkin.data.model.SignInRequest
import kotlinx.coroutines.launch

class SignInViewModel : ViewModel() {
    var showDialog = mutableStateOf(false)
    var dialogText = mutableStateOf("")

    fun signIn(signInRequest: SignInRequest, navController: NavController) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.userManagementService.signIn(signInRequest)
                if (response.isSuccessful) {
                    // Успех -> Домой
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                } else {
                    dialogText.value = "Неверный логин или пароль"
                    showDialog.value = true
                }
            } catch (e: Exception) {
                dialogText.value = "Ошибка: ${e.message}"
                showDialog.value = true
            }
        }
    }
}
