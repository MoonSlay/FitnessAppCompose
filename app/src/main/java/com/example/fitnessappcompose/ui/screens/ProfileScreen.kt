package com.example.fitnessappcompose.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.fitnessappcompose.utils.getFullName

@Composable
fun ProfileScreen(navController: NavController) {
    val context = LocalContext.current
    val fullName = getFullName(context)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = fullName, style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = { navController.navigate("profile_data") }) {
            Text("Profile")
        }
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = { navController.navigate("goal_data") }) {
            Text("Goals")
        }
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = { navController.navigate("settings") }) {
            Text("Settings")
        }
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = { /* Navigate to About Us */ }) {
            Text("About Us")
        }
    }
}