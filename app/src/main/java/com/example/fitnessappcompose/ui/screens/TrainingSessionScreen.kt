// TrainingSessionScreen.kt
package com.example.fitnessappcompose.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.fitnessappcompose.utils.SharedViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import android.util.Log

data class ExerciseSetState(
    val exercise: Exercise,
    val setNumber: Int,
    var checked: Boolean = false,
    var restTime: Int = 15
)



@Composable
fun TrainingSessionScreen(navController: NavController, sharedViewModel: SharedViewModel = viewModel()) {
    val training = sharedViewModel.selectedTraining.observeAsState()
    var timer by remember { mutableStateOf(0) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        while (true) {
            delay(1000L)
            timer++
        }
    }

    training.value?.let {
        val exerciseSetStates = remember {
            it.exercises?.flatMap { exercise ->
                (1..(exercise.sets ?: 0)).map { setNumber ->
                    ExerciseSetState(exercise, setNumber)
                }
            }?.toMutableStateList() ?: mutableStateListOf()
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(text = "Training Session", style = MaterialTheme.typography.headlineLarge)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Time: $timer seconds", style = MaterialTheme.typography.bodyMedium)
            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(exerciseSetStates) { state ->
                    ExerciseCheckCard(state)
                }
            }
            Button(
                onClick = {
                    val checkedExercises = exerciseSetStates.count { it.checked }
                    Log.d("TrainingSessionScreen", "Checked Exercises: $checkedExercises")
                    val caloriesBurned = computeCaloriesBurned(checkedExercises, timer)
                    Log.d("TrainingSessionScreen", "Calories Burned: $caloriesBurned")
                    sharedViewModel.setCaloriesBurned(caloriesBurned)
                    navController.navigate("report_screen")
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Finish Workout")
            }
        }
    }
}

fun computeCaloriesBurned(checkedExercises: Int, duration: Int): Int {
    val calories = checkedExercises * (duration / 1) * 5
    Log.d("computeCaloriesBurned", "Checked Exercises: $checkedExercises, Duration: $duration, Calories: $calories")
    return calories
}

@Composable
fun ExerciseCheckCard(state: ExerciseSetState) {
    var checked by remember { mutableStateOf(state.checked) }
    var restTime by remember { mutableStateOf(state.restTime) }
    val scope = rememberCoroutineScope()

    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(8.dp)
            ) {
                Checkbox(
                    checked = checked,
                    onCheckedChange = {
                        checked = it
                        state.checked = it
                        if (it) {
                            scope.launch {
                                while (restTime > 0) {
                                    delay(1000L)
                                    restTime--
                                    state.restTime = restTime
                                }
                            }
                        }
                    }
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(text = "${state.exercise.name} - Set ${state.setNumber}", style = MaterialTheme.typography.bodyMedium)
                    Text(text = "Reps: ${state.exercise.repetitions}", style = MaterialTheme.typography.bodySmall)
                }
            }
            if (checked) {
                RestTimeCard(if (restTime > 0) "$restTime seconds" else "Resting done")
            }
        }
    }
}