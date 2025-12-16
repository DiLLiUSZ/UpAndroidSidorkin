// ... imports

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
                        startDestination = "register", // Или "login", как вам удобнее
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        // Существующие экраны...
                        composable("register") { RegisterScreen(navController = navController) }
                        composable("login") { LoginScreen(navController = navController) }
                        composable("home") { Text("Дом") }

                        // --- НОВЫЕ МАРШРУТЫ ---

                        // 1. Экран "Забыл пароль"
                        composable("forgot_password") {
                            ForgotPasswordScreen(navController = navController)
                        }

                        // 2. Экран OTP. Мы добавили параметр `type`, чтобы знать, это регистрация или сброс пароля.
                        // Маршрут: verifyOTP/{email}/{type}
                        composable(
                            "verifyOTP/{email}/{type}",
                            arguments = listOf(
                                navArgument("email") { type = NavType.StringType },
                                navArgument("type") { type = NavType.StringType } // "signup" или "recovery"
                            )
                        ) { backStackEntry ->
                            val email = backStackEntry.arguments?.getString("email") ?: ""
                            val type = backStackEntry.arguments?.getString("type") ?: "signup"
                            VerifyOTPScreen(navController = navController, email = email, otpType = type)
                        }

                        // 3. Экран "Новый пароль"
                        composable("new_password") {
                            NewPasswordScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}
