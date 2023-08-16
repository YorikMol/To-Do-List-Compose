package com.example.todolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            val navController = rememberNavController()

            NavHost(navController, startDestination = "mainScreen") {
                composable("mainScreen") { MainScreen(navController) }
                composable("myDay") { MyDay(navController) }
                composable("tasks") { Tasks() }
            }
        }
    }
}

@Composable
fun MainScreen(navController: NavHostController) {
    Column(
        Modifier
            .fillMaxSize()
            .background(Color.Black)
            .navigationBarsPadding()
            .statusBarsPadding()
    ) {
        Row(Modifier.padding(top = 8.dp, start = 8.dp)) {
            Image(
                painter = painterResource(id = R.drawable.example_user_pic),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(40.dp)
                    .height(40.dp)
                    .clip(CircleShape)
            )
            Column(Modifier.padding(start = 8.dp)) {
                Text(text = "James Thompson", color = Color.White)
                Text(text = "useremail@gmail.com", color = Color(0xFF858585))
            }
        }
        Button(
            onClick = { navController.navigate("myDay") },
            Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(0),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
            contentPadding = PaddingValues(8.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_wb_sunny_24),
                contentDescription = null
            )
            Text(
                text = "My day", modifier = Modifier
                    .padding(start = 10.dp)
                    .weight(1f)
            )
        }
        Button(
            onClick = { navController.navigate("tasks") },
            Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(0),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
            contentPadding = PaddingValues(8.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_check_circle_outline_24),
                contentDescription = null
            )
            Text(
                text = "Tasks", modifier = Modifier
                    .padding(start = 10.dp)
                    .weight(1f)
            )
        }

        HorizontalDivider(thickness = 1.dp, color = Color.White)

        val textList = listOf(
            "Item 1",
            "Item 2",
            "Item 3",
            "Item 1",
            "Item 2",
            "Item 3",
            "Item 1",
            "Item 2",
            "Item 3",
            "Item 1",
            "Item 2",
            "Item 3",
            "Item 1",
            "Item 2",
            "Item 3",
            "Item 1",
            "Item 2",
            "Item 3",
            "Item 1",
            "Item 2",
            "Item 3",
            "Item 1",
            "Item 2",
            "Item 3",
            "Item 1",
            "Item 2",
            "Item 3"
        )
        LazyColumn(
            Modifier
                .weight(1f)
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            items(textList.size) { item ->
                Text(
                    text = textList[item],
                    color = Color.White,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
        Button(
            onClick = { /*TODO*/ },
            Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(0),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
            contentPadding = PaddingValues(8.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_add_24_white),
                contentDescription = null
            )
            Text(
                text = "Tasks", modifier = Modifier
                    .padding(start = 10.dp)
                    .weight(1f)
            )
        }

    }
}


@Composable
fun MyDay(navController: NavHostController) {
    var expanded by remember {
        mutableStateOf(false)
    }
    val calendar = Calendar.getInstance()
    val dateFormat = SimpleDateFormat("EEEE, MMMM d", Locale.getDefault())
    val formattedDate = dateFormat.format(calendar.time)

    Image(
        painter = painterResource(id = R.drawable.background),
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.statusBars)
    ) {
        Row {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Default.ArrowBack, tint = Color(0xFFfefafa), contentDescription = null)
            }
            Column {
                Text(text = "My Day", color = Color(0xFFfefafa), fontSize = 22.sp)
                Text(text = formattedDate, color = Color(0xFFfefafa), fontSize = 16.sp)
            }
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = { expanded = true }) {
                Icon(Icons.Default.MoreVert, contentDescription = null, tint = Color(0xFFfefafa))
                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    DropdownMenuItem(
                        text = { Text(text = "Change Background") },
                        onClick = { /*TODO*/ },
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_photo_24),
                                contentDescription = null
                            )
                        })
                }
            }
        }
        val textList = listOf(
            "Item 1",
            "Item 2",
            "Item 3",
            "Item 1",
            "Item 2",
            "Item 3",
            "Item 1",
            "Item 2",
            "Item 3",
            "Item 1",
            "Item 2",
            "Item 3",
            "Item 1",
            "Item 2",
            "Item 3",
            "Item 1",
            "Item 2",
            "Item 3",
            "Item 1",
            "Item 2",
            "Item 3",
            "Item 1",
            "Item 2",
            "Item 3",
            "Item 1",
            "Item 2",
            "Item 3",
            "Item 1",
            "Item 2",
            "Item 3",
            "Item 1",
            "Item 2",
            "Item 3",
            "Item 1",
            "Item 2",
            "Item 3",
            "Item 1",
            "Item 2",
            "Item 3",
            "Item 1",
            "Item 2",
            "Item 3",
            "Item 1",
            "Item 2",
            "Item 3",
            "Item 1",
            "Item 2",
            "Item 3",
            "Item 1",
            "Item 2",
            "Item 3",
            "Item 1",
            "Item 2",
            "Item 3"
        )

        Box(Modifier.fillMaxSize()) {
            LazyColumn(
                Modifier
                    .fillMaxWidth()

            ) {
                items(textList.size) { item ->
                    Card(
                        Modifier
                            .fillMaxWidth()
                            .padding(top = 3.dp)
                            , colors = CardDefaults.cardColors(Color(0xFF212121))
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically){
                            Checkbox(checked = false, onCheckedChange ={},Modifier.padding(10.dp) )
                            Text(
                                text = textList[item],
                                color = Color.White
                            )
                        }
                    }
                }
            }
            FloatingActionButton(
                onClick = {},
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .navigationBarsPadding()
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_add_24),
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
fun Tasks() {
    Text("Tasks")
}