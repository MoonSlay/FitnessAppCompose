// TrainingDetailScreen.kt
package com.example.fitnessappcompose.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.fitnessappcompose.R

// TrainingDetailScreen.kt
@Composable
fun TrainingDetailScreen(navController: NavController, sharedViewModel: SharedViewModel = viewModel()) {
    val training = sharedViewModel.selectedTraining.observeAsState()

    training.value?.let {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = it.name ?: "No name", style = MaterialTheme.typography.headlineLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = it.description ?: "No description", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(16.dp))
            it.imageResId?.let { resId ->
                Image(
                    painter = painterResource(id = resId),
                    contentDescription = "Training image",
                    modifier = Modifier
                        .size(200.dp)
                        .clip(CircleShape)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Exercises", style = MaterialTheme.typography.headlineSmall)
            it.exercises?.forEach { exercise ->
                Text(text = exercise.name ?: "No name", style = MaterialTheme.typography.bodyMedium)
                Text(text = exercise.description ?: "No description", style = MaterialTheme.typography.bodySmall)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Duration", style = MaterialTheme.typography.headlineSmall)
            Text(text = it.duration ?: "No duration", style = MaterialTheme.typography.bodyMedium)
        }
    }
}