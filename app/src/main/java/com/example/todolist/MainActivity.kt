package com.example.todolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            NavHost(navController, startDestination = "mainScreen") {
                composable("mainScreen") { MainScreen(navController) }
                composable("myDay") { MyDay() }
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
            .background(Color.Black), horizontalAlignment = Alignment.Start
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

        Divider(thickness = 1.dp, color = Color.White)

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
fun MyDay() {
    Text("My day")
}

@Composable
fun Tasks() {
    Text("Tasks")
}
