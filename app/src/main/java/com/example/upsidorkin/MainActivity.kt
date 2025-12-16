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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.upsidorkin.ui.theme.UpSidorkinTheme
import com.example.upsidorkin.ui.view.RegisterScreen

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
                        composable("register") {
                            RegisterScreen(navController = navController)
                        }
                        composable("verifyOTP") {
                            Text("Здесь будет экран ввода кода из письма")
                        }
                    }
                }
            }
        }
    }
}
