package com.example.todolist

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

@Composable
fun NavHost(navController: NavHostController, db: AppDatabase) {
    androidx.navigation.compose.NavHost(navController, startDestination = "mainScreen") {
        composable("mainScreen", enterTransition = {
            slideIntoContainer(
                animationSpec = tween(300, easing = EaseOut),
                towards = AnimatedContentTransitionScope.SlideDirection.End
            )
        },
            exitTransition = {
                slideOutOfContainer(
                    animationSpec = tween(300, easing = EaseIn),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                )
            }) { MainScreen(navController, db) }
        composable("myDay") { MyDay(navController, db) }
        composable("tasks") {Tasks(navController, db)}
        composable(
            route = "newlist/{screenNameId}",
            arguments = listOf(navArgument("screenNameId") { type = NavType.IntType })
        ) { backStackEntry ->
            val screenNameId = backStackEntry.arguments?.getInt("screenNameId")
            screenNameId?.let {
                NewListsComposable(navController, db, screenNameId)
            }
        }
    }

}