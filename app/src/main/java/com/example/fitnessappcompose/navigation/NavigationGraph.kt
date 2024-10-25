// NavigationGraph.kt
package com.example.fitnessappcompose.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.fitnessappcompose.ui.screens.*
import com.example.fitnessappcompose.ui.screens.auth.LoginScreen
import com.example.fitnessappcompose.ui.screens.auth.RegisterScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fitnessappcompose.utils.SharedViewModel

@Composable
fun NavigationGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    val sharedViewModel: SharedViewModel = viewModel()

    NavHost(navController, startDestination = "login", modifier = modifier) {
        composable("login") { LoginScreen(navController = navController) }
        composable("register") { RegisterScreen(navController = navController) }
        composable("dashboard") { DashboardScreen() }
        composable("recipe") { RecipeScreen() }
        composable("training") { TrainingScreen(navController = navController, sharedViewModel = sharedViewModel) }
        composable("report_screen") {
            ReportScreen(sharedViewModel = sharedViewModel)
        }
        composable("profile") { ProfileScreen(navController = navController) }
        composable("profile_data") { ProfileDataScreen(navController = navController) }
        composable("trainingDetail") {
            TrainingDetailScreen(navController = navController, sharedViewModel = sharedViewModel)
        }
        composable("trainingSession") {
            TrainingSessionScreen(navController = navController, sharedViewModel = sharedViewModel)
        }
    }
}