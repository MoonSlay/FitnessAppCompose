package com.example.fitnessappcompose.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.fitnessappcompose.ui.screens.*
import com.example.fitnessappcompose.ui.screens.subscreen.*
import com.example.fitnessappcompose.SharedViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun NavigationGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    toggleDarkMode: (Boolean) -> Unit,
    isDarkMode: Boolean
) {
    val sharedViewModel: SharedViewModel = viewModel()

    NavHost(navController, startDestination = "home", modifier = modifier) {
        // Dashboard screens
        composable("home") { DashboardScreen(navController = navController) }

        // Recipe screens
        composable("recipe") { RecipeScreen() }

        // Training screens
        composable("training") { TrainingScreen(navController = navController, sharedViewModel = sharedViewModel) }
        composable("trainingDetail") { TrainingDetailScreen(navController = navController, sharedViewModel = sharedViewModel) }
        composable("trainingSession") { TrainingSessionScreen(navController = navController, sharedViewModel = sharedViewModel) }
        composable("selectExercise") { SelectExerciseScreen(navController, sharedViewModel) }
        composable("sessionOver") { SessionOverScreen(navController = navController, sharedViewModel = sharedViewModel) }

        // Report screens
        composable("report") { ReportScreen(sharedViewModel = sharedViewModel) }

        // Profile screens
        composable("profile") { ProfileScreen(navController = navController, toggleDarkMode = toggleDarkMode, isDarkMode = isDarkMode) }
        composable("profile_data") { ProfileDataScreen(navController = navController) }
        composable("goal_data") { GoalDataScreen(navController = navController) }
        composable("about_us") { AboutUsScreen(navController = navController) }

    }
}