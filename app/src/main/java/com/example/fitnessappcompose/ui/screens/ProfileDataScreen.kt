// ProfileDataScreen.kt
package com.example.fitnessappcompose.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.fitnessappcompose.utils.*

@Composable
fun ProfileDataScreen(navController: NavController) {
    val context = LocalContext.current
    val fullName = getFullName(context)
    val username = getUsername(context)
    val gender = getGender(context)
    val age = getAge(context)
    val height = getHeight(context)
    val weight = getWeight(context)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Profile Data", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "Full Name: $fullName")
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Username: $username")
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Gender: $gender")
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Age: $age")
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Height: $height")
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Weight: $weight")
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = { navController.navigateUp() }) {
            Text("Back")
        }
    }
}