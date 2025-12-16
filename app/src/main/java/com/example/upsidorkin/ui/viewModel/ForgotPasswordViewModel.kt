package com.example.upsidorkin.ui.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ForgotPasswordViewModel : ViewModel() {
    val showDialog = mutableStateOf(false)

    fun sendRecoveryEmail(email: String) {
        viewModelScope.launch {
            // Вызов API: recoverPassword(mapOf("email" to email))
            // Если успех -> showDialog.value = true
            showDialog.value = true // Пока просто показываем диалог для теста UI
        }
    }
}
