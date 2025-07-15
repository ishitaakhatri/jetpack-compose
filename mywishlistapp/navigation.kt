package com.example.mywishlistapp

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import wishViewModel

@Composable
fun navigation(viewModel: wishViewModel = viewModel(),navController: NavHostController = rememberNavController()){

    NavHost(
        navController = navController,
        startDestination = screen.HomeScreen.route
    ){

        composable(screen.HomeScreen.route){
            HomeView(navController,viewModel)
        }
        composable(screen.AddScreen.route + "/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.LongType
                    defaultValue = 0L
                    nullable = false
                }
            )
        ) { entry ->
            val id = entry.arguments?.getLong("id") ?: 0L
            AddEditDetailView(id = id, viewModel = viewModel, navController = navController)
        }
    }
}