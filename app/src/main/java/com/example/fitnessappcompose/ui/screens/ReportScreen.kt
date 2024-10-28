package com.example.fitnessappcompose.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fitnessappcompose.SharedViewModel
import com.example.fitnessappcompose.utils.getGoalDailySC
import com.example.fitnessappcompose.utils.getGoalWeeklySC
import com.example.fitnessappcompose.utils.getGoalMonthlySC
import com.example.fitnessappcompose.utils.getGoalDailyCB
import com.example.fitnessappcompose.utils.getGoalWeeklyCB
import com.example.fitnessappcompose.utils.getGoalMonthlyCB
import kotlin.math.min

@Composable
fun ReportScreen(sharedViewModel: SharedViewModel = viewModel()) {
    val dailyStepCount by sharedViewModel.dailyStepCount.observeAsState(0)
    val weeklyStepCount by sharedViewModel.weeklyStepCount.observeAsState(0)
    val monthlyStepCount by sharedViewModel.monthlyStepCount.observeAsState(0)

    val dailyCaloriesBurned by sharedViewModel.dailyCaloriesBurned.observeAsState(0)
    val weeklyCaloriesBurned by sharedViewModel.weeklyCaloriesBurned.observeAsState(0)
    val monthlyCaloriesBurned by sharedViewModel.monthlyCaloriesBurned.observeAsState(0)

    var selectedStepTab by remember { mutableStateOf(0) }
    var selectedCaloriesTab by remember { mutableStateOf(0) }

    val stepCount = when (selectedStepTab) {
        0 -> dailyStepCount
        1 -> weeklyStepCount
        2 -> monthlyStepCount
        else -> dailyStepCount
    }
    val calories = when (selectedCaloriesTab) {
        0 -> dailyCaloriesBurned
        1 -> weeklyCaloriesBurned
        2 -> monthlyCaloriesBurned
        else -> dailyCaloriesBurned
    }

    // Fetching targets based on selected tab
    val targetSteps = when (selectedStepTab) {
        0 -> getGoalDailySC(LocalContext.current).toIntOrNull() ?: 0 // Daily target
        1 -> getGoalWeeklySC(LocalContext.current).toIntOrNull() ?: 0 // Weekly target (example: 10,000 steps * 7 days)
        2 -> getGoalMonthlySC(LocalContext.current).toIntOrNull() ?: 0 // Monthly target (example: 10,000 steps * 30 days)
        else -> 10000 // Default to daily target
    }

    val targetCalories = when (selectedCaloriesTab) {
        0 -> getGoalDailyCB(LocalContext.current).toIntOrNull() ?: 0 // Daily target
        1 -> getGoalWeeklyCB(LocalContext.current).toIntOrNull() ?: 0 // Weekly target (example: 2000 calories * 7 days)
        2 -> getGoalMonthlyCB(LocalContext.current).toIntOrNull() ?: 0 // Monthly target (example: 2000 calories * 30 days)
        else -> 2000 // Default to daily target
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        // Step Count Section
        Text(text = "Step Count", style = MaterialTheme.typography.headlineSmall)

        TabRow(selectedTabIndex = selectedStepTab) {
            listOf("Daily", "Weekly", "Monthly").forEachIndexed { index, title ->
                Tab(
                    selected = selectedStepTab == index,
                    onClick = { selectedStepTab = index },
                    text = { Text(text = title) }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        GoalProgressBar(
            progressValue = stepCount,
            targetValue = targetSteps,
            label = "Steps",
            targetLabel = "Step Goal"
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Calories Burned Section
        Text(text = "Calories Burned", style = MaterialTheme.typography.headlineSmall)

        TabRow(selectedTabIndex = selectedCaloriesTab) {
            listOf("Daily", "Weekly", "Monthly").forEachIndexed { index, title ->
                Tab(
                    selected = selectedCaloriesTab == index,
                    onClick = { selectedCaloriesTab = index },
                    text = { Text(text = title) }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        GoalProgressBar(
            progressValue = calories,
            targetValue = targetCalories,
            label = "Calories",
            targetLabel = "Calorie Goal"
        )
    }
}

@Composable
fun GoalProgressBar(progressValue: Int, targetValue: Int, label: String, targetLabel: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicatorWithText(
            progress = min(progressValue / targetValue.toFloat(), 1f),
            label = label,
            value = "$progressValue/$targetValue"
        )
    }
}
