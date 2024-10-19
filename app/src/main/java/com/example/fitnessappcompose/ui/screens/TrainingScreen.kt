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
import com.example.fitnessappcompose.R
import kotlinx.coroutines.launch
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver

data class Training(
    val imageResId: Int,
    val name: String,
    val description: String,
    val exercises: List<String>,
    val instructions: String,
    val duration: String
)

val defaultTraining = Training(
    imageResId = R.drawable.breakfast_protein_smoothie,
    name = "Default Training",
    description = "Default description",
    exercises = listOf("Default exercise"),
    instructions = "Default instructions",
    duration = "Default duration"
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrainingScreen() {
    val sections = listOf(
        Triple("Morning Workouts", "Start your day with these energizing workouts", listOf(
            Training(
                R.drawable.ic_recipe,
                "Morning Yoga",
                "A relaxing yoga session to start your day.",
                exercises = listOf("Sun Salutation", "Downward Dog", "Child's Pose"),
                instructions = "1. Start with Sun Salutation...\n2. Move to Downward Dog...\n3. Finish with Child's Pose...",
                duration = "30 minutes"
            ),
            // Add more Training objects here
        )),
        // Add more sections here
    )

    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    var selectedTraining by rememberSaveable(stateSaver = TrainingSaver) { mutableStateOf(defaultTraining) }

    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetContent = {
            BottomSheetContent(selectedTraining)
        },
        sheetPeekHeight = 0.dp
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            sections.forEach { (title, description, trainings) ->
                item {
                    Section(title, description, trainings) { training ->
                        coroutineScope.launch {
                            selectedTraining = training
                            bottomSheetScaffoldState.bottomSheetState.expand()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BottomSheetContent(training: Training) {
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
            Text(text = training.name, style = MaterialTheme.typography.headlineSmall)
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
                Text(text = exercise, style = MaterialTheme.typography.bodyMedium)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Instructions", style = MaterialTheme.typography.headlineSmall)
            Text(text = training.instructions, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Duration", style = MaterialTheme.typography.headlineSmall)
            Text(text = training.duration, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

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
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(8.dp)
        ) {
            Image(
                painter = painterResource(id = training.imageResId),
                contentDescription = "Training image",
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxHeight()
            ) {
                Text(text = training.name, style = MaterialTheme.typography.bodyLarge)
                Text(text = training.description, style = MaterialTheme.typography.bodySmall)
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
            training.exercises,
            training.instructions,
            training.duration
        )
    },
    restore = { list ->
        Training(
            imageResId = list[0] as Int,
            name = list[1] as String,
            description = list[2] as String,
            exercises = list[3] as List<String>,
            instructions = list[4] as String,
            duration = list[5] as String
        )
    }
)