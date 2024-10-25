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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Report Screen", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Calories Burned: $caloriesBurned", style = MaterialTheme.typography.bodyMedium)
    }
}