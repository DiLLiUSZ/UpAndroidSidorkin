package com.example.upsidorkin.ui.view

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocalShipping
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.upsidorkin.R // Убедитесь, что R класс импортирован корректно
import java.io.File
import kotlin.random.Random

// Основной экран
@Composable
fun ProfileScreen(navController: NavHostController) {
    // Состояния данных
    var isEditing by remember { mutableStateOf(false) }
    var firstName by remember { mutableStateOf("Emmanuel") }
    var lastName by remember { mutableStateOf("Oyiboke") }
    var address by remember { mutableStateOf("Nigeria") }
    var phone by remember { mutableStateOf("+7 811-732-5298") }
    var avatarUri by remember { mutableStateOf<Uri?>(null) }

    // Логика камеры
    val context = LocalContext.current
    val tmpImageUri = remember {
        val file = File(context.cacheDir, "profile_photo.jpg")
        FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
    }
    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success) avatarUri = tmpImageUri
    }
    val permissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) cameraLauncher.launch(tmpImageUri)
        else Toast.makeText(context, "Нужен доступ к камере", Toast.LENGTH_SHORT).show()
    }
    fun launchCamera() {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            cameraLauncher.launch(tmpImageUri)
        } else {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    Scaffold(
        containerColor = Color.White, // Белый фон как на макете
        bottomBar = {
            CurvedBottomBar() // Кастомный BottomBar
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp), // Отступы по бокам
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // 1. Верхняя панель (Профиль + кнопка редактирования)
            TopHeader(isEditing = isEditing, onEditClick = { isEditing = !isEditing })

            Spacer(modifier = Modifier.height(24.dp))

            // 2. Аватар
            AvatarSection(avatarUri = avatarUri, onClick = { if (isEditing) launchCamera() })

            Spacer(modifier = Modifier.height(16.dp))

            // 3. Имя под аватаром
            Text(
                text = "$firstName $lastName",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF333333)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // 4. Блок штрих-кода
            BarcodeCard()

            Spacer(modifier = Modifier.height(24.dp))

            // 5. Поля ввода
            ProfileField(title = "Имя", value = firstName, onValueChange = { firstName = it }, isEditing = isEditing)
            ProfileField(title = "Фамилия", value = lastName, onValueChange = { lastName = it }, isEditing = isEditing)
            ProfileField(title = "Адрес", value = address, onValueChange = { address = it }, isEditing = isEditing)
            ProfileField(title = "Телефон", value = phone, onValueChange = { phone = it }, isEditing = isEditing)

            // Кнопка сохранения (появляется только при редактировании)
            if (isEditing) {
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { isEditing = false },
                    modifier = Modifier.fillMaxWidth().height(52.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF48B2E7))
                ) {
                    Text("Сохранить", fontSize = 16.sp, color = Color.White)
                }
            }

            // Дополнительный отступ снизу, чтобы контент не перекрывался BottomBar
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

// --- Компоненты UI ---

@Composable
fun TopHeader(isEditing: Boolean, onEditClick: () -> Unit) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Профиль",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF333333),
            modifier = Modifier.align(Alignment.Center)
        )

        // Кнопка справа: либо синий круг с карандашом, либо текст "Готово"
        Box(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .size(32.dp)
                .clip(CircleShape)
                .background(if (isEditing) Color.Transparent else Color(0xFF48B2E7))
                .clickable { onEditClick() },
            contentAlignment = Alignment.Center
        ) {
            if (isEditing) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_check), // Используйте свою иконку галочки или стандартную
                    contentDescription = "Save",
                    tint = Color(0xFF48B2E7),
                    modifier = Modifier.size(24.dp)
                )
            } else {
                // Иконка карандаша
                Icon(
                    painter = painterResource(id = R.drawable.ic_edit), // Убедитесь, что ic_edit есть в drawable
                    contentDescription = "Edit",
                    tint = Color.White,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

@Composable
fun AvatarSection(avatarUri: Uri?, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(100.dp)
            .clip(CircleShape)
            .background(Color.LightGray)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        if (avatarUri != null) {
            Image(
                painter = rememberAsyncImagePainter(model = avatarUri),
                contentDescription = "Avatar",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        } else {
            // Плейсхолдер, если фото нет (можно заменить на вашу картинку из ресурсов)
            Image(
                painter = painterResource(id = R.drawable.ic_profile), // Вставьте id вашего плейсхолдера
                contentDescription = "Placeholder",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
fun BarcodeCard() {
    // Карточка со штрих-кодом
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp) // Высота как на фото
            .background(Color.Transparent), // Фон прозрачный, контент сам задает стиль
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Левая часть: Вертикальный текст "Открыть"
        Box(
            modifier = Modifier
                .width(40.dp)
                .fillMaxHeight(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Открыть",
                fontSize = 12.sp,
                color = Color.Gray,
                modifier = Modifier.rotate(-90f), // Поворот текста
                maxLines = 1
            )
        }

        // Правая часть: Штрих-код
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(vertical = 10.dp)
        ) {
            // Имитация штрих-кода (рисуем полоски)
            Canvas(modifier = Modifier.fillMaxSize()) {
                val canvasWidth = size.width
                val canvasHeight = size.height
                var x = 0f
                val random = Random(123) // Фиксированный seed для одинакового рисунка

                while (x < canvasWidth) {
                    val lineWidth = random.nextInt(2, 8).toFloat()
                    val gap = random.nextInt(2, 6).toFloat()

                    drawRect(
                        color = Color.Black,
                        topLeft = Offset(x, 0f),
                        size = Size(lineWidth, canvasHeight)
                    )
                    x += lineWidth + gap
                }
            }
        }
    }
}

@Composable
fun ProfileField(
    title: String,
    value: String,
    onValueChange: (String) -> Unit,
    isEditing: Boolean
) {
    Column(modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)) {
        Text(
            text = title,
            fontSize = 14.sp,
            color = Color(0xFF888888),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Поле ввода с серым фоном и скругленными углами
        BasicTextField(
            value = value,
            onValueChange = { if (isEditing) onValueChange(it) },
            enabled = isEditing,
            textStyle = LocalTextStyle.current.copy(
                color = Color.Black,
                fontSize = 16.sp
            ),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp)
                        .clip(RoundedCornerShape(12.dp)) // Скругление как на фото
                        .background(Color(0xFFF7F7F7)) // Светло-серый фон
                        .padding(horizontal = 16.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    innerTextField()
                }
            }
        )
    }
}

// --- Кастомный Bottom Bar ---

@Composable
fun CurvedBottomBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp), // Высота бара
        contentAlignment = Alignment.BottomCenter
    ) {
        // Тень и форма фона
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp) // Видимая высота белой части
                .shadow(elevation = 10.dp, shape = BottomBarShape()) // Тень
        ) {
            drawPath(
                path = createBottomBarPath(size, 80.dp.toPx(), 40.dp.toPx()), // Вырез
                color = Color.White
            )
        }

        // Кнопки навигации (расставлены вручную)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Левая группа
            IconButton(onClick = { }) { Icon(Icons.Outlined.Home, contentDescription = null, tint = Color.Gray) }
            IconButton(onClick = { }) { Icon(Icons.Outlined.FavoriteBorder, contentDescription = null, tint = Color.Gray) }

            Spacer(modifier = Modifier.width(48.dp)) // Место под центральную кнопку

            // Правая группа
            IconButton(onClick = { }) { Icon(Icons.Outlined.LocalShipping, contentDescription = null, tint = Color.Gray) }
            IconButton(onClick = { }) { Icon(Icons.Outlined.Person, contentDescription = null, tint = Color(0xFF48B2E7)) }
        }

        // Центральная плавающая кнопка (Lock)
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter) // Поднимаем выше фона
                .offset(y = (-10).dp) // Сдвиг вверх для эффекта выступания
                .size(56.dp)
                .clip(CircleShape)
                .background(Color(0xFF48B2E7))
                .clickable { },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Filled.Lock,
                contentDescription = "Center",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

// Форма для выреза (Shape) для тени
class BottomBarShape : Shape {
    override fun createOutline(size: Size, layoutDirection: LayoutDirection, density: Density): Outline {
        val path = createBottomBarPath(size, with(density) { 80.dp.toPx() }, with(density) { 40.dp.toPx() })
        return Outline.Generic(path)
    }
}

// Логика рисования пути с вырезом
fun createBottomBarPath(size: Size, cutoutWidth: Float, cutoutDepth: Float): Path {
    val path = Path()
    val width = size.width
    val height = size.height
    val center = width / 2f

    // Начало слева сверху
    path.moveTo(0f, 0f)

    // Линия до начала выреза
    path.lineTo(center - cutoutWidth, 0f)

    // Кривая Безье для выреза (плавный спуск и подъем)
    // Первая контрольная точка (спуск)
    path.cubicTo(
        center - cutoutWidth + 20f, 0f,
        center - cutoutWidth + 20f, cutoutDepth,
        center, cutoutDepth
    )
    // Вторая контрольная точка (подъем)
    path.cubicTo(
        center + cutoutWidth - 20f, cutoutDepth,
        center + cutoutWidth - 20f, 0f,
        center + cutoutWidth, 0f
    )

    // Линия до конца справа
    path.lineTo(width, 0f)
    path.lineTo(width, height)
    path.lineTo(0f, height)
    path.close()

    return path
}
