package com.example.upsidorkin.ui.view

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.upsidorkin.R
import com.example.upsidorkin.ui.theme.UpSidorkinTheme
import com.example.upsidorkin.ui.viewModel.SignUpViewModel

@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: SignUpViewModel = viewModel()
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }
    var isTermsAccepted by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()
    val context = LocalContext.current

    // если возникла ошибка из ViewModel – показываем тост
    LaunchedEffect(viewModel.errorMessage.value) {
        viewModel.errorMessage.value?.let { msg ->
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
        }
    }

    Surface(
        modifier = modifier.fillMaxSize(),
        color = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 24.dp)
        ) {

            Spacer(modifier = Modifier.height(16.dp))

            // Кнопка «назад»
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFF2F2F2))
                        .clickable {
                            // если есть куда вернуться – вернёмся, иначе ничего
                            navController.popBackStack()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Назад",
                        tint = Color(0xFF555555)
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Заголовок по центру
            Text(
                text = "Регистрация",
                fontSize = 30.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF333333),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Подзаголовок по центру
            Text(
                text = "Заполните Свои Данные",
                fontSize = 14.sp,
                color = Color(0xFFB0B0B0),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Ваше имя
            Text(
                text = "Ваше имя",
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF666666),
                modifier = Modifier.padding(bottom = 6.dp)
            )

            StyledTextField(
                value = name,
                onValueChange = { name = it },
                placeholder = "Иван Иванов"
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Email
            Text(
                text = "Email",
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF666666),
                modifier = Modifier.padding(bottom = 6.dp)
            )

            StyledTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = "example@gmail.com",
                keyboardType = KeyboardType.Email
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Пароль
            Text(
                text = "Пароль",
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF666666),
                modifier = Modifier.padding(bottom = 6.dp)
            )

            StyledTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = "•••••••••",
                trailingIcon = {
                    IconButton(onClick = { showPassword = !showPassword }) {
                        Icon(
                            imageVector = if (showPassword)
                                Icons.Default.VisibilityOff
                            else
                                Icons.Default.Visibility,
                            contentDescription = "Показать/скрыть пароль",
                            tint = Color(0xFFB0B0B0)
                        )
                    }
                },
                keyboardType = KeyboardType.Password,
                visualTransformation = if (showPassword)
                    VisualTransformation.None
                else
                    PasswordVisualTransformation()
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Чекбокс с shield
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ShieldCheckbox(
                    checked = isTermsAccepted,
                    onCheckedChange = { isTermsAccepted = it }
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Даю согласие на обработку персональных данных",
                    fontSize = 13.sp,
                    color = Color(0xFF4A4A4A),
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            // Кнопка "Зарегистрироваться"
            Button(
                onClick = {
                    if (validateInputs(name, email, password, isTermsAccepted)) {
                        viewModel.signUp(
                            email.trim(),
                            password.trim(),
                            navController
                        )
                    } else {
                        Toast.makeText(
                            context,
                            "Проверьте введённые данные",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2B6B8B),
                    contentColor = Color.White,
                    disabledContainerColor = Color(0xFFCED6DD),
                    disabledContentColor = Color.White
                ),
                enabled = !viewModel.isLoading.value &&
                        validateInputs(name, email, password, isTermsAccepted)
            ) {
                if (viewModel.isLoading.value) {
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.size(20.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(
                        text = "Зарегистрироваться",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Низ: "Есть аккаунт? Войти" — пока просто заглушка
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Есть аккаунт?",
                    fontSize = 13.sp,
                    color = Color(0xFF9E9E9E)
                )
                Text(
                    text = " Войти",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF205B89),
                    modifier = Modifier.clickable {
                        // navController.navigate("login")  // когда появится экран логина
                    }
                )
            }
        }
    }
}

@Composable
private fun StyledTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    trailingIcon: @Composable (() -> Unit)? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        placeholder = {
            Text(
                text = placeholder,
                fontSize = 14.sp,
                color = Color(0xFFCBCBCB)
            )
        },
        trailingIcon = trailingIcon,
        singleLine = true,
        visualTransformation = visualTransformation,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        shape = RoundedCornerShape(18.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color(0xFF333333),
            unfocusedTextColor = Color(0xFF333333),
            focusedContainerColor = Color(0xFFF7F7F7),
            unfocusedContainerColor = Color(0xFFF7F7F7),
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            cursorColor = Color(0xFF333333),
            focusedPlaceholderColor = Color(0xFFCBCBCB),
            unfocusedPlaceholderColor = Color(0xFFCBCBCB)
        )
    )
}

@Composable
fun ShieldCheckbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    val backgroundColor = if (checked) Color(0xFF333333) else Color.White
    val borderColor = if (checked) Color(0xFF333333) else Color(0xFFBDBDBD)

    Box(
        modifier = Modifier
            .size(20.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(backgroundColor)
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(4.dp)
            )
            .clickable { onCheckedChange(!checked) },
        contentAlignment = Alignment.Center
    ) {
        if (checked) {
            Icon(
                painter = painterResource(id = R.drawable.shield),
                contentDescription = "Принято",
                tint = Color.White,
                modifier = Modifier.size(14.dp)
            )
        }
    }
}

fun isValidEmail(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

fun validateInputs(
    name: String,
    email: String,
    password: String,
    isTermsAccepted: Boolean
): Boolean {
    return name.trim().isNotEmpty() &&
            email.trim().isNotEmpty() && isValidEmail(email.trim()) &&
            password.trim().isNotEmpty() && password.length >= 8 &&
            isTermsAccepted
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RegisterScreenPreview() {
    UpSidorkinTheme {
        val navController = rememberNavController()
        RegisterScreen(navController = navController)
    }
}
