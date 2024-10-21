// AuthViewModel.kt
package com.example.fitnessappcompose

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> get() = _isLoggedIn

    fun login() {
        viewModelScope.launch {
            _isLoggedIn.value = true
            Log.d("AuthViewModel", "User logged in")
        }
    }

    fun logout() {
        viewModelScope.launch {
            _isLoggedIn.value = false
            Log.d("AuthViewModel", "User logged out")
        }
    }
}