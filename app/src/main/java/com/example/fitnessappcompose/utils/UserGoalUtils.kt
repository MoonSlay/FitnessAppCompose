// UserGoalUtils.kt
package com.example.fitnessappcompose.utils

import android.content.Context
import android.util.Log

fun setGoalWeight(context: Context, goalWeight: String) {
    val sharedPreferences = context.getSharedPreferences("user_goals", Context.MODE_PRIVATE)
    with(sharedPreferences.edit()) {
        putString("goal_weight", goalWeight)
        apply()
    }
    Log.d("UserGoalUtils", "Goal weight saved: $goalWeight")
}

fun getGoalWeight(context: Context): String {
    val sharedPreferences = context.getSharedPreferences("user_goals", Context.MODE_PRIVATE)
    return sharedPreferences.getString("goal_weight", "") ?: ""
}

fun setGoalDailySC(context: Context, goalDailySC: String) {
    val sharedPreferences = context.getSharedPreferences("user_goals", Context.MODE_PRIVATE)
    with(sharedPreferences.edit()) {
        putString("goal_daily_sc", goalDailySC)
        apply()
    }
    Log.d("UserGoalUtils", "Daily step count saved: $goalDailySC")
}

fun getGoalDailySC(context: Context): String {
    val sharedPreferences = context.getSharedPreferences("user_goals", Context.MODE_PRIVATE)
    return sharedPreferences.getString("goal_daily_sc", "") ?: ""
}

fun setGoalWeeklySC(context: Context, goalWeeklySC: String) {
    val sharedPreferences = context.getSharedPreferences("user_goals", Context.MODE_PRIVATE)
    with(sharedPreferences.edit()) {
        putString("goal_weekly_sc", goalWeeklySC)
        apply()
    }
    Log.d("UserGoalUtils", "Weekly step count saved: $goalWeeklySC")
}

fun getGoalWeeklySC(context: Context): String {
    val sharedPreferences = context.getSharedPreferences("user_goals", Context.MODE_PRIVATE)
    return sharedPreferences.getString("goal_weekly_sc", "") ?: ""
}

fun setGoalMonthlySC(context: Context, goalMonthlySC: String) {
    val sharedPreferences = context.getSharedPreferences("user_goals", Context.MODE_PRIVATE)
    with(sharedPreferences.edit()) {
        putString("goal_monthly_sc", goalMonthlySC)
        apply()
    }
    Log.d("UserGoalUtils", "Monthly step count saved: $goalMonthlySC")
}

fun getGoalMonthlySC(context: Context): String {
    val sharedPreferences = context.getSharedPreferences("user_goals", Context.MODE_PRIVATE)
    return sharedPreferences.getString("goal_monthly_sc", "") ?: ""
}

fun setGoalDailyCB(context: Context, goalDailyCB: String) {
    val sharedPreferences = context.getSharedPreferences("user_goals", Context.MODE_PRIVATE)
    with(sharedPreferences.edit()) {
        putString("goal_daily_cb", goalDailyCB)
        apply()
    }
    Log.d("UserGoalUtils", "Daily calories burned saved: $goalDailyCB")
}

fun getGoalDailyCB(context: Context): String {
    val sharedPreferences = context.getSharedPreferences("user_goals", Context.MODE_PRIVATE)
    return sharedPreferences.getString("goal_daily_cb", "") ?: ""
}

fun setGoalWeeklyCB(context: Context, goalWeeklyCB: String) {
    val sharedPreferences = context.getSharedPreferences("user_goals", Context.MODE_PRIVATE)
    with(sharedPreferences.edit()) {
        putString("goal_weekly_cb", goalWeeklyCB)
        apply()
    }
    Log.d("UserGoalUtils", "Weekly calories burned saved: $goalWeeklyCB")
}

fun getGoalWeeklyCB(context: Context): String {
    val sharedPreferences = context.getSharedPreferences("user_goals", Context.MODE_PRIVATE)
    return sharedPreferences.getString("goal_weekly_cb", "") ?: ""
}

fun setGoalMonthlyCB(context: Context, goalMonthlyCB: String) {
    val sharedPreferences = context.getSharedPreferences("user_goals", Context.MODE_PRIVATE)
    with(sharedPreferences.edit()) {
        putString("goal_monthly_cb", goalMonthlyCB)
        apply()
    }
    Log.d("UserGoalUtils", "Monthly calories burned saved: $goalMonthlyCB")
}

fun getGoalMonthlyCB(context: Context): String {
    val sharedPreferences = context.getSharedPreferences("user_goals", Context.MODE_PRIVATE)
    return sharedPreferences.getString("goal_monthly_cb", "") ?: ""
}