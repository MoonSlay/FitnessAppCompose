package com.example.fitnessappcompose.ui.screens.subscreen

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.fitnessappcompose.utils.*

@Composable
fun GoalDataScreen(navController: NavController) {
    val context = LocalContext.current
    var isEditing by remember { mutableStateOf(false) }

    if (isEditing) {
        EditGoalDataScreen(navController, context) { isEditing = false }
    } else {
        DisplayGoalDataScreen(navController) { isEditing = true }
    }
}

@Composable
fun DisplayGoalDataScreen(navController: NavController, onEditClick: () -> Unit) {
    val context = LocalContext.current
    val goalWeight = getGoalWeight(context).toIntOrNull() ?: 0
    val dailyStepCount = getGoalDailySC(context).toIntOrNull() ?: 0
    val dailyCaloriesBurned = getGoalDailyCB(context).toIntOrNull() ?: 0

    // Calculate weekly and monthly values
    val weeklyStepCount = dailyStepCount * 7
    val monthlyStepCount = dailyStepCount * 30
    val weeklyCaloriesBurned = dailyCaloriesBurned * 7
    val monthlyCaloriesBurned = dailyCaloriesBurned * 30

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Text(text = "Goal Data", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(20.dp))

        // List of goal fields
        listOf(
            "Goal Weight" to goalWeight,
            "Daily Step Count" to dailyStepCount.toString(),
            "Weekly Step Count" to weeklyStepCount.toString(),
            "Monthly Step Count" to monthlyStepCount.toString(),
            "Daily Calories Burned" to dailyCaloriesBurned.toString(),
            "Weekly Calories Burned" to weeklyCaloriesBurned.toString(),
            "Monthly Calories Burned" to monthlyCaloriesBurned.toString()
        ).forEach { (label, value) ->
            ProfileDataRow(label = label, value = value.toString())
        }

        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = onEditClick, modifier = Modifier.fillMaxWidth()) {
            Text("Edit")
        }
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = { navController.navigateUp() }, modifier = Modifier.fillMaxWidth()) {
            Text("Back")
        }
    }
}

@Composable
fun EditGoalDataScreen(navController: NavController, context: Context, onSaveClick: () -> Unit) {
    var goalWeight by remember { mutableStateOf(getGoalWeight(context)) }
    var dailyStepCount by remember { mutableStateOf(getGoalDailySC(context)) }
    var dailyCaloriesBurned by remember { mutableStateOf(getGoalDailyCB(context)) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Edit Goal Data", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(20.dp))

        // Wrap input fields in a Card for consistent appearance
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium,
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp) // Equal spacing between fields
            ) {
                OutlinedTextField(
                    value = goalWeight,
                    onValueChange = { if (it.all { char -> char.isDigit() }) goalWeight = it },
                    label = { Text("Goal Weight") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                OutlinedTextField(
                    value = dailyStepCount,
                    onValueChange = { if (it.all { char -> char.isDigit() }) dailyStepCount = it },
                    label = { Text("Daily Step Count") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                OutlinedTextField(
                    value = dailyCaloriesBurned,
                    onValueChange = { if (it.all { char -> char.isDigit() }) dailyCaloriesBurned = it },
                    label = { Text("Daily Calories Burned") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {
            // Save all relevant data
            saveGoalData(context, goalWeight, dailyStepCount, dailyCaloriesBurned)
            onSaveClick()
        }, modifier = Modifier.fillMaxWidth()) {
            Text("Save")
        }
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = { navController.navigateUp() }, modifier = Modifier.fillMaxWidth()) {
            Text("Cancel")
        }
    }
}


fun saveGoalData(context: Context, goalWeight: String, dailyStepCount: String, dailyCaloriesBurned: String) {
    // Calculate weekly and monthly values
    val weeklyStepCount = dailyStepCount.toIntOrNull()?.times(7)?.toString() ?: "0"
    val monthlyStepCount = dailyStepCount.toIntOrNull()?.times(30)?.toString() ?: "0"
    val weeklyCaloriesBurned = dailyCaloriesBurned.toIntOrNull()?.times(7)?.toString() ?: "0"
    val monthlyCaloriesBurned = dailyCaloriesBurned.toIntOrNull()?.times(30)?.toString() ?: "0"

    // Save data to shared preferences or database
    setGoalWeight(context, goalWeight)
    setGoalDailySC(context, dailyStepCount)
    setGoalWeeklySC(context, weeklyStepCount)
    setGoalMonthlySC(context, monthlyStepCount)
    setGoalDailyCB(context, dailyCaloriesBurned)
    setGoalWeeklyCB(context, weeklyCaloriesBurned)
    setGoalMonthlyCB(context, monthlyCaloriesBurned)
}
