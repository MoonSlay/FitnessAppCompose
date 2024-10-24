package com.example.fitnessappcompose.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.fitnessappcompose.R
import kotlinx.coroutines.launch
import kotlinx.coroutines.CoroutineScope
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver

data class Exercise(
    val name: String?,
    val description: String?,
    val imageResId: Int?,
    val sets: Int? = null,
    val repetitions: Int? = null,
    val duration: String? = null
)

data class Training(
    val imageResId: Int?,
    val name: String?,
    val description: String?,
    val exercises: List<Exercise>?,
    val duration: String?
)

val defaultTraining = Training(
    imageResId = R.drawable.breakfast_protein_smoothie,
    name = "Default Training",
    description = "Default description",
    exercises = listOf(
        Exercise("Default Exercise 1", "Default description 1", R.drawable.breakfast_steak_eggs),
        Exercise("Default Exercise 2", "Default description 2", R.drawable.breakfast_tofu_scramble)
    ),
    duration = "Default duration"
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrainingScreen(navController: NavController) {
    val exercises = getExercises()
    val sections = getSections(exercises)
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    var selectedTraining by rememberSaveable(stateSaver = TrainingSaver) { mutableStateOf(defaultTraining) }

    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetContent = {
            BottomSheetContent(selectedTraining, navController)
        },
        sheetPeekHeight = 0.dp
    ) {
        TrainingList(sections, selectedTraining, { selectedTraining = it }, bottomSheetScaffoldState, coroutineScope)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrainingList(
    sections: List<Triple<String, String, List<Training>>>,
    selectedTraining: Training,
    onTrainingSelected: (Training) -> Unit,
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    coroutineScope: CoroutineScope
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        sections.forEach { (title, description, trainings) ->
            item {
                Section(title, description, trainings) { training ->
                    onTrainingSelected(training)
                    coroutineScope.launch {
                        bottomSheetScaffoldState.bottomSheetState.expand()
                    }
                }
            }
        }
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "All Exercises", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(8.dp))
            ExerciseList(sections)
        }
    }
}

@Composable
fun ExerciseList(sections: List<Triple<String, String, List<Training>>>) {
    Column {
        sections.flatMap { it.third }.flatMap { it.exercises ?: emptyList() }.distinct().forEach { exercise ->
            ExerciseCard(exercise)
        }
    }
}

@Composable
fun BottomSheetContent(training: Training, navController: NavController) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = screenHeight * 0.8f)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text(text = training.name ?: "", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = training.description ?: "", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(16.dp))
            training.imageResId?.let {
                Image(
                    painter = painterResource(id = it),
                    contentDescription = "Training image",
                    modifier = Modifier
                        .size(200.dp)
                        .clip(CircleShape)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Exercises", style = MaterialTheme.typography.headlineSmall)
            training.exercises?.forEach { exercise ->
                Text(text = exercise.name ?: "", style = MaterialTheme.typography.bodyMedium)
                Text(text = exercise.description ?: "", style = MaterialTheme.typography.bodySmall)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Duration", style = MaterialTheme.typography.headlineSmall)
            Text(text = training.duration ?: "", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { navController.navigate("trainingDetail/${training.name}") }) {
                Text("Go to Training Detail")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Section(title: String, description: String, trainings: List<Training>, onTrainingClick: (Training) -> Unit) {
    Column {
        Text(text = title, style = MaterialTheme.typography.headlineSmall)
        Text(text = description, style = MaterialTheme.typography.bodySmall)
        Spacer(modifier = Modifier.height(16.dp))
        trainings.forEach { training ->
            TrainingCard(training, onTrainingClick)
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun TrainingCard(training: Training, onTrainingClick: (Training) -> Unit) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onTrainingClick(training) }
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                training.imageResId?.let {
                    Image(
                        painter = painterResource(id = it),
                        contentDescription = "Training image",
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(text = training.name ?: "", style = MaterialTheme.typography.bodyMedium)
                    Text(text = training.description ?: "", style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}


@Composable
fun ExerciseCard(exercise: Exercise) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = MaterialTheme.shapes.medium,
        tonalElevation = 2.dp
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
                Text(text = exercise.name ?: "", style = MaterialTheme.typography.bodyMedium)
                Text(text = exercise.description ?: "", style = MaterialTheme.typography.bodySmall)
                exercise.sets?.let { sets ->
                    exercise.repetitions?.let { repetitions ->
                        Text(text = "Sets: $sets, Repetitions: $repetitions", style = MaterialTheme.typography.bodySmall)
                    }
                }
                exercise.duration?.let { duration ->
                    Text(text = "Duration: $duration", style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}

val TrainingSaver: Saver<Training, Any> = listSaver(
    save = { training ->
        listOf(
            training.imageResId,
            training.name,
            training.description,
            training.exercises?.map { listOf(it.name, it.description, it.imageResId) }, // Save exercises as a list of lists
            training.duration
        )
    },
    restore = { list ->
        Training(
            imageResId = list[0] as Int?,
            name = list[1] as String?,
            description = list[2] as String?,
            exercises = (list[3] as List<List<Any>>?)?.map { Exercise(it[0] as String?, it[1] as String?, it[2] as Int?) }, // Restore exercises from a list of lists
            duration = list[4] as String?
        )
    }
)

fun getExercises(): List<Exercise> {
    return listOf(
        Exercise("Sun Salutation", "A series of poses to warm up the body.", R.drawable.breakfast_steak_eggs, sets = 3, repetitions = 10),
        Exercise("Downward Dog", "A foundational yoga pose.", R.drawable.breakfast_protein_smoothie, duration = "2 minutes"),
        Exercise("Child's Pose", "A resting pose.", R.drawable.breakfast_quinoa_stuffed_peppers, duration = "1 minute"),
        Exercise("Jumping Jacks", "A full-body exercise.", R.drawable.breakfast_tofu_scramble, sets = 4, repetitions = 15),
        Exercise("Burpees", "A high-intensity full-body exercise.", R.drawable.breakfast_buckwheat_pancakes, sets = 3, repetitions = 12),
        Exercise("High Knees", "A cardio exercise.", R.drawable.breakfast_chickpea_salad_sandwich, duration = "3 minutes"),
        Exercise("Push-ups", "An upper body strength exercise.", R.drawable.breakfast_quinoa_stuffed_peppers, sets = 3, repetitions = 10),
        Exercise("Squats", "A lower body strength exercise.", R.drawable.breakfast_buckwheat_pancakes, sets = 4, repetitions = 15),
        Exercise("Lunges", "A lower body exercise.", R.drawable.breakfast_buckwheat_pancakes, sets = 3, repetitions = 12)
    )
}

fun getSections(exercises: List<Exercise>): List<Triple<String, String, List<Training>>> {
    return listOf(
        Triple("Morning Workouts", "Start your day with these energizing workouts", listOf(
            Training(
                R.drawable.ic_recipe,
                "Morning Yoga",
                "A relaxing yoga session to start your day.",
                exercises = exercises.subList(0, 3),
                duration = "30 minutes"
            ),
            Training(
                R.drawable.ic_recipe,
                "Cardio Blast",
                "High-intensity cardio workout.",
                exercises = exercises.subList(3, 6),
                duration = "20 minutes"
            ),
            Training(
                R.drawable.ic_recipe,
                "Strength Training",
                "Build muscle with this strength training routine.",
                exercises = exercises.subList(6, 9),
                duration = "40 minutes"
            )
        ))
        // Add more sections here
    )
}