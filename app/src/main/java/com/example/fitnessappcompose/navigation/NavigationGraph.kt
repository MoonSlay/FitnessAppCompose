// NavigationGraph.kt
package com.example.fitnessappcompose.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.fitnessappcompose.ui.screens.*
import com.example.fitnessappcompose.ui.screens.subscreen.*
import com.example.fitnessappcompose.utils.SharedViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun NavigationGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    val sharedViewModel: SharedViewModel = viewModel()

    NavHost(navController, startDestination = "home", modifier = modifier) {
        composable("home") { DashboardScreen(navController = navController) }
        composable("recipe") { RecipeScreen() }
        composable("training") { TrainingScreen(navController = navController, sharedViewModel = sharedViewModel) }
        composable("report") { ReportScreen(sharedViewModel = sharedViewModel) }
        composable("profile") { ProfileScreen(navController = navController) }
        composable("profile_data") { ProfileDataScreen(navController = navController) }
        composable("goal_data") { GoalDataScreen(navController = navController) }
        composable("trainingDetail") { TrainingDetailScreen(navController = navController, sharedViewModel = sharedViewModel) }
        composable("trainingSession") { TrainingSessionScreen(navController = navController, sharedViewModel = sharedViewModel) }
        composable("settings") { SettingsScreen(navController = navController) }
        composable("selectExercise") { SelectExerciseScreen(navController, sharedViewModel) }
        composable("sessionOver") { SessionOverScreen(navController = navController, sharedViewModel = sharedViewModel) }
    }
}