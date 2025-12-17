package com.example.upsidorkin.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.upsidorkin.R

@Composable
fun Onboard1Screen(navController: NavHostController) {
    OnboardBase(
        imageRes = R.drawable.onboard1,  // картинка кроссовка
        title = "ДОБРО\nПОЖАЛОВАТЬ",
        subtitle = "",
        buttonText = "Начать",
        onButtonClick = { navController.navigate("onboard2") },
        indicatorIndex = 0
    )
}

@Composable
fun Onboard2Screen(navController: NavHostController) {
    OnboardBase(
        imageRes = R.drawable.onboard2,
        title = "Начнем\nпутешествие",
        subtitle = "Умная, великолепная и модная\nколлекция. Изучите сейчас",
        buttonText = "Далее",
        onButtonClick = { navController.navigate("onboard3") },
        indicatorIndex = 1
    )
}

@Composable
fun Onboard3Screen(navController: NavHostController) {
    OnboardBase(
        imageRes = R.drawable.onboard3,
        title = "У Вас Есть Сила,\nЧтобы",
        subtitle = "В вашей комнате много красивых\nи привлекательных растений",
        buttonText = "Далее",
        onButtonClick = { navController.navigate("register") },
        indicatorIndex = 2
    )
}

@Composable
private fun OnboardBase(
    imageRes: Int,
    title: String,
    subtitle: String,
    buttonText: String,
    onButtonClick: () -> Unit,
    indicatorIndex: Int
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFF48B2E7)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 32.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Верх – просто пустое пространство или статусбар
            Spacer(modifier = Modifier.height(16.dp))

            // Середина – кроссовок и текст
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = null,
                    modifier = Modifier
                        .height(220.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = title,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )

                if (subtitle.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = subtitle,
                        fontSize = 14.sp,
                        color = Color(0xFFE0E0E0),
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Индикаторы страниц (3 точки)
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    repeat(3) { index ->
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 4.dp)
                                .width(if (index == indicatorIndex) 24.dp else 8.dp)
                                .height(6.dp)
                                .background(
                                    color = if (index == indicatorIndex) Color.White else Color(0x55FFFFFF),
                                    shape = RoundedCornerShape(3.dp)
                                )
                        )
                    }
                }
            }

            // Кнопка снизу
            Button(
                onClick = onButtonClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color(0xFF48B2E7)
                )
            ) {
                Text(text = buttonText, fontSize = 16.sp, fontWeight = FontWeight.Medium)
            }
        }
    }
}
