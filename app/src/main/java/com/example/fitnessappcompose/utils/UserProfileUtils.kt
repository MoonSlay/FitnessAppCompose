// UserProfileUtils.kt
package com.example.fitnessappcompose.utils

import android.content.Context
import android.util.Log

fun setFullName(context: Context, fullName: String) {
    val sharedPreferences = context.getSharedPreferences("user_profile", Context.MODE_PRIVATE)
    with(sharedPreferences.edit()) {
        putString("full_name", fullName)
        apply()
    }
    Log.d("UserProfileUtils", "Full name saved: $fullName")
}

fun getFullName(context: Context): String {
    val sharedPreferences = context.getSharedPreferences("user_profile", Context.MODE_PRIVATE)
    return sharedPreferences.getString("full_name", "") ?: ""
}

fun setUsername(context: Context, username: String) {
    val sharedPreferences = context.getSharedPreferences("user_profile", Context.MODE_PRIVATE)
    with(sharedPreferences.edit()) {
        putString("username", username)
        apply()
    }
    Log.d("UserProfileUtils", "Username saved: $username")
}

fun getUsername(context: Context): String {
    val sharedPreferences = context.getSharedPreferences("user_profile", Context.MODE_PRIVATE)
    return sharedPreferences.getString("username", "") ?: ""
}

fun setGender(context: Context, gender: String) {
    val sharedPreferences = context.getSharedPreferences("user_profile", Context.MODE_PRIVATE)
    with(sharedPreferences.edit()) {
        putString("gender", gender)
        apply()
    }
    Log.d("UserProfileUtils", "Gender saved: $gender")
}

fun getGender(context: Context): String {
    val sharedPreferences = context.getSharedPreferences("user_profile", Context.MODE_PRIVATE)
    return sharedPreferences.getString("gender", "") ?: ""
}

fun setAge(context: Context, age: String) {
    val sharedPreferences = context.getSharedPreferences("user_profile", Context.MODE_PRIVATE)
    with(sharedPreferences.edit()) {
        putString("age", age)
        apply()
    }
    Log.d("UserProfileUtils", "Age saved: $age")
}

fun getAge(context: Context): String {
    val sharedPreferences = context.getSharedPreferences("user_profile", Context.MODE_PRIVATE)
    return sharedPreferences.getString("age", "") ?: ""
}

fun setHeight(context: Context, height: String) {
    val sharedPreferences = context.getSharedPreferences("user_profile", Context.MODE_PRIVATE)
    with(sharedPreferences.edit()) {
        putString("height", height)
        apply()
    }
    Log.d("UserProfileUtils", "Height saved: $height")
}

fun getHeight(context: Context): String {
    val sharedPreferences = context.getSharedPreferences("user_profile", Context.MODE_PRIVATE)
    return sharedPreferences.getString("height", "") ?: ""
}

fun setWeight(context: Context, weight: String) {
    val sharedPreferences = context.getSharedPreferences("user_profile", Context.MODE_PRIVATE)
    with(sharedPreferences.edit()) {
        putString("weight", weight)
        apply()
    }
    Log.d("UserProfileUtils", "Weight saved: $weight")
}

fun getWeight(context: Context): String {
    val sharedPreferences = context.getSharedPreferences("user_profile", Context.MODE_PRIVATE)
    return sharedPreferences.getString("weight", "") ?: ""
}