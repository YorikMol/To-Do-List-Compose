package com.example.todolist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyDay(navController: NavHostController, database: AppDatabase) {
    var expanded by remember {
        mutableStateOf(false)
    }
    val calendar = Calendar.getInstance()
    val dateFormat = SimpleDateFormat("EEEE, MMMM d", Locale.getDefault())
    val formattedDate = dateFormat.format(calendar.time)
    val sheetStateInput = rememberModalBottomSheetState()
    val sheetStateBackground = rememberModalBottomSheetState()
    var containerColor by remember { mutableStateOf(Color(0xFFEE9E8B)) }
    val scope = rememberCoroutineScope()
    val focusRequester = remember { FocusRequester() }
    var showBottomSheetInput by remember { mutableStateOf(false) }
    var showBottomSheetBackground by remember { mutableStateOf(false) }
    var textValue by remember { mutableStateOf("") }
    val users by database.userDao().getAll().collectAsState(emptyList())
    var selectedBackgroundImage by remember { mutableIntStateOf(R.drawable.background) }

    when (selectedBackgroundImage) {
        R.drawable.background -> {
            containerColor = Color(0xFFEE9E8B)
        }

        R.drawable.background1 -> {
            containerColor = Color(0xFFE1CAA3)
        }

        R.drawable.background2 -> {
            containerColor = Color(0xFFAE9994)
        }

        R.drawable.background4 -> {
            containerColor = Color(0xFFc15d5f)
        }

        R.drawable.background5 -> {
            containerColor = Color(0xFFd4e8f3)
        }
    }
    val images = listOf(
        R.drawable.background,
        R.drawable.background1,
        R.drawable.background2,
        R.drawable.background4,
        R.drawable.background5
    )
    if (showBottomSheetInput) {
        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }
        ModalBottomSheet(
            onDismissRequest = { showBottomSheetInput = false },
            windowInsets = WindowInsets(bottom = 0.dp),
            sheetState = sheetStateInput,
            containerColor = Color(0xFF212121),
            dragHandle = { BottomSheetDefaults.DragHandle(color = Color(0xFF777779)) }
        ) {
            Row(
                modifier = Modifier
                    .navigationBarsPadding()
                    .padding(start = 12.dp, top = 10.dp, bottom = 10.dp, end = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
                    Checkbox(checked = false, onCheckedChange = {})
                }
                TextField(
                    textStyle = TextStyle(color = Color.White),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color(0xFF212121),
                        focusedContainerColor = Color(0xFF212121),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = Color.White

                    ),
                    placeholder = { Text(color = Color.White, text = "Enter your text") },
                    maxLines = 1,
                    value = textValue,
                    modifier = Modifier
                        .weight(1f)
                        .focusRequester(focusRequester),
                    onValueChange = { newText -> textValue = newText })
                Button(
                    onClick = {
                        if (textValue.isNotBlank()) {
                            scope.launch {
                                database.userDao()
                                    .insertAll(User(isChecked = false, todo = textValue))
                                textValue = ""
                            }
                        }
                    },
                    modifier = Modifier
                        .size(48.dp),
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF777779)),
                    shape = RoundedCornerShape(8.dp)

                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_arrow_upward_24),
                        contentDescription = null,
                        tint = Color(0xFF212121)
                    )

                }
            }
        }
    }
    if (showBottomSheetBackground) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheetBackground = false },
            windowInsets = WindowInsets(bottom = 0.dp),
            sheetState = sheetStateBackground,
            containerColor = Color(0xFF212121),
            dragHandle = { BottomSheetDefaults.DragHandle(color = Color(0xFF777779)) }
        ) {
            Column(modifier = Modifier.navigationBarsPadding()) {
                Text(
                    text = "Pick a background",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFDDDDDD),
                    fontSize = 20.sp,
                    modifier = Modifier.padding(start = 10.dp)
                )
                LazyRow {
                    items(images) { drawableId ->
                        AsyncImage(
                            model = drawableId,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .padding(4.dp)
                                .height(120.dp)
                                .width(80.dp)
                                .clickable {
                                    selectedBackgroundImage = drawableId
                                }
                                .clip(RoundedCornerShape(10.dp))

                        )


                    }
                }
            }
        }
    }
    AsyncImage(
        model = selectedBackgroundImage,
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop,

        )

    Column(
        modifier = Modifier
            .padding(top = 8.dp, start = 8.dp, end = 8.dp)
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.statusBars)
    ) {
        Row {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    Icons.Default.ArrowBack,
                    tint = Color(0xFFfefafa),
                    contentDescription = null
                )
            }
            Column {
                Text(text = "My Day", color = Color(0xFFfefafa), fontSize = 22.sp)
                Text(text = formattedDate, color = Color(0xFFfefafa), fontSize = 16.sp)
            }
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = { expanded = true }) {
                Icon(
                    Icons.Default.MoreVert,
                    contentDescription = null,
                    tint = Color(0xFFfefafa)
                )
                DropdownMenu(
                    modifier = Modifier.background(Color(0xFF303030)),
                    expanded = expanded,
                    onDismissRequest = { expanded = false }) {
                    DropdownMenuItem(
                        text = { Text(text = "Change Background", color = Color.White) },
                        onClick = {
                            showBottomSheetBackground = true
                            expanded = false
                        },
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_photo_24),
                                contentDescription = null, tint = Color.White
                            )
                        })
                }
            }
        }
        Box(Modifier.fillMaxSize()) {
            LazyColumn(
                Modifier
                    .fillMaxWidth()

            ) {
                items(users) { user ->
                    val isCheckedState = remember { mutableStateOf(user.isChecked) }
                    Card(
                        Modifier
                            .fillMaxWidth()
                            .padding(top = 3.dp),
                        colors = CardDefaults.cardColors(Color(0xFF212121))
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(
                                checked = user.isChecked,
                                onCheckedChange = {
                                    scope.launch{
                                        if (!isCheckedState.value) {
                                            isCheckedState.value = true
                                        } else if (isCheckedState.value) {
                                            isCheckedState.value = false
                                        }
                                        val updatedUser = user.copy(isChecked = isCheckedState.value)
                                        database.userDao().updateItem(updatedUser)
                                    }
                                },
                                Modifier.padding(10.dp)
                            )
                            Text(
                                text = user.todo,
                                color = Color.White
                            )
                        }
                    }
                }
            }
            FloatingActionButton(
                onClick = {
                    showBottomSheetInput = true
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .navigationBarsPadding()
                    .padding(bottom = 8.dp),
                containerColor = containerColor
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_add_24),
                    contentDescription = null
                )
            }
        }
    }
}