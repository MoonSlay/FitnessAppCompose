// TrainingDetailScreen.kt
package com.example.fitnessappcompose.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.fitnessappcompose.R

@Composable
fun TrainingDetailScreen(navController: NavController, trainingName: String) {
    // Dummy data for demonstration
    val training = Training(
        imageResId = R.drawable.ic_recipe,
        name = trainingName,
        description = "Detailed description for $trainingName",
        exercises = listOf(
            Exercise("Exercise 1", "Description for Exercise 1", R.drawable.breakfast_steak_eggs),
            Exercise("Exercise 2", "Description for Exercise 2", R.drawable.breakfast_tofu_scramble),
            Exercise("Exercise 3", "Description for Exercise 3", R.drawable.breakfast_protein_smoothie)
        ),
        instructions = "Detailed instructions for $trainingName",
        duration = "45 minutes"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = training.name, style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = training.description, style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Image(
            painter = painterResource(id = training.imageResId),
            contentDescription = "Training image",
            modifier = Modifier
                .size(200.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Exercises", style = MaterialTheme.typography.headlineSmall)
        training.exercises.forEach { exercise ->
            Text(text = exercise.name, style = MaterialTheme.typography.bodyMedium)
            Text(text = exercise.description, style = MaterialTheme.typography.bodySmall)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Instructions", style = MaterialTheme.typography.headlineSmall)
        Text(text = training.instructions, style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Duration", style = MaterialTheme.typography.headlineSmall)
        Text(text = training.duration, style = MaterialTheme.typography.bodyMedium)
    }
}