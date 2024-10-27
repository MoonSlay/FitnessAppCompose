package com.example.fitnessappcompose.ui.screens

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.fitnessappcompose.MainActivity
import com.example.fitnessappcompose.utils.*
import kotlinx.coroutines.delay
import android.Manifest
import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import kotlin.math.min

@Suppress("DEPRECATION")
@Composable
fun DashboardScreen(navController: NavHostController, sharedViewModel: SharedViewModel = viewModel()) {

    // Request permission to access activity recognition for step count
    RequestActivityRecognitionPermission()

    // Retrieve username from shared preferences
    val context = LocalContext.current
    val username = remember { getUsername(context) }

    // Retrieve daily step count and daily calories burned from shared view model
    val mainActivity = context as MainActivity
    val dailyStepCount by mainActivity.stepCount.collectAsState(initial = 0)
    val dailyCaloriesBurned by sharedViewModel.dailyCaloriesBurned.observeAsState(0)

    // Retrieve goals from shared preferences
    val goalDailySC = remember { getGoalDailySC(context).toIntOrNull() ?: 0 }
    val goalDailyCB = remember { getGoalDailyCB(context).toIntOrNull() ?: 0 }

    // Check if UserGoalUtils and UserProfileUtils are empty
    val isGoalsSet = goalDailySC > 0 && goalDailyCB > 0
    val isProfileSet = username.isNotBlank()

    // List of motivational quotes
    val quotes = listOf(
        "The only bad workout is the one that didn't happen.",
        "Push yourself, because no one else is going to do it for you.",
        "Success starts with self-discipline.",
        "The body achieves what the mind believes.",
        "Don't limit your challenges, challenge your limits."
    )

    // Display a random quote
    var currentQuote by remember { mutableStateOf(quotes[0]) }

    // Change quote every 5 seconds
    LaunchedEffect(Unit) {
        while (true) {
            delay(5000)
            currentQuote = quotes.random()
        }
    }

    // Display in a LazyColumn for vertical scrolling
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        //Checks if goals and profile are set and prompts user to set them if not
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
            ) {
                if (!isGoalsSet) {
                    ClickableText(
                        text = AnnotatedString("Set up Goals!"),
                        onClick = { navController.navigate("goal_data") },
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = Color.Red,
                            fontWeight = FontWeight.Bold,
                            textDecoration = TextDecoration.Underline,
                            textAlign = TextAlign.Start
                        )
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                if (!isProfileSet) {
                    ClickableText(
                        text = AnnotatedString("Set up your Profile!"),
                        onClick = { navController.navigate("profile_data") },
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = Color.Red,
                            fontWeight = FontWeight.Bold,
                            textDecoration = TextDecoration.Underline,
                            textAlign = TextAlign.Start
                        )
                    )
                }
            }
        }

        // Welcome message
        item {
            Text(
                text = "Welcome $username!",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 20.dp)
            )
        }

        // Goal progress surface
        item {
            GoalProgressSurface(
                stepCount = dailyStepCount,
                targetSteps = goalDailySC,
                caloriesBurned = dailyCaloriesBurned,
                targetCalories = goalDailyCB
            )
        }

        // Display a motivational quote
        item {
            Text(
                text = currentQuote,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(top = 20.dp, bottom = 20.dp),
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(50.dp))
        }


        // Buttons to navigate to other screens
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly // Space buttons evenly
            ) {
                val buttonModifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .height(60.dp) // Set a fixed height for the buttons

                Button(
                    onClick = { navController.navigate("recipe") },
                    modifier = buttonModifier
                ) {
                    Text("Browse Recipes", fontSize = 20.sp) // Adjust text size for visibility
                }
                Spacer(modifier = Modifier.height(15.dp)) // Add space between buttons
                Button(
                    onClick = { navController.navigate("training") },
                    modifier = buttonModifier
                ) {
                    Text("Start Training", fontSize = 20.sp) // Adjust text size for visibility
                }
            }
        }

    }
}

// Handles the goal progress surface
@Composable
fun GoalProgressSurface(stepCount: Int, targetSteps: Int, caloriesBurned: Int, targetCalories: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            Text(
                text = "Daily Goals:",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                CircularProgressIndicatorWithText(
                    progress = min(stepCount / targetSteps.toFloat(), 1f),
                    label = "Steps",
                    value = "$stepCount/$targetSteps"
                )
                Spacer(modifier = Modifier.width(10.dp))
                CircularProgressIndicatorWithText(
                    progress = min(caloriesBurned / targetCalories.toFloat(), 1f),
                    label = "Calories",
                    value = "$caloriesBurned/$targetCalories"
                )
            }
        }
    }
}

// Custom circular progress indicator with text
@Composable
fun CircularProgressIndicatorWithText(progress: Float, label: String, value: String) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(120.dp)
    ) {
        CircularProgressIndicator(
            progress = { progress },
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.primary,
            strokeWidth = 8.dp,
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = value,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = label,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Gray
            )
        }
    }
}

// Request permission to access activity recognition for step count
@Composable
fun RequestActivityRecognitionPermission() {
    val context = LocalContext.current
    val activity = context as Activity

    if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACTIVITY_RECOGNITION) != PackageManager.PERMISSION_GRANTED) {
        ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.ACTIVITY_RECOGNITION), 1)
    }
}
