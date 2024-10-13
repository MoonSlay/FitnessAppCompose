package com.example.fitnessappcompose.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.fitnessappcompose.ui.screens.DashboardScreen
import com.example.fitnessappcompose.ui.screens.ProfileDataScreen
import com.example.fitnessappcompose.ui.screens.ProfileScreen
import com.example.fitnessappcompose.ui.screens.RecipeScreen
import com.example.fitnessappcompose.ui.screens.ReportScreen
import com.example.fitnessappcompose.ui.screens.setup.SetupGoalScreen
import com.example.fitnessappcompose.ui.screens.TrainingScreen

@Composable
fun NavigationGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(navController, startDestination = "Dashboard", modifier = modifier) {
        composable("Dashboard") { DashboardScreen() }
        composable("Recipe") { RecipeScreen() }
        composable("Training") { TrainingScreen() }
        composable("Report") { ReportScreen() }
        composable("Profile") { ProfileScreen(navController = navController) }
        composable("setup_goal") { SetupGoalScreen(navController = navController) }
        composable("profile_data") { ProfileDataScreen(navController = navController) }
    }
}