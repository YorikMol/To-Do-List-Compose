package com.example.todolist

import android.content.Context
import android.media.MediaPlayer
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.todolist.RoomDatabase.AppDatabase
import com.example.todolist.RoomDatabase.MyDayEntity
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MyDay(navController: NavHostController, database: AppDatabase) {
    val BACKGROUND_KEY = intPreferencesKey("background_my_day")
    var expanded by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val dateFormat = SimpleDateFormat("EEEE, d MMMM", Locale.getDefault())
    val formattedDate = dateFormat.format(calendar.time)
    val mediaPlayer = remember { MediaPlayer.create(context, R.raw.complition_sound) }
    var containerColor by remember { mutableStateOf(Color(0xFFEE9E8B)) }

    val scope = rememberCoroutineScope()

    val focusRequester = remember { FocusRequester() }

    val sheetStateInput = rememberModalBottomSheetState()
    val sheetStateBackground = rememberModalBottomSheetState()
    var showBottomSheetInput by remember { mutableStateOf(false) }
    var showBottomSheetBackground by remember { mutableStateOf(false) }

    var textValue by remember { mutableStateOf("") }
    val myDayData by database.MyDayDao().getAll().collectAsState(emptyList())
    var selectedBackgroundImage by remember { mutableIntStateOf(R.drawable.background) }
    LaunchedEffect(context.dataStore) {
        context.dataStore.data.map { preferences ->
            preferences[BACKGROUND_KEY] ?: R.drawable.background
        }.collect { newValue ->
            selectedBackgroundImage = newValue
        }
    }
    when (selectedBackgroundImage) {
        R.drawable.background -> {
            containerColor = Color(0xFFEE9E8B)
        }

        R.drawable.background1 -> {
            containerColor = Color(0xFFE1CAA3)
        }

        R.drawable.background2 -> {
            containerColor = Color(0xFFd16a30)
        }

        R.drawable.background3 -> {
            containerColor = Color(0xFFAE9994)
        }

        R.drawable.background4 -> {
            containerColor = Color(0xFFa57157)
        }

        R.drawable.background5 -> {
            containerColor = Color(0xFFd4e8f3)
        }

        R.drawable.background6 -> {
            containerColor = Color(0xFF798193)
        }

        R.drawable.background7 -> {
            containerColor = Color(0xFF06b7b2)
        }

        R.drawable.background8 -> {
            containerColor = Color(0xFFb07a2d)
        }
    }
    val images = listOf(
        R.drawable.background,
        R.drawable.background1,
        R.drawable.background2,
        R.drawable.background3,
        R.drawable.background4,
        R.drawable.background5,
        R.drawable.background6,
        R.drawable.background7,
        R.drawable.background8
    )
    if (showBottomSheetInput) {
        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }
        ModalBottomSheet(
            onDismissRequest = { showBottomSheetInput = false },
            windowInsets = WindowInsets(bottom = 0.dp),
            sheetState = sheetStateInput,
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            dragHandle = { BottomSheetDefaults.DragHandle(color = if (isSystemInDarkTheme()) Color.White else Color.Black) }
        ) {
            Row(
                modifier = Modifier
                    .navigationBarsPadding()
                    .padding(start = 12.dp, top = 10.dp, bottom = 10.dp, end = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
                    Checkbox(checked = false, onCheckedChange = {}, enabled = false)
                }
                TextField(
                    textStyle = TextStyle(MaterialTheme.colorScheme.secondary),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                        focusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = MaterialTheme.colorScheme.secondary,
                    ),
                    placeholder = {
                        Text(
                            color = MaterialTheme.colorScheme.secondary,
                            text = stringResource(id = R.string.enter_your_text)
                        )
                    },
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
                                database.MyDayDao()
                                    .insertAll(MyDayEntity(todo = textValue))
                                textValue = ""
                            }
                        }
                    },
                    modifier = Modifier
                        .size(48.dp),
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isSystemInDarkTheme()) Color(0xFF777779) else Color(
                            0xFFE2E2E2
                        )
                    ),
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
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            dragHandle = { BottomSheetDefaults.DragHandle(color = if (isSystemInDarkTheme()) Color.White else Color.Black) }
        ) {
            Column(modifier = Modifier.navigationBarsPadding()) {
                Text(
                    text = stringResource(id = R.string.pick_background),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.secondary,
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
                                    scope.launch {
                                        context.dataStore.edit { preferences ->
                                            preferences[BACKGROUND_KEY] = drawableId
                                        }
                                    }
                                }
                                .clip(RoundedCornerShape(10.dp))

                        )
                    }
                }
            }
        }
    }
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(selectedBackgroundImage)
            .build(),
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
                Text(
                    text = stringResource(id = R.string.my_day),
                    color = Color(0xFFfefafa),
                    fontSize = 22.sp,
                )
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
                    modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer),
                    expanded = expanded,
                    onDismissRequest = { expanded = false }) {
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = stringResource(id = R.string.change_background),
                                color = MaterialTheme.colorScheme.secondary
                            )
                        },
                        onClick = {
                            showBottomSheetBackground = true
                            expanded = false
                        },
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_photo_24),
                                contentDescription = null,
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
                items(myDayData) { dayInfo ->
                    val isCheckedState = remember { mutableStateOf(dayInfo.isChecked) }
                    val sheetStateEdit = rememberModalBottomSheetState()
                    var showBottomSheetEdit by remember { mutableStateOf(false) }
                    var textValueEdit by remember { mutableStateOf("") }
                    var expandedEdit by remember { mutableStateOf(false) }
                    if (showBottomSheetEdit) {
                        LaunchedEffect(Unit) {
                            focusRequester.requestFocus()
                        }
                        ModalBottomSheet(
                            onDismissRequest = { showBottomSheetEdit = false },
                            windowInsets = WindowInsets(bottom = 0.dp),
                            sheetState = sheetStateEdit,
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            dragHandle = { BottomSheetDefaults.DragHandle(color = if (isSystemInDarkTheme()) Color.White else Color.Black) }
                        ) {
                            Row(
                                modifier = Modifier
                                    .navigationBarsPadding()
                                    .padding(
                                        start = 12.dp,
                                        top = 10.dp,
                                        bottom = 10.dp,
                                        end = 12.dp
                                    ),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
                                    Checkbox(checked = false, onCheckedChange = {})
                                }
                                TextField(
                                    textStyle = TextStyle(MaterialTheme.colorScheme.secondary),
                                    colors = TextFieldDefaults.colors(
                                        unfocusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                                        focusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                                        focusedIndicatorColor = Color.Transparent,
                                        unfocusedIndicatorColor = Color.Transparent,
                                        cursorColor = MaterialTheme.colorScheme.secondary,
                                    ),
                                    placeholder = {
                                        Text(
                                            color = MaterialTheme.colorScheme.secondary,
                                            text = stringResource(id = R.string.enter_your_text)
                                        )
                                    },
                                    maxLines = 1,
                                    value = textValueEdit,
                                    modifier = Modifier
                                        .weight(1f)
                                        .focusRequester(focusRequester),
                                    onValueChange = { newText -> textValueEdit = newText })
                                Button(
                                    onClick = {
                                        if (textValueEdit.isNotBlank()) {
                                            scope.launch {
                                                val editedDayInfo =
                                                    dayInfo.copy(todo = textValueEdit)
                                                database.MyDayDao().updateItem(editedDayInfo)
                                                textValueEdit = ""
                                                showBottomSheetEdit = false
                                            }
                                        }
                                    },
                                    modifier = Modifier
                                        .size(48.dp),
                                    contentPadding = PaddingValues(0.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = if (isSystemInDarkTheme()) Color(0xFF777779) else Color(
                                            0xFFE2E2E2
                                        )
                                    ),
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
                    Card(
                        Modifier
                            .fillMaxWidth()
                            .padding(top = 3.dp)
                            .combinedClickable(
                                onClick = {},
                                onLongClick = { expandedEdit = true }
                            ),
                        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primaryContainer)
                    ) {
                        DropdownMenu(
                            modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer),
                            expanded = expandedEdit,
                            onDismissRequest = { expandedEdit = false }) {
                            DropdownMenuItem(
                                text = {
                                    Row(
                                        horizontalArrangement = Arrangement.Center,
                                        modifier = Modifier.padding(top = 12.dp)
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.baseline_edit_24),
                                            contentDescription = null
                                        )
                                        Text(
                                            text = stringResource(id = R.string.edit),
                                            color = MaterialTheme.colorScheme.secondary,
                                            modifier = Modifier.padding(start = 8.dp)
                                        )
                                    }
                                },
                                onClick = {
                                    showBottomSheetEdit = true
                                    expandedEdit = false
                                })
                            DropdownMenuItem(
                                text = {
                                    Row(
                                        horizontalArrangement = Arrangement.Center,
                                        modifier = Modifier.padding(top = 12.dp)
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.baseline_delete_24),
                                            contentDescription = null
                                        )
                                        Text(
                                            text = stringResource(id = R.string.delete),
                                            color = MaterialTheme.colorScheme.secondary,
                                            modifier = Modifier.padding(start = 8.dp)
                                        )
                                    }
                                },

                                onClick = {
                                    scope.launch {
                                        database.MyDayDao().delete(dayInfo)
                                    }
                                    expandedEdit = false
                                })

                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(
                                checked = dayInfo.isChecked,
                                colors = CheckboxDefaults.colors(
                                    checkmarkColor = MaterialTheme.colorScheme.primaryContainer,
                                    checkedColor = MaterialTheme.colorScheme.onPrimary
                                ),
                                onCheckedChange = {
                                    scope.launch {
                                        if (!isCheckedState.value) {
                                            isCheckedState.value = true
                                            mediaPlayer.start()
                                        } else if (isCheckedState.value) {
                                            isCheckedState.value = false
                                        }
                                        val updatedMyDay =
                                            dayInfo.copy(isChecked = isCheckedState.value)
                                        database.MyDayDao().updateItem(updatedMyDay)
                                    }
                                },
                                modifier = Modifier.padding(10.dp)
                            )
                            Text(
                                text = dayInfo.todo,
                                color = if (isCheckedState.value) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.secondary,
                                textDecoration = if (isCheckedState.value) TextDecoration.LineThrough else null
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
    DisposableEffect(Unit) {
        onDispose { mediaPlayer.release() }// This block will be executed when the Composable leaves the composition
    }
}