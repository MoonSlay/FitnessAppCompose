// SharedViewModel.kt
package com.example.fitnessappcompose.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fitnessappcompose.ui.screens.Training

class SharedViewModel : ViewModel() {
    private val _selectedTraining = MutableLiveData<Training>()
    val selectedTraining: LiveData<Training> get() = _selectedTraining

    fun selectTraining(training: Training) {
        _selectedTraining.value = training
    }
}