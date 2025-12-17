package com.example.upsidorkin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.upsidorkin.ui.theme.UpSidorkinTheme
import com.example.upsidorkin.ui.view.ForgotPasswordScreen
import com.example.upsidorkin.ui.view.LoginScreen
import com.example.upsidorkin.ui.view.NewPasswordScreen
import com.example.upsidorkin.ui.view.Onboard1Screen
import com.example.upsidorkin.ui.view.Onboard2Screen
import com.example.upsidorkin.ui.view.Onboard3Screen
import com.example.upsidorkin.ui.view.RegisterScreen
import com.example.upsidorkin.ui.view.SplashScreen
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
                        startDestination = "splash",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        // Splash
                        composable("splash") {
                            SplashScreen(navController = navController)
                        }

                        // Onboarding 1–3
                        composable("onboard1") {
                            Onboard1Screen(navController = navController)
                        }
                        composable("onboard2") {
                            Onboard2Screen(navController = navController)
                        }
                        composable("onboard3") {
                            Onboard3Screen(navController = navController)
                        }

                        // Регистрация / Логин / Дом
                        composable("register") {
                            RegisterScreen(navController = navController)
                        }
                        composable("login") {
                            LoginScreen(navController = navController)
                        }
                        composable("home") {
                            Text("Дом")
                        }

                        // Забыл пароль
                        composable("forgot_password") {
                            ForgotPasswordScreen(navController = navController)
                        }

                        // OTP: verifyOTP/{email}/{type}  (type = signup / recovery)
                        composable(
                            route = "verifyOTP/{email}/{type}",
                            arguments = listOf(
                                navArgument("email") { type = NavType.StringType },
                                navArgument("type") { type = NavType.StringType }
                            )
                        ) { backStackEntry ->
                            val email = backStackEntry.arguments?.getString("email") ?: ""
                            val type = backStackEntry.arguments?.getString("type") ?: "signup"
                            VerifyOTPScreen(
                                navController = navController,
                                email = email,
                                otpType = type
                            )
                        }

                        // Новый пароль: new_password/{email}
                        composable(
                            route = "new_password/{email}",
                            arguments = listOf(
                                navArgument("email") { type = NavType.StringType }
                            )
                        ) { backStackEntry ->
                            val email = backStackEntry.arguments?.getString("email") ?: ""
                            NewPasswordScreen(
                                navController = navController,
                                email = email
                            )
                        }
                    }
                }
            }
        }
    }
}
