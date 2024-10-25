// SharedViewModel.kt
package com.example.fitnessappcompose.ui.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    private val _selectedTraining = MutableLiveData<Training>()
    val selectedTraining: LiveData<Training> get() = _selectedTraining

    fun selectTraining(training: Training) {
        _selectedTraining.value = training
    }
}