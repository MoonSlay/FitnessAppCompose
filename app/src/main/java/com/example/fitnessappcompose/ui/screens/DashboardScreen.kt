package com.example.fitnessappcompose.ui.screens

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
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
import com.example.fitnessappcompose.utils.getUsername
import kotlinx.coroutines.delay
import android.Manifest
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fitnessappcompose.utils.SharedViewModel
import kotlin.math.min

@Composable
fun DashboardScreen(sharedViewModel: SharedViewModel = viewModel()) {
    RequestActivityRecognitionPermission()
    val context = LocalContext.current
    val username = remember { getUsername(context) }
    val mainActivity = context as MainActivity
    val dailyStepCount by mainActivity.stepCount.collectAsState(initial = 0)
    val dailyCaloriesBurned by sharedViewModel.dailyCaloriesBurned.observeAsState(0)

    val quotes = listOf(
        "The only bad workout is the one that didn't happen.",
        "Push yourself, because no one else is going to do it for you.",
        "Success starts with self-discipline.",
        "The body achieves what the mind believes.",
        "Don't limit your challenges, challenge your limits."
    )

    var currentQuote by remember { mutableStateOf(quotes[0]) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(5000)
            currentQuote = quotes.random()
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        item {
            Text(
                text = "Welcome $username,",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 50.dp)
            )
        }

        item {
            GoalProgressSurface(stepCount = dailyStepCount, targetSteps = 100, caloriesBurned = dailyCaloriesBurned, targetCalories = 100)
        }

        item {
            Text(
                text = currentQuote,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(top = 20.dp)
            )
        }
    }
}

@Composable
fun GoalProgressSurface(stepCount: Int, targetSteps: Int, caloriesBurned: Int, targetCalories: Int) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp)
            .height(150.dp),
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = 4.dp
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            CircularProgressIndicatorWithText(
                progress = min(stepCount / targetSteps.toFloat(), 1f),
                label = "Steps",
                value = "$stepCount/$targetSteps"
            )
            CircularProgressIndicatorWithText(
                progress = min(caloriesBurned / targetCalories.toFloat(), 1f),
                label = "Calories",
                value = "$caloriesBurned/$targetCalories"
            )
        }
    }
}

@Composable
fun CircularProgressIndicatorWithText(progress: Float, label: String, value: String) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(120.dp)
    ) {
        CircularProgressIndicator(
            progress = progress,
            color = MaterialTheme.colorScheme.primary,
            strokeWidth = 8.dp,
            modifier = Modifier.fillMaxSize()
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

@Composable
fun RequestActivityRecognitionPermission() {
    val context = LocalContext.current
    val activity = context as Activity

    if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACTIVITY_RECOGNITION) != PackageManager.PERMISSION_GRANTED) {
        ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.ACTIVITY_RECOGNITION), 1)
    }
}