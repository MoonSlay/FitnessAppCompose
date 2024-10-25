// SharedViewModel.kt
package com.example.fitnessappcompose.utils

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.fitnessappcompose.ui.screens.Training

class SharedViewModel(application: Application) : AndroidViewModel(application) {
    private val sharedPreferences = application.getSharedPreferences("fitness_prefs", Context.MODE_PRIVATE)
    private val _selectedTraining = MutableLiveData<Training>()
    val selectedTraining: LiveData<Training> get() = _selectedTraining

    private val _caloriesBurned = MutableLiveData<Int>()
    val caloriesBurned: LiveData<Int> get() = _caloriesBurned

    private val _totalCaloriesBurned = MutableLiveData<Int>(sharedPreferences.getInt("total_calories_burned", 0))
    val totalCaloriesBurned: LiveData<Int> get() = _totalCaloriesBurned

    fun setCaloriesBurned(calories: Int) {
        _caloriesBurned.value = calories
        val newTotal = (_totalCaloriesBurned.value ?: 0) + calories
        _totalCaloriesBurned.value = newTotal
        sharedPreferences.edit().putInt("total_calories_burned", newTotal).apply()
    }

    fun selectTraining(training: Training) {
        _selectedTraining.value = training
    }
}