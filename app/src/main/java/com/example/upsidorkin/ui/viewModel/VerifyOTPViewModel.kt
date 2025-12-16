package com.example.upsidorkin.ui.viewModel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.upsidorkin.data.RetrofitInstance
import com.example.upsidorkin.data.model.VerifyOtpRequest
import kotlinx.coroutines.launch

class VerifyOTPViewModel : ViewModel() {

    fun verifyOTP(email: String, token: String, context: Context, navController: NavController) {
        viewModelScope.launch {
            try {
                val request = VerifyOtpRequest(
                    type = "signup",
                    email = email,
                    token = token
                )

                val response = RetrofitInstance.userManagementService.verifyOTP(request)

                if (response.isSuccessful) {
                    Toast.makeText(context, "Email подтвержден!", Toast.LENGTH_SHORT).show()
                    // Переход на Login, очищаем стек
                    navController.navigate("login") {
                        popUpTo("register") { inclusive = true }
                    }
                } else {
                    Toast.makeText(context, "Ошибка подтверждения", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Ошибка сети: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
