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
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.fitnessappcompose.navigation.BottomNavigationBar
import com.example.fitnessappcompose.navigation.NavigationGraph
import com.example.fitnessappcompose.ui.theme.AppTheme
import com.example.fitnessappcompose.utils.SharedViewModel
import com.example.fitnessappcompose.utils.ThemeViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class MainActivity : ComponentActivity(), SensorEventListener {

    // Lazy initialization of the SensorManager for handling sensors
    private val sensorManager: SensorManager by lazy {
        getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    // Reference to the step detector sensor
    private var sensor: Sensor? = null

    // StateFlow to track the step count in real-time
    private val _stepCount = MutableStateFlow(0)
    val stepCount: StateFlow<Int> get() = _stepCount

    // SharedViewModel for managing shared data between components
    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Enable edge-to-edge display

        // Initialize the step detector sensor
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR)

        // Initialize the ViewModel for shared data
        sharedViewModel = ViewModelProvider(this)[SharedViewModel::class.java]

        // Retrieve saved step count from SharedPreferences when the app starts
        val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        _stepCount.value = sharedPreferences.getInt("step_count", 0)

        // Set the UI content
        setContent {
            val themeViewModel: ThemeViewModel = viewModel()  // Get the ThemeViewModel instance
            val isDarkMode by themeViewModel.isDarkMode.collectAsState()  // Observe theme mode

            AppTheme(darkTheme = isDarkMode) {  // Apply the selected theme
                val navController = rememberNavController()
                registerSensor()  // Register sensor for step detection

                Scaffold(
                    bottomBar = { BottomNavigationBar(navController) },  // Bottom navigation bar
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    // Main navigation graph for the app
                    NavigationGraph(navController, Modifier.padding(innerPadding))
                }
            }
        }
    }

    /**
     * Registers and unregisters the step detector sensor to listen for step events.
     */
    @Composable
    private fun registerSensor() {
        LaunchedEffect(Unit) {
            sensor?.let {
                sensorManager.registerListener(this@MainActivity, it, SensorManager.SENSOR_DELAY_FASTEST)
            }
        }
        DisposableEffect(Unit) {
            onDispose {
                sensorManager.unregisterListener(this@MainActivity)
            }
        }
    }

    /**
     * Callback that triggers each time the step detector sensor detects a step.
     * Updates the step count, saves it to SharedPreferences, and updates the SharedViewModel.
     */
    override fun onSensorChanged(sensorEvent: SensorEvent?) {
        if (sensorEvent?.sensor?.type == Sensor.TYPE_STEP_DETECTOR) {
            _stepCount.update { it + 1 }  // Increment step count
            saveStepCount()  // Save step count in SharedPreferences
            sharedViewModel.setStepCount(1)  // Update SharedViewModel
        }
    }

    /**
     * Saves the current step count to SharedPreferences for persistent storage.
     */
    private fun saveStepCount() {
        val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putInt("step_count", _stepCount.value)
            apply()
        }
    }

    /**
     * Required callback for sensor accuracy changes. Not used in this implementation.
     */
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Not used
    }
}
