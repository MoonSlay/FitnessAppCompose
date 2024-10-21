package com.example.fitnessappcompose.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.fitnessappcompose.ui.screens.*
import com.example.fitnessappcompose.ui.screens.auth.LoginScreen
import com.example.fitnessappcompose.ui.screens.auth.RegisterScreen

@Composable
fun NavigationGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(navController, startDestination = "login", modifier = modifier) {
        composable("login") { LoginScreen(navController = navController) }
        composable("register") { RegisterScreen(navController = navController) }
        composable("dashboard") { DashboardScreen() }
        composable("recipe") { RecipeScreen() }
        composable("training") { TrainingScreen(navController = navController) }
        composable("report") { ReportScreen() }
        composable("profile") { ProfileScreen(navController = navController) }
        composable("profile_data") { ProfileDataScreen(navController = navController) }
        composable("trainingDetail/{trainingName}") { backStackEntry ->
            TrainingDetailScreen(
                navController = navController,
                trainingName = backStackEntry.arguments?.getString("trainingName") ?: ""
            )
        }
    }
}