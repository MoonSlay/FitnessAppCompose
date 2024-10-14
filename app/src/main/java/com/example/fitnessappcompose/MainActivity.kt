package com.example.fitnessappcompose

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.compose.AppTheme
import com.example.fitnessappcompose.navigation.BottomNavigationBar
import com.example.fitnessappcompose.navigation.NavigationGraph
import com.example.fitnessappcompose.ui.screens.setup.ProfileSetupScreen
import com.example.fitnessappcompose.ui.startup.StartupAnimation
import com.example.fitnessappcompose.utils.isUserProfileSetUp
import com.example.fitnessappcompose.utils.setUserProfileSetUp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity(), SensorEventListener {

    private val sensorManager: SensorManager by lazy {
        getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    private var sensor: Sensor? = null

    private val _stepCount = MutableStateFlow(0)
    val stepCount: StateFlow<Int> get() = _stepCount

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR)
        sensorManager.registerListener(this, sensor!!, SensorManager.SENSOR_DELAY_FASTEST)

        setContent {
            AppTheme {
                var showAnimation by remember { mutableStateOf(false) } // Set to false to skip animation
                var showProfileSetup by remember { mutableStateOf(!isUserProfileSetUp(this)) }
                val navController = rememberNavController()

                if (showAnimation) {
                    StartupAnimation(onAnimationEnd = { showAnimation = false })
                } else if (showProfileSetup) {
                    ProfileSetupScreen(onProfileSetupComplete = {
                        setUserProfileSetUp(this)
                        showProfileSetup = false
                        navController.navigate("setup_goal") // Ensure navigation to setup_goal
                    }, navController = navController)
                } else {
                    Scaffold(
                        bottomBar = { BottomNavigationBar(navController) },
                        modifier = Modifier.fillMaxSize()
                    ) { innerPadding ->
                        NavigationGraph(navController, Modifier.padding(innerPadding))
                    }
                }
            }
        }
    }

    override fun onSensorChanged(sensorEvent: SensorEvent?) {
        sensorEvent?.let { event ->
            if (event.sensor.type == Sensor.TYPE_STEP_DETECTOR) {
                _stepCount.update { it + 1 }
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Not used
    }
}

