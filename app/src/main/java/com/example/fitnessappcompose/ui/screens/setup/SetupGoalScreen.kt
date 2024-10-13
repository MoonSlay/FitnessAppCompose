package com.example.fitnessappcompose.ui.screens.setup

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.fitnessappcompose.utils.setGoal

@Composable
fun SetupGoalScreen(navController: NavController) {
    var goal by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Setup Goal Screen", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = goal,
            onValueChange = { goal = it },
            label = { Text("Goal") },
            keyboardOptions = KeyboardOptions.Default,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        if (errorMessage.isNotEmpty()) {
            Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
            Spacer(modifier = Modifier.height(16.dp))
        }

        Button(onClick = {
            Log.d("SetupGoalScreen", "Complete button clicked")
            if (goal.isEmpty()) {
                errorMessage = "Goal is required"
            } else {
                try {
                    setGoal(context, goal)
                    navController.navigate("dashboard")
                } catch (e: Exception) {
                    Log.e("SetupGoalScreen", "Error saving goal", e)
                }
            }
        }) {
            Text("Complete")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SetupGoalScreenPreview() {
    val navController = rememberNavController()
    SetupGoalScreen(navController = navController)
}