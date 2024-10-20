// NavigationGraph.kt
package com.example.fitnessappcompose.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.fitnessappcompose.ui.screens.*
import com.example.fitnessappcompose.ui.screens.setup.SetupGoalScreen

@Composable
fun NavigationGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(navController, startDestination = Screen.Dashboard.route, modifier = modifier) {
        composable(Screen.Dashboard.route) { DashboardScreen() }
        composable(Screen.Recipe.route) { RecipeScreen() }
        composable(Screen.Training.route) { TrainingScreen(navController = navController) }
        composable(Screen.Report.route) { ReportScreen() }
        composable(Screen.Profile.route) { ProfileScreen(navController = navController) }
        composable(Screen.SetupGoal.route) { SetupGoalScreen(navController = navController) }
        composable(Screen.ProfileData.route) { ProfileDataScreen(navController = navController) }
        composable("trainingDetail/{trainingName}") { backStackEntry ->
            TrainingDetailScreen(
                navController = navController,
                trainingName = backStackEntry.arguments?.getString("trainingName") ?: ""
            )
        }
    }
}