// ProfileScreen.kt
package com.example.fitnessappcompose.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.fitnessappcompose.AuthViewModel

@Composable
fun ProfileScreen(navController: NavController) {
    val authViewModel: AuthViewModel = viewModel()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Profile Screen", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = { navController.navigate("profile_data") }) {
            Text("Profile")
        }
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = { navController.navigate("goal_data") }) {
            Text("Goals")
        }
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = {
            authViewModel.logout()
            navController.navigate("login") {
                popUpTo("dashboard") { inclusive = true }
            }
        }) {
            Text("Logout")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    val navController = rememberNavController()
    ProfileScreen(navController = navController)
}