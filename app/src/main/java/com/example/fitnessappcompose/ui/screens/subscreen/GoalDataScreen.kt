package com.example.fitnessappcompose.ui.screens.subscreen

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
    val goalWeight = getGoalWeight(context)
    val goalDailySC = getGoalDailySC(context)
    val goalWeeklySC = getGoalWeeklySC(context)
    val goalMonthlySC = getGoalMonthlySC(context)
    val goalDailyCB = getGoalDailyCB(context)
    val goalWeeklyCB = getGoalWeeklyCB(context)
    val goalMonthlyCB = getGoalMonthlyCB(context)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Goal Data", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "Goal Weight: $goalWeight")
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Daily Step Count: $goalDailySC")
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Weekly Step Count: $goalWeeklySC")
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Monthly Step Count: $goalMonthlySC")
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Daily Calories Burned: $goalDailyCB")
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Weekly Calories Burned: $goalWeeklyCB")
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Monthly Calories Burned: $goalMonthlyCB")
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = onEditClick) {
            Text("Edit")
        }
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = { navController.navigateUp() }) {
            Text("Back")
        }
    }
}

@Composable
fun EditGoalDataScreen(navController: NavController, context: Context, onSaveClick: () -> Unit) {
    var goalWeight by remember { mutableStateOf(getGoalWeight(context)) }
    var goalDailySC by remember { mutableStateOf(getGoalDailySC(context)) }
    var goalWeeklySC by remember { mutableStateOf(getGoalWeeklySC(context)) }
    var goalMonthlySC by remember { mutableStateOf(getGoalMonthlySC(context)) }
    var goalDailyCB by remember { mutableStateOf(getGoalDailyCB(context)) }
    var goalWeeklyCB by remember { mutableStateOf(getGoalWeeklyCB(context)) }
    var goalMonthlyCB by remember { mutableStateOf(getGoalMonthlyCB(context)) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text(text = "Edit Goal Data", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(20.dp))
        }
        item {
            OutlinedTextField(
                value = goalWeight,
                onValueChange = { if (it.all { char -> char.isDigit() }) goalWeight = it },
                label = { Text("Goal Weight") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Spacer(modifier = Modifier.height(10.dp))
        }
        item {
            OutlinedTextField(
                value = goalDailySC,
                onValueChange = { if (it.all { char -> char.isDigit() }) goalDailySC = it },
                label = { Text("Daily Step Count") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Spacer(modifier = Modifier.height(10.dp))
        }
        item {
            OutlinedTextField(
                value = goalWeeklySC,
                onValueChange = { if (it.all { char -> char.isDigit() }) goalWeeklySC = it },
                label = { Text("Weekly Step Count") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Spacer(modifier = Modifier.height(10.dp))
        }
        item {
            OutlinedTextField(
                value = goalMonthlySC,
                onValueChange = { if (it.all { char -> char.isDigit() }) goalMonthlySC = it },
                label = { Text("Monthly Step Count") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Spacer(modifier = Modifier.height(10.dp))
        }
        item {
            OutlinedTextField(
                value = goalDailyCB,
                onValueChange = { if (it.all { char -> char.isDigit() }) goalDailyCB = it },
                label = { Text("Daily Calories Burned") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Spacer(modifier = Modifier.height(10.dp))
        }
        item {
            OutlinedTextField(
                value = goalWeeklyCB,
                onValueChange = { if (it.all { char -> char.isDigit() }) goalWeeklyCB = it },
                label = { Text("Weekly Calories Burned") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Spacer(modifier = Modifier.height(10.dp))
        }
        item {
            OutlinedTextField(
                value = goalMonthlyCB,
                onValueChange = { if (it.all { char -> char.isDigit() }) goalMonthlyCB = it },
                label = { Text("Monthly Calories Burned") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Spacer(modifier = Modifier.height(20.dp))
        }
        item {
            Button(onClick = {
                saveGoalData(context, goalWeight, goalDailySC, goalWeeklySC, goalMonthlySC, goalDailyCB, goalWeeklyCB, goalMonthlyCB)
                onSaveClick()
            }) {
                Text("Save")
            }
            Spacer(modifier = Modifier.height(10.dp))
        }
        item {
            Button(onClick = { navController.navigateUp() }) {
                Text("Cancel")
            }
        }
    }
}

fun saveGoalData(context: Context, goalWeight: String, goalDailySC: String, goalWeeklySC: String, goalMonthlySC: String, goalDailyCB: String, goalWeeklyCB: String, goalMonthlyCB: String) {
    setGoalWeight(context, goalWeight)
    setGoalDailySC(context, goalDailySC)
    setGoalWeeklySC(context, goalWeeklySC)
    setGoalMonthlySC(context, goalMonthlySC)
    setGoalDailyCB(context, goalDailyCB)
    setGoalWeeklyCB(context, goalWeeklyCB)
    setGoalMonthlyCB(context, goalMonthlyCB)
}