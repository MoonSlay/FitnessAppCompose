// SessionOverScreen.kt
package com.example.fitnessappcompose.ui.screens.subscreen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.fitnessappcompose.ui.theme.AppTypography
import com.example.fitnessappcompose.utils.SharedViewModel


@Composable
fun SessionOverScreen(navController: NavController, sharedViewModel: SharedViewModel = viewModel()) {
    val caloriesBurned = sharedViewModel.caloriesBurned.observeAsState()
    val training = sharedViewModel.selectedTraining.observeAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Congratulations!", style = AppTypography.headlineLarge)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "You have completed your workout.", style = AppTypography.bodyMedium)
        Spacer(modifier = Modifier.height(16.dp))
        training.value?.let {
            Text(text = "Workout: ${it.name ?: "No name"}", style = AppTypography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Exercises Completed: ${it.exercises.size}", style = AppTypography.bodyMedium)
        }
        Spacer(modifier = Modifier.height(16.dp))
        caloriesBurned.value?.let {
            Text(text = "Calories Burned: $it", style = AppTypography.bodyMedium)
        }
        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = { navController.navigate("training") }) {
            Text(text = "Done")
        }
    }
}
