package com.example.fitnessappcompose

import android.annotation.SuppressLint
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

    private val _isDarkMode = MutableStateFlow(false)
    val isDarkMode: StateFlow<Boolean> get() = _isDarkMode

    // SharedViewModel for managing shared data between components
    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Enable edge-to-edge display

        // Initialize the step detector sensor
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR)

        // Initialize the ViewModel for shared data
        sharedViewModel = ViewModelProvider(this)[SharedViewModel::class.java]

        // Retrieve saved step count and dark mode state from SharedPreferences when the app starts
        val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        _stepCount.value = sharedPreferences.getInt("step_count", 0)
        _isDarkMode.value = sharedPreferences.getBoolean("dark_mode", false)

        // Set the UI content
        setContent {
            val isDarkMode by _isDarkMode.collectAsState()

            AppTheme(darkTheme = isDarkMode) {
                val navController = rememberNavController()
                registerSensor()

                Scaffold(
                    bottomBar = { BottomNavigationBar(navController) },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    NavigationGraph(navController, Modifier.padding(innerPadding), ::toggleDarkMode, isDarkMode)
                }
            }
        }
    }

    private fun toggleDarkMode(isDark: Boolean) {
        _isDarkMode.value = isDark
        saveDarkModeState(isDark)
    }

    private fun saveDarkModeState(isDark: Boolean) {
        val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putBoolean("dark_mode", isDark)
            apply()
        }
    }

    @SuppressLint("ComposableNaming")
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

    override fun onSensorChanged(sensorEvent: SensorEvent?) {
        if (sensorEvent?.sensor?.type == Sensor.TYPE_STEP_DETECTOR) {
            _stepCount.update { it + 1 }  // Increment step count
            saveStepCount()  // Save step count in SharedPreferences

            // Assuming 0.04 calories burned per step
            val caloriesBurnedPerStep = 0.04
            val caloriesBurned = (1 * caloriesBurnedPerStep).toInt() // 1 step's worth of calories

            // Update sharedViewModel with the calories burned
            sharedViewModel.setCaloriesBurned(caloriesBurned)
            sharedViewModel.setStepCount(1)  // Update step count in SharedViewModel
        }
    }


    private fun saveStepCount() {
        val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putInt("step_count", _stepCount.value)
            apply()
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Not used
    }
}