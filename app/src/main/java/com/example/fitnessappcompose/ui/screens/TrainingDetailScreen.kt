// TrainingDetailScreen.kt
package com.example.fitnessappcompose.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
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
import com.example.fitnessappcompose.utils.SharedViewModel

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
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Exercises", style = MaterialTheme.typography.headlineSmall)
            LazyColumn {
                it.exercises?.forEach { exercise ->
                    exercise.sets?.let { sets ->
                        items(sets * 2) { index ->
                            if (index % 2 == 0) {
                                ExerciseSetCard(exercise, (index / 2) + 1)
                            } else {
                                RestTimeCard("15 seconds")
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Duration", style = MaterialTheme.typography.headlineSmall)
            Text(text = it.duration ?: "No duration", style = MaterialTheme.typography.bodyMedium)
        }
    }
}
@Composable
fun ExerciseSetCard(exercise: Exercise, setNumber: Int) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            exercise.imageResId?.let {
                Image(
                    painter = painterResource(id = it),
                    contentDescription = exercise.name,
                    modifier = Modifier.size(64.dp)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = "${exercise.name} - Set $setNumber", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Reps: ${exercise.repetitions}", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@Composable
fun RestTimeCard(restTime: String) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = "Rest Time", style = MaterialTheme.typography.bodyMedium)
                Text(text = restTime, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}