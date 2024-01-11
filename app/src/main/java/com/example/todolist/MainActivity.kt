package com.example.todolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.todolist.RoomDatabase.AppDatabase
import com.example.todolist.RoomDatabase.ScreenNameEntity
import com.example.todolist.ui.theme.ToDoListTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val db = AppDatabase.getInstance(applicationContext)
        setContent {
            val navController = rememberNavController()
            ToDoListTheme {
                NavHost(navController, db)
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreen(navController: NavHostController, database: AppDatabase) {
    var showDialog by remember { mutableStateOf(false) }
    var listName by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val screenNames by database.ScreenNameDao().getAll().collectAsState(emptyList())
    val focusRequester = remember { FocusRequester() }
    if (showDialog) {
        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }
        Dialog(onDismissRequest = { showDialog = false }) {
            Card(colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primaryContainer)) {
                Column(Modifier.padding(20.dp)) {
                    Text(text = "New list",color=MaterialTheme.colorScheme.secondary)
                    TextField(value = listName,
                        onValueChange = { newValue -> listName = newValue },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor =MaterialTheme.colorScheme.secondary,
                            unfocusedIndicatorColor = MaterialTheme.colorScheme.secondary
                        ),
                        placeholder = {
                            Text(
                                text = "Enter list title"
                            )
                        }, modifier = Modifier.focusRequester(focusRequester)
                    )
                    Row {
                        Spacer(modifier = Modifier.weight(1f))
                        Button(onClick = {
                            if (listName.isNotBlank()) {
                                scope.launch {
                                    database.ScreenNameDao().insert(
                                        ScreenNameEntity(screenName = listName)
                                    )
                                    listName = ""
                                    showDialog = false
                                }
                            }
                        }, colors = ButtonDefaults.buttonColors(Color.Transparent)) {
                            Text(text = "Create list", color = MaterialTheme.colorScheme.secondary)
                        }
                    }
                }
            }
        }
    }
    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .navigationBarsPadding()
            .statusBarsPadding()
    ) {
        Row(Modifier.padding(top = 8.dp, start = 8.dp)) {
            AsyncImage(
                model = R.drawable.example_user_pic,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(40.dp)
                    .height(40.dp)
                    .clip(CircleShape)
            )
            Column(Modifier.padding(start = 8.dp)) {
                Text(
                    text = stringResource(id = R.string.username),
                    color = MaterialTheme.colorScheme.secondary
                )
                Text(
                    text = stringResource(id = R.string.email),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
        Button(
            onClick = { navController.navigate("myDay") },
            Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(0),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.background),
            contentPadding = PaddingValues(8.dp)
        ) {
            Row {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_wb_sunny_24),
                    contentDescription = null
                )
                Text(
                    text = stringResource(id = R.string.my_day), modifier = Modifier
                        .padding(start = 10.dp)
                        .weight(1f), color = MaterialTheme.colorScheme.secondary,
                    fontSize = 16.sp
                )
            }
        }
        Button(
            onClick = { navController.navigate("tasks") },
            Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(0),
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.background),
            contentPadding = PaddingValues(8.dp)
        ) {
            Row {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_check_circle_outline_24),
                    contentDescription = null
                )
                Text(
                    text = stringResource(id = R.string.tasks), modifier = Modifier
                        .padding(start = 10.dp)
                        .weight(1f), color = MaterialTheme.colorScheme.secondary,
                    fontSize = 16.sp
                )
            }
        }
        if (screenNames.isNotEmpty()) {
            Divider(thickness = 1.5.dp, color = MaterialTheme.colorScheme.secondary)
        }

        LazyColumn(
            Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            items(screenNames) { screenName ->
                var expandedEdit by remember { mutableStateOf(false) }
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 15.dp, bottom = 15.dp, start = 8.dp)
                        .combinedClickable(
                            onClick = { navController.navigate("newlist/${screenName.id}") },
                            onLongClick = {expandedEdit = true},
                        ),
                    shape = RoundedCornerShape(0),
                    colors = CardDefaults.cardColors(MaterialTheme.colorScheme.background)
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
                                    database.ScreenNameDao().deleteScreenNameAndAssociatedNewList(screenName.screenName)
                                }
                                expandedEdit = false
                            })
                    }
                    Row {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_format_list_bulleted_24),
                            contentDescription = null,
                            tint = Color(0xFF7d81b4)
                        )
                        Text(
                            text = screenName.screenName,
                            color = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.padding(start = 10.dp),
                            fontSize = 16.sp,
                        )
                    }
                }
            }
        }
        Button(
            onClick = { showDialog = true },
            Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(0),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.background),
            contentPadding = PaddingValues(8.dp)
        ) {
            Row {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_add_24_white),
                    contentDescription = null
                )
                Text(
                    text = stringResource(id = R.string.new_list), modifier = Modifier
                        .padding(start = 10.dp)
                        .weight(1f), color = MaterialTheme.colorScheme.secondary,
                    fontSize = 16.sp
                )
            }
        }

    }
}





