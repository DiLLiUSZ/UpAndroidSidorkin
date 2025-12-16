package com.example.upsidorkin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.upsidorkin.ui.theme.UpSidorkinTheme
import com.example.upsidorkin.ui.view.LoginScreen
import com.example.upsidorkin.ui.view.RegisterScreen
import com.example.upsidorkin.ui.view.VerifyOTPScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UpSidorkinTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "register",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        // 1. Экран регистрации
                        composable("register") {
                            RegisterScreen(navController = navController)
                        }

                        // 2. Экран OTP (принимает email)
                        composable("verifyOTP/{email}") { backStackEntry ->
                            val email = backStackEntry.arguments?.getString("email") ?: ""
                            VerifyOTPScreen(navController = navController, email = email)
                        }

                        // 3. Экран входа
                        composable("login") {
                            LoginScreen(navController = navController)
                        }

                        // 4. Домашний экран
                        composable("home") {
                            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                Text("Главная страница. Вы успешно вошли!")
                            }
                        }
                    }
                }
            }
        }
    }
}
