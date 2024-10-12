package com.example.fitnessappcompose.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.fitnessappcompose.ui.screens.HomeScreen
import com.example.fitnessappcompose.ui.screens.ProfileScreen
import com.example.fitnessappcompose.ui.screens.ReportScreen
import com.example.fitnessappcompose.ui.screens.TrainingScreen

@Composable
fun NavigationGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(navController, startDestination = "Dashboard", modifier = modifier) {
        composable("Dashboard") { HomeScreen() }
        composable("Training") { TrainingScreen() }
        composable("Report") { ReportScreen() }
        composable("Profile") { ProfileScreen() }
    }
}