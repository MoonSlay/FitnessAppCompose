package com.example.fitnessappcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.compose.AppTheme
import com.example.fitnessappcompose.navigation.BottomNavigationBar
import com.example.fitnessappcompose.navigation.NavigationGraph
import com.example.fitnessappcompose.ui.screens.ProfileSetupScreen
import com.example.fitnessappcompose.ui.startup.StartupAnimation
import com.example.fitnessappcompose.utils.isUserProfileSetUp
import com.example.fitnessappcompose.utils.setUserProfileSetUp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                var showAnimation by remember { mutableStateOf(true) }
                var showProfileSetup by remember { mutableStateOf(!isUserProfileSetUp(this)) }
                val navController = rememberNavController()

                if (showAnimation) {
                    StartupAnimation(onAnimationEnd = { showAnimation = false })
                } else if (showProfileSetup) {
                    ProfileSetupScreen(onProfileSetupComplete = {
                        setUserProfileSetUp(this)
                        showProfileSetup = false
                    }, navController = navController)
                } else {
                    Scaffold(
                        bottomBar = { BottomNavigationBar(navController) },
                        modifier = Modifier.fillMaxSize()
                    ) { innerPadding ->
                        NavigationGraph(navController, Modifier.padding(innerPadding))
                    }
                }
            }
        }
    }
}