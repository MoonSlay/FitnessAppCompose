package com.example.fitnessappcompose.ui.screens.subscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.fitnessappcompose.ui.screens.Exercise
import com.example.fitnessappcompose.ui.screens.getExercises
import com.example.fitnessappcompose.SharedViewModel

@Composable
fun SelectExerciseScreen(navController: NavController, sharedViewModel: SharedViewModel = viewModel()) {
    val exercises = getExercises()
    var selectedExercise by remember { mutableStateOf<Exercise?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    LazyColumn {
        items(exercises) { exercise ->
            ExerciseCard(exercise) {
                selectedExercise = exercise
                showDialog = true
            }
        }
    }

    selectedExercise?.let { exercise ->
        if (showDialog) {
            SetExerciseDialog(
                exercise = exercise,
                onDismiss = { showDialog = false },
                onSave = { sets, repetitions ->
                    sharedViewModel.addExerciseToSelectedTraining(exercise, sets, repetitions)
                    navController.popBackStack()
                }
            )
        }
    }
}

@Composable
fun ExerciseCard(exercise: Exercise, onClick: () -> Unit) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(6.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() }
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = exercise.imageResId),
                contentDescription = exercise.name,
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = exercise.name, style = MaterialTheme.typography.bodyMedium)
                Text(text = exercise.description, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@Composable
fun SetExerciseDialog(
    exercise: Exercise,
    onDismiss: () -> Unit,
    onSave: (Int, Int) -> Unit
) {
    var sets by remember { mutableStateOf("") }
    var repetitions by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Set Exercise Details") },
        text = {
            Column {
                TextField(
                    value = sets,
                    onValueChange = { sets = it },
                    label = { Text("Sets") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                )
                TextField(
                    value = repetitions,
                    onValueChange = { repetitions = it },
                    label = { Text("Repetitions") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                )
            }
        },
        confirmButton = {
            Button(onClick = {
                val setsInt = sets.toIntOrNull() ?: 0
                val repetitionsInt = repetitions.toIntOrNull() ?: 0
                onSave(setsInt, repetitionsInt)
            }) {
                Text("Save")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}