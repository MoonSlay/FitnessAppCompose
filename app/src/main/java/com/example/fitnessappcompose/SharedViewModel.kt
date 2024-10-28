// SharedViewModel.kt
package com.example.fitnessappcompose

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.fitnessappcompose.ui.screens.Exercise
import com.example.fitnessappcompose.ui.screens.Training
import java.util.*

class SharedViewModel(application: Application) : AndroidViewModel(application) {
    private val sharedPreferences = application.getSharedPreferences("fitness_prefs", Context.MODE_PRIVATE)

    private val _caloriesBurned = MutableLiveData<Int>()
    val caloriesBurned: LiveData<Int> get() = _caloriesBurned

    private val _dailyCaloriesBurned = MutableLiveData<Int>(sharedPreferences.getInt("daily_calories_burned", 0))
    val dailyCaloriesBurned: LiveData<Int> get() = _dailyCaloriesBurned

    private val _weeklyCaloriesBurned = MutableLiveData<Int>(sharedPreferences.getInt("weekly_calories_burned", 0))
    val weeklyCaloriesBurned: LiveData<Int> get() = _weeklyCaloriesBurned

    private val _monthlyCaloriesBurned = MutableLiveData<Int>(sharedPreferences.getInt("monthly_calories_burned", 0))
    val monthlyCaloriesBurned: LiveData<Int> get() = _monthlyCaloriesBurned

    private val _dailyStepCount = MutableLiveData<Int>(sharedPreferences.getInt("daily_step_count", 0))
    val dailyStepCount: LiveData<Int> get() = _dailyStepCount

    private val _weeklyStepCount = MutableLiveData<Int>(sharedPreferences.getInt("weekly_step_count", 0))
    val weeklyStepCount: LiveData<Int> get() = _weeklyStepCount

    private val _monthlyStepCount = MutableLiveData<Int>(sharedPreferences.getInt("monthly_step_count", 0))
    val monthlyStepCount: LiveData<Int> get() = _monthlyStepCount

    init {
        resetCountsIfNeeded()
    }

    // Update the calories burned based on the number of calories burned
    fun setCaloriesBurned(calories: Int) {
        _caloriesBurned.value = calories

        val newDailyTotal = (_dailyCaloriesBurned.value ?: 0) + calories
        _dailyCaloriesBurned.value = newDailyTotal
        sharedPreferences.edit().putInt("daily_calories_burned", newDailyTotal).apply()

        val newWeeklyTotal = (_weeklyCaloriesBurned.value ?: 0) + calories
        _weeklyCaloriesBurned.value = newWeeklyTotal
        sharedPreferences.edit().putInt("weekly_calories_burned", newWeeklyTotal).apply()

        val newMonthlyTotal = (_monthlyCaloriesBurned.value ?: 0) + calories
        _monthlyCaloriesBurned.value = newMonthlyTotal
        sharedPreferences.edit().putInt("monthly_calories_burned", newMonthlyTotal).apply()
    }

    // Update the step count and calories burned based on the number of steps taken
    fun setStepCount(steps: Int) {

        val newDailyTotal = (_dailyStepCount.value ?: 0) + steps
        _dailyStepCount.value = newDailyTotal
        sharedPreferences.edit().putInt("daily_step_count", newDailyTotal).apply()

        val newWeeklyTotal = (_weeklyStepCount.value ?: 0) + steps
        _weeklyStepCount.value = newWeeklyTotal
        sharedPreferences.edit().putInt("weekly_step_count", newWeeklyTotal).apply()

        val newMonthlyTotal = (_monthlyStepCount.value ?: 0) + steps
        _monthlyStepCount.value = newMonthlyTotal
        sharedPreferences.edit().putInt("monthly_step_count", newMonthlyTotal).apply()
    }

    // Reset the daily, weekly, and monthly counts if the date has changed
    private fun resetCountsIfNeeded() {
        val currentDate = Calendar.getInstance()
        val lastResetDate = Calendar.getInstance().apply {
            timeInMillis = sharedPreferences.getLong("last_reset_date", 0)
        }

        if (currentDate.get(Calendar.DAY_OF_YEAR) != lastResetDate.get(Calendar.DAY_OF_YEAR)) {
            _dailyCaloriesBurned.value = 0
            _dailyStepCount.value = 0
            sharedPreferences.edit().putInt("daily_calories_burned", 0).apply()
            sharedPreferences.edit().putInt("daily_step_count", 0).apply()
        }

        if (currentDate.get(Calendar.WEEK_OF_YEAR) != lastResetDate.get(Calendar.WEEK_OF_YEAR)) {
            _weeklyCaloriesBurned.value = 0
            _weeklyStepCount.value = 0
            sharedPreferences.edit().putInt("weekly_calories_burned", 0).apply()
            sharedPreferences.edit().putInt("weekly_step_count", 0).apply()
        }

        if (currentDate.get(Calendar.MONTH) != lastResetDate.get(Calendar.MONTH)) {
            _monthlyCaloriesBurned.value = 0
            _monthlyStepCount.value = 0
            sharedPreferences.edit().putInt("monthly_calories_burned", 0).apply()
            sharedPreferences.edit().putInt("monthly_step_count", 0).apply()
        }

        sharedPreferences.edit().putLong("last_reset_date", currentDate.timeInMillis).apply()
    }

    private val _selectedTraining = MutableLiveData<Training?>()
    val selectedTraining: MutableLiveData<Training?> get() = _selectedTraining

    fun selectTraining(training: Training) {
        _selectedTraining.value = training
    }

    fun addExerciseToSelectedTraining(exercise: Exercise, sets: Int, repetitions: Int) {
        _selectedTraining.value?.let { training ->
            val updatedExercise = exercise.copy(sets = sets, repetitions = repetitions)
            val updatedExercises = training.exercises.toMutableList().apply { add(updatedExercise) }
            _selectedTraining.value = training.copy(exercises = updatedExercises)
        }
    }

    fun removeSetFromExercise(context: Context, exercise: Exercise) {
        _selectedTraining.value?.let { training ->
            val updatedExercises = training.exercises.map {
                if (it == exercise && it.sets != null && it.sets > 0) {
                    it.copy(sets = it.sets - 1)
                } else {
                    it
                }
            }
            _selectedTraining.value = training.copy(exercises = updatedExercises)
            Toast.makeText(context, "Exercise Removed", Toast.LENGTH_SHORT).show()
        }
    }

}