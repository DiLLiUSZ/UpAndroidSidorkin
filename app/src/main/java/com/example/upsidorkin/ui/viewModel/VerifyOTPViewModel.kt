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

    // Обновленная сигнатура метода с параметром type
    fun verifyOTP(
        email: String,
        token: String,
        type: String, // <--- Новый параметр
        context: Context,
        navController: NavController
    ) {
        viewModelScope.launch {
            try {
                // Преобразуем наш внутренний тип в тип для API
                // Supabase принимает "signup", "recovery", "invite" и т.д.
                // Если мы передаем "recovery", то и отправляем "recovery"
                val requestType = if (type == "recovery") "recovery" else "signup"

                val request = VerifyOtpRequest(
                    type = requestType,
                    email = email,
                    token = token
                )

                val response = RetrofitInstance.userManagementService.verifyOTP(request)

                if (response.isSuccessful) {
                    // Успех
                    if (type == "recovery") {
                        // Если это восстановление -> идем задавать новый пароль
                        navController.navigate("new_password")
                    } else {
                        // Если регистрация -> идем логиниться
                        navController.navigate("login") {
                            popUpTo("register") { inclusive = true }
                        }
                    }
                } else {
                    Toast.makeText(context, "Неверный код", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Ошибка: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
