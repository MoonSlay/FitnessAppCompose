// ReportScreen.kt
package com.example.fitnessappcompose.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fitnessappcompose.utils.SharedViewModel

@Composable
fun ReportScreen(sharedViewModel: SharedViewModel = viewModel()) {
    val caloriesBurned by sharedViewModel.caloriesBurned.observeAsState(0)
    val dailyCaloriesBurned by sharedViewModel.dailyCaloriesBurned.observeAsState(0)
    val weeklyCaloriesBurned by sharedViewModel.weeklyCaloriesBurned.observeAsState(0)
    val monthlyCaloriesBurned by sharedViewModel.monthlyCaloriesBurned.observeAsState(0)

    val dailyStepCount by sharedViewModel.dailyStepCount.observeAsState(0)
    val weeklyStepCount by sharedViewModel.weeklyStepCount.observeAsState(0)
    val monthlyStepCount by sharedViewModel.monthlyStepCount.observeAsState(0)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Report Screen", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Calories Burned in Last Session: $caloriesBurned", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Daily Calories Burned: $dailyCaloriesBurned", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Weekly Calories Burned: $weeklyCaloriesBurned", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Monthly Calories Burned: $monthlyCaloriesBurned", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Daily Steps Taken: $dailyStepCount", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Weekly Steps Taken: $weeklyStepCount", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Monthly Steps Taken: $monthlyStepCount", style = MaterialTheme.typography.bodyMedium)
    }
}