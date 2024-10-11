package com.example.fitnessappcompose

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun NavigationGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(navController, startDestination = "Home", modifier = modifier) {
        composable("Home") { HomeScreen() }
        composable("Profile") { ProfileScreen() }
        composable("Settings") { SettingsScreen() }
    }
}