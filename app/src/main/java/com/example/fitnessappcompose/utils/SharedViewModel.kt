// SharedViewModel.kt
package com.example.fitnessappcompose.utils

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.fitnessappcompose.ui.screens.Training
import java.util.*

class SharedViewModel(application: Application) : AndroidViewModel(application) {
    private val sharedPreferences = application.getSharedPreferences("fitness_prefs", Context.MODE_PRIVATE)
    private val _selectedTraining = MutableLiveData<Training>()
    val selectedTraining: LiveData<Training> get() = _selectedTraining

    private val _caloriesBurned = MutableLiveData<Int>()
    val caloriesBurned: LiveData<Int> get() = _caloriesBurned

    private val _totalCaloriesBurned = MutableLiveData<Int>(sharedPreferences.getInt("total_calories_burned", 0))
    val totalCaloriesBurned: LiveData<Int> get() = _totalCaloriesBurned

    private val _dailyCaloriesBurned = MutableLiveData<Int>(sharedPreferences.getInt("daily_calories_burned", 0))
    val dailyCaloriesBurned: LiveData<Int> get() = _dailyCaloriesBurned

    private val _weeklyCaloriesBurned = MutableLiveData<Int>(sharedPreferences.getInt("weekly_calories_burned", 0))
    val weeklyCaloriesBurned: LiveData<Int> get() = _weeklyCaloriesBurned

    private val _monthlyCaloriesBurned = MutableLiveData<Int>(sharedPreferences.getInt("monthly_calories_burned", 0))
    val monthlyCaloriesBurned: LiveData<Int> get() = _monthlyCaloriesBurned

    init {
        resetCaloriesIfNeeded()
    }

    fun setCaloriesBurned(calories: Int) {
        _caloriesBurned.value = calories

        val newTotal = (_totalCaloriesBurned.value ?: 0) + calories
        _totalCaloriesBurned.value = newTotal
        sharedPreferences.edit().putInt("total_calories_burned", newTotal).apply()

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

    private fun resetCaloriesIfNeeded() {
        val currentDate = Calendar.getInstance()
        val lastResetDate = Calendar.getInstance().apply {
            timeInMillis = sharedPreferences.getLong("last_reset_date", 0)
        }

        if (currentDate.get(Calendar.DAY_OF_YEAR) != lastResetDate.get(Calendar.DAY_OF_YEAR)) {
            _dailyCaloriesBurned.value = 0
            sharedPreferences.edit().putInt("daily_calories_burned", 0).apply()
        }

        if (currentDate.get(Calendar.WEEK_OF_YEAR) != lastResetDate.get(Calendar.WEEK_OF_YEAR)) {
            _weeklyCaloriesBurned.value = 0
            sharedPreferences.edit().putInt("weekly_calories_burned", 0).apply()
        }

        if (currentDate.get(Calendar.MONTH) != lastResetDate.get(Calendar.MONTH)) {
            _monthlyCaloriesBurned.value = 0
            sharedPreferences.edit().putInt("monthly_calories_burned", 0).apply()
        }

        sharedPreferences.edit().putLong("last_reset_date", currentDate.timeInMillis).apply()
    }

    fun selectTraining(training: Training) {
        _selectedTraining.value = training
    }
}