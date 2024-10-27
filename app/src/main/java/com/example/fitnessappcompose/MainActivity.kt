package com.example.fitnessappcompose

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
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

    private val sensorManager: SensorManager by lazy {
        getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    private var sensor: Sensor? = null

    private val _stepCount = MutableStateFlow(0)
    val stepCount: StateFlow<Int> get() = _stepCount

    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR)

        // Retrieve step count from SharedPreferences
        val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        _stepCount.value = sharedPreferences.getInt("step_count", 0)

        sharedViewModel = ViewModelProvider(this).get(SharedViewModel::class.java)

        setContent {
            val themeViewModel: ThemeViewModel = viewModel()
            val isDarkMode by themeViewModel.isDarkMode.collectAsState()

            AppTheme(darkTheme = isDarkMode) {
                val navController = rememberNavController()

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

                Scaffold(
                    bottomBar = {
                        Log.d("MainActivity", "BottomNavigationBar is displayed")
                        BottomNavigationBar(navController)
                    },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    NavigationGraph(navController, Modifier.padding(innerPadding))
                }
            }
        }
    }

    override fun onSensorChanged(sensorEvent: SensorEvent?) {
        sensorEvent?.let { event ->
            if (event.sensor.type == Sensor.TYPE_STEP_DETECTOR) {
                _stepCount.update { it + 1 }
                // Save step count to SharedPreferences
                val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                with(sharedPreferences.edit()) {
                    putInt("step_count", _stepCount.value)
                    apply()
                }
                updateStepCount()
            }
        }
    }

    private fun updateStepCount() {
        sharedViewModel.setStepCount(1)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Not used
    }
}