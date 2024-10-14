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
    NavHost(navController, startDestination = Screen.Dashboard.route, modifier = modifier) {
        composable(Screen.Dashboard.route) { DashboardScreen() }
        composable(Screen.Recipe.route) { RecipeScreen() }
        composable(Screen.Training.route) { TrainingScreen() }
        composable(Screen.Report.route) { ReportScreen() }
        composable(Screen.Profile.route) { ProfileScreen(navController = navController) }
        composable(Screen.SetupGoal.route) { SetupGoalScreen(navController = navController) }
        composable(Screen.ProfileData.route) { ProfileDataScreen(navController = navController) }
    }
}