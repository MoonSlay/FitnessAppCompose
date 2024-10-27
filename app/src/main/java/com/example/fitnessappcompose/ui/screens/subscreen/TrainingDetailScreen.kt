// TrainingDetailScreen.kt
package com.example.fitnessappcompose.ui.screens.subscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.fitnessappcompose.R
import com.example.fitnessappcompose.ui.screens.Exercise
import com.example.fitnessappcompose.utils.SharedViewModel

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
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Exercises", style = MaterialTheme.typography.headlineSmall)
            LazyColumn {
                it.exercises.forEach { exercise ->
                    exercise.sets?.let { sets ->
                        items(sets * 2) { index ->
                            if (index % 2 == 0) {
                                ExerciseSetCard(exercise, (index / 2) + 1, sharedViewModel)
                            }
                        }
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { navController.navigate("selectExercise") }) {
                        Text(text = "Add Exercise")
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { navController.navigate("trainingSession") }) {
                        Text(text = "Start Training")
                    }
                }
            }
        }
    }
}

@Composable
fun ExerciseSetCard(exercise: Exercise, setNumber: Int, sharedViewModel: SharedViewModel) {
    val context = LocalContext.current

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
                Text(text = exercise.name, style = MaterialTheme.typography.bodyMedium)
                Text(text = "Reps: ${exercise.repetitions}", style = MaterialTheme.typography.bodySmall)
            }
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = { sharedViewModel.removeSetFromExercise(context, exercise) }) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_delete),
                    contentDescription = "Delete Set"
                )
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