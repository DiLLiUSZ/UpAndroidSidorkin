package com.example.upsidorkin.ui.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.upsidorkin.ui.viewModel.VerifyOTPViewModel

@Composable
fun VerifyOTPScreen(
    navController: NavHostController,
    email: String, // Принимаем email
    viewModel: VerifyOTPViewModel = viewModel()
) {
    var code by remember { mutableStateOf("") }
    val context = LocalContext.current

    Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
        Column(
            modifier = Modifier.fillMaxSize().padding(24.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text("OTP Проверка", fontSize = 24.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())

            Spacer(modifier = Modifier.height(10.dp))
            Text("Код отправлен на $email", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth(), color = Color.Gray)

            Spacer(modifier = Modifier.height(30.dp))

            OutlinedTextField(
                value = code,
                onValueChange = { code = it },
                label = { Text("Введите код") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = {
                    // Вызов метода верификации
                    viewModel.verifyOTP(email, code, context, navController)
                },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF48B2E7))
            ) {
                Text("Подтвердить")
            }
        }
    }
}
