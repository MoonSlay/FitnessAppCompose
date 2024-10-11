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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                var showAnimation by remember { mutableStateOf(true) }
                val navController = rememberNavController()

                if (showAnimation) {
                    StartupAnimation(onAnimationEnd = { showAnimation = false })
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