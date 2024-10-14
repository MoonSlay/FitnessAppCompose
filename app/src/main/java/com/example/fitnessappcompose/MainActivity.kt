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
import com.example.fitnessappcompose.ui.screens.setup.ProfileSetupScreen
import com.example.fitnessappcompose.ui.startup.StartupAnimation
import com.example.fitnessappcompose.utils.isUserProfileSetUp
import com.example.fitnessappcompose.utils.setUserProfileSetUp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                var showAnimation by remember { mutableStateOf(false) } // Set to false to skip animation
                var showProfileSetup by remember { mutableStateOf(false) }
                val navController = rememberNavController()
                val coroutineScope = rememberCoroutineScope()

                LaunchedEffect(Unit) {
                    coroutineScope.launch(Dispatchers.IO) {
                        showProfileSetup = !isUserProfileSetUp(this@MainActivity)
                    }
                }

                // Comment out the StartupAnimation block
                /*
                if (showAnimation) {
                    StartupAnimation(onAnimationEnd = { showAnimation = false })
                } else
                */
                if (showProfileSetup) {
                    ProfileSetupScreen(onProfileSetupComplete = {
                        coroutineScope.launch(Dispatchers.IO) {
                            setUserProfileSetUp(this@MainActivity)
                            showProfileSetup = false
                            navController.navigate("setup_goal")
                        }
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