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
import com.example.fitnessappcompose.utils.SharedViewModel
import kotlinx.coroutines.launch
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.ui.text.style.TextAlign

// Data classes for Exercise and Training
data class Exercise(
    val name: String,
    val description: String,
    val imageResId: Int,
    val sets: Int? = null,
    val repetitions: Int? = null,
    val duration: String? = null,
)

data class Training(
    val imageResId: Int,
    val name: String,
    val description: String,
    val exercises: List<Exercise>,
    val duration: String
)

// Default Training data
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
fun TrainingScreen(navController: NavController, sharedViewModel: SharedViewModel) {
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    var selectedTraining by rememberSaveable(stateSaver = TrainingSaver) { mutableStateOf(defaultTraining) }

    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetContent = { BottomSheetContent(selectedTraining, navController, sharedViewModel) },
        sheetPeekHeight = 0.dp
    ) {
        Column(modifier = Modifier.fillMaxSize().padding(5.dp)) {
            Text(text = "Workout", style = MaterialTheme.typography.headlineSmall, modifier = Modifier.padding(bottom = 5.dp))
            TrainingList(
                sections = getSections(),
                onTrainingSelected = { training ->
                    selectedTraining = training
                    coroutineScope.launch { bottomSheetScaffoldState.bottomSheetState.expand() }
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrainingList(
    sections: List<Triple<String, String, List<Training>>>,
    onTrainingSelected: (Training) -> Unit
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        sections.forEach { (_, _, trainings) ->
            item { TrainingSection(trainings, onTrainingSelected) }
        }
        item {
            Spacer(modifier = Modifier.height(5.dp))
            Text(text = "All Exercises", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(8.dp))
            ExerciseList(getExercises())
        }
    }
}

@Composable
fun TrainingSection(trainings: List<Training>, onTrainingClick: (Training) -> Unit) {
    Column {
        Spacer(modifier = Modifier.height(5.dp))
        trainings.forEach { training -> TrainingCard(training, onTrainingClick) }
        Spacer(modifier = Modifier.height(5.dp))
    }
}

@Composable
fun ExerciseList(exercises: List<Exercise>) {
    Column {
        exercises.forEach { exercise -> ExerciseCard(exercise) }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetContent(training: Training, navController: NavController, sharedViewModel: SharedViewModel) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = screenHeight * 0.8f)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item { TrainingInfo(training) }
        item { Spacer(modifier = Modifier.height(16.dp)) }
        item {
            Button(onClick = {
                sharedViewModel.selectTraining(training)
                navController.navigate("trainingDetail")
            }) { Text("Go to Training Detail") }
        }
    }
}

@Composable
fun TrainingInfo(training: Training) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = training.name, style = MaterialTheme.typography.headlineSmall, textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = training.description, style = MaterialTheme.typography.bodyMedium, textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(16.dp))
        Image(
            painter = painterResource(id = training.imageResId),
            contentDescription = "Training image",
            modifier = Modifier.size(200.dp).clip(CircleShape)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Exercises", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(8.dp))
        training.exercises.forEach { ExerciseRow(it) }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Duration: ${training.duration}", style = MaterialTheme.typography.bodyMedium, textAlign = TextAlign.Center)
    }
}

@Composable
fun ExerciseRow(exercise: Exercise) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = exercise.name,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f).padding(8.dp),
            textAlign = TextAlign.Center
        )
        Text(
            text = exercise.repetitions?.let { "Sets: ${exercise.sets}, Reps: $it" } ?: "Duration: ${exercise.duration}",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.weight(1f).padding(8.dp),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun TrainingCard(training: Training, onTrainingClick: (Training) -> Unit) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(6.dp),
        modifier = Modifier.fillMaxWidth().padding(8.dp).clickable { onTrainingClick(training) }
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = training.imageResId),
                contentDescription = "Training image",
                modifier = Modifier.size(64.dp).clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = training.name, style = MaterialTheme.typography.bodyMedium)
                Text(text = training.description, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@Composable
fun ExerciseCard(exercise: Exercise) {
    Surface(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        shape = MaterialTheme.shapes.medium,
        tonalElevation = 2.dp
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
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

// Saver to store training state
val TrainingSaver: Saver<Training, Any> = listSaver(
    save = { training -> listOf(training.imageResId, training.name, training.description, training.exercises.map { listOf(it.name, it.description, it.imageResId) }, training.duration) },
    restore = { list -> Training(list[0] as Int, list[1] as String, list[2] as String, (list[3] as List<List<Any>>).map { Exercise(it[0] as String, it[1] as String, it[2] as Int) }, list[4] as String) }
)


@Composable
fun getExercises(): List<Exercise> {
    return listOf(
        // Cardio Exercises
        Exercise("Jumping Jacks", "A full-body cardio exercise that increases heart rate.", R.drawable.cardio_jumping_jacks),
        Exercise("Burpees", "A high-intensity cardio and full-body workout.", R.drawable.cardio_burpees),
        Exercise("High Knees", "A cardio exercise that strengthens legs and boosts endurance.", R.drawable.cardio_high_knees),
        Exercise("Mountain Climbers", "A cardio exercise that also engages the core and upper body.", R.drawable.cardio_mountain_climbers),
        Exercise("Jump Rope", "A cardio exercise for building endurance and coordination.", R.drawable.cardio_jump_ropes),
        Exercise("Sprints", "Short, fast runs to build speed and burn calories.", R.drawable.cardio_sprint),
        Exercise("Running", "A cardio activity that strengthens the heart and lungs.", R.drawable.cardio_running),
        Exercise("Cycling", "A low-impact cardio exercise great for endurance.", R.drawable.cardio_cycling),

        // Strength Training (Upper Body)
        Exercise("Push-ups", "A bodyweight exercise to strengthen chest and triceps.", R.drawable.strength_push_up),
        Exercise("Bench Press", "A strength exercise for building chest and upper body muscles.", R.drawable.strength_bench_press),
        Exercise("Overhead Press", "Targets shoulders and upper back muscles.", R.drawable.strength_overhead_press),
        Exercise("Pull-ups", "Strengthens back and arm muscles.", R.drawable.strength_pull_ups),
        Exercise("Bent-over Rows", "A strength exercise to target the back muscles.", R.drawable.strength_bend_over_rows),
        Exercise("Dumbbell Bicep Curls", "Isolates and strengthens the bicep muscles.", R.drawable.strength_dumbbell_bicep_curls),
        Exercise("Triceps Dips", "Targets the triceps and builds arm strength.", R.drawable.strength_triceps_dips),

        // Strength Training (Lower Body)
        Exercise("Squats", "A lower-body exercise to build leg and glute strength.", R.drawable.strength_squats),
        Exercise("Lunges", "Strengthens legs and improves balance.", R.drawable.strength_lunges),
        Exercise("Deadlifts", "Targets hamstrings, glutes, and lower back.", R.drawable.strength_deadlifts),
        Exercise("Leg Press", "Works on quadriceps and glutes.", R.drawable.strength_leg_press),
        Exercise("Glute Bridges", "Strengthens glutes and hamstrings.", R.drawable.strength_glute_bridges),
        Exercise("Step-ups", "Engages legs and glutes while improving balance.", R.drawable.strength_step_ups),
        Exercise("Calf Raises", "Strengthens calf muscles.", R.drawable.strength_calf_raises),

        // Core/Abdominal Exercises
        Exercise("Planks", "A core workout good for stability and endurance.", R.drawable.cardio_plank),
        Exercise("Russian Twists", "Targets obliques and strengthens core muscles.", R.drawable.cardio_russian_twists),
        Exercise("Bicycle Crunches", "Engages the entire core with a focus on obliques.", R.drawable.core_bicycle_crunches),
        Exercise("Leg Raises", "Strengthens the lower abdominal muscles.", R.drawable.core_leg_raises),
        Exercise("Sit-ups", "A traditional core exercise for building abdominal strength.", R.drawable.core_sit_ups),
        Exercise("Mountain Climbers", "Also engages the core while being a cardio exercise.", R.drawable.core_mountain_climbers),
        Exercise("Side Plank", "Strengthens the core and oblique muscles.", R.drawable.core_side_plank),

        // Flexibility and Mobility Exercises
        Exercise("Forward Fold", "Stretches the hamstrings and back muscles.", R.drawable.flexibility_forward_fold),
        Exercise("Child's Pose", "A restorative stretch for the back and hips.", R.drawable.flexibility_childs_pose),
        Exercise("Cobra Stretch", "Stretches the spine and opens the chest.", R.drawable.flexibility_cobra_stretch),
        Exercise("Cat-Cow Stretch", "Increases spine flexibility and relieves tension.", R.drawable.flexibility_cat_cow_stretch),
        Exercise("Downward Dog", "Stretches the hamstrings, calves, and shoulders.", R.drawable.flexibility_downward_dog),
        Exercise("Hip Flexor Stretch", "Stretches the hip muscles for mobility.", R.drawable.flexibility_hip_flexor_stretch),
        Exercise("Pigeon Pose", "Targets hip flexibility and lower back.", R.drawable.flexibility_pigeon_pose)
    )
}

@Composable
fun getSections(): List<Triple<String, String, List<Training>>> {
    val exercises = getExercises()

    return listOf(
        // Morning Workouts
        Triple("Morning Workouts", "Start your day with these energizing workouts", listOf(
            Training(
                R.drawable.ic_recipe,
                "Morning Cardio Blast",
                "A quick cardio workout to boost your energy for the day.",
                exercises = listOf(
                    exercises[0].copy(sets = 4, repetitions = 15), // Jumping Jacks
                    exercises[1].copy(sets = 3, repetitions = 15), // Burpees
                    exercises[2].copy(sets = 3, repetitions = 20), // High Knees
                    exercises[3].copy(sets = 3, repetitions = 15)  // Mountain Climbers
                ),
                duration = "25 minutes"
            )
        )),

        // Upper Body Strength
        Triple("Upper Body Strength", "Build strength and tone your upper body", listOf(
            Training(
                R.drawable.ic_recipe,
                "Upper Body Strength Workout",
                "Strengthen your chest, shoulders, and arms with this workout.",
                exercises = listOf(
                    exercises[8].copy(sets = 4, repetitions = 10), // Push-ups
                    exercises[9].copy(sets = 3, repetitions = 12), // Bench Press
                    exercises[10].copy(sets = 3, repetitions = 10), // Overhead Press
                    exercises[11].copy(sets = 3, repetitions = 10)  // Pull-ups
                ),
                duration = "30 minutes"
            )
        )),

        // Lower Body Strength
        Triple("Lower Body Strength", "Strengthen your legs and glutes with these exercises", listOf(
            Training(
                R.drawable.ic_recipe,
                "Lower Body Strength Workout",
                "Focus on building strength in your legs and glutes.",
                exercises = listOf(
                    exercises[15].copy(sets = 4, repetitions = 12), // Squats
                    exercises[16].copy(sets = 3, repetitions = 12), // Lunges
                    exercises[17].copy(sets = 4, repetitions = 10),  // Deadlifts
                    exercises[18].copy(sets = 3, repetitions = 12)   // Leg Press
                ),
                duration = "30 minutes"
            )
        )),

        // Core Workout
        Triple("Core Strength", "Engage and strengthen your core muscles", listOf(
            Training(
                R.drawable.ic_recipe,
                "Core Strength Workout",
                "Target your abdominal muscles with this effective routine.",
                exercises = listOf(
                    exercises[24].copy(sets = 3, repetitions = 15), // Planks
                    exercises[25].copy(sets = 4, repetitions = 15), // Russian Twists
                    exercises[26].copy(sets = 3, repetitions = 12),  // Bicycle Crunches
                    exercises[27].copy(sets = 3, repetitions = 12)   // Leg Raises
                ),
                duration = "20 minutes"
            )
        )),

        // Flexibility and Mobility
        Triple("Flexibility and Mobility", "Improve flexibility and range of motion", listOf(
            Training(
                R.drawable.ic_recipe,
                "Flexibility and Mobility Routine",
                "Enhance your flexibility with these restorative stretches.",
                exercises = listOf(
                    exercises[27].copy(sets = 1, repetitions = 1), // Forward Fold
                    exercises[28].copy(sets = 1, repetitions = 1), // Child's Pose
                    exercises[29].copy(sets = 1, repetitions = 1), // Cobra Stretch
                    exercises[30].copy(sets = 1, repetitions = 1)  // Cat-Cow Stretch
                ),
                duration = "15 minutes"
            )
        )),

        // Full Body Workout
        Triple("Full Body Circuit", "A high-intensity workout targeting all muscle groups", listOf(
            Training(
                R.drawable.ic_recipe,
                "Full Body Circuit",
                "Challenge your entire body with this intense circuit.",
                exercises = listOf(
                    exercises[0].copy(sets = 4, repetitions = 15), // Jumping Jacks
                    exercises[3].copy(sets = 3, repetitions = 15), // Mountain Climbers
                    exercises[4].copy(sets = 3, repetitions = 10),  // Jump Rope
                    exercises[15].copy(sets = 3, repetitions = 12)  // Squats
                ),
                duration = "25 minutes"
            )
        )),

        // Endurance and Cardio
        Triple("Endurance Training", "Build stamina and endurance with this workout", listOf(
            Training(
                R.drawable.ic_recipe,
                "Endurance Training",
                "Focus on building your cardiovascular endurance.",
                exercises = listOf(
                    exercises[6].copy(sets = 3, repetitions = 15), // Running
                    exercises[7].copy(sets = 4, repetitions = 12), // Cycling
                    exercises[5].copy(sets = 3, repetitions = 10),  // Sprints
                    exercises[1].copy(sets = 3, repetitions = 10)   // Burpees
                ),
                duration = "30 minutes"
            )
        )),
    )
}

