package com.example.fitnessappcompose.utils

import android.content.Context
import android.util.Log

fun isUserProfileSetUp(context: Context): Boolean {
    val sharedPreferences = context.getSharedPreferences("user_profile", Context.MODE_PRIVATE)
    return sharedPreferences.getBoolean("is_set_up", false)
}

fun setUserProfileSetUp(context: Context) {
    val sharedPreferences = context.getSharedPreferences("user_profile", Context.MODE_PRIVATE)
    with(sharedPreferences.edit()) {
        putBoolean("is_set_up", true)
        apply()
    }
    Log.d("UserProfileUtils", "User profile setup state saved")
}