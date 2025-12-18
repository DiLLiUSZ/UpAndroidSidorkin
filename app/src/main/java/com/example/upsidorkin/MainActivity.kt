package com.example.upsidorkin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.upsidorkin.ui.theme.UpSidorkinTheme
import com.example.upsidorkin.ui.view.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()          // 1. включаем системный сплэш
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            UpSidorkinTheme {          // 2. compose‑тема
                val navController = rememberNavController()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "onboard1",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("onboard1") { Onboard1Screen(navController) }
                        composable("onboard2") { Onboard2Screen(navController) }
                        composable("onboard3") { Onboard3Screen(navController) }
                        composable("login") { LoginScreen(navController = navController) }
                        composable("register") { RegisterScreen(navController = navController) }
                        composable("home") { HomeScreen(navController) }

                        composable("forgot_password") {
                            ForgotPasswordScreen(navController)
                        }

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

                        composable(
                            route = "new_password/{email}",
                            arguments = listOf(
                                navArgument("email") { type = NavType.StringType }
                            )
                        ) { backStackEntry ->
                            val email = backStackEntry.arguments?.getString("email") ?: ""
                            NewPasswordScreen(navController = navController, email = email)
                        }
                    }
                }
            }
        }
    }
}
