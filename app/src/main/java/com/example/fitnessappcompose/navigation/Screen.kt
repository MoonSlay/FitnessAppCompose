package com.example.fitnessappcompose.navigation

sealed class Screen(val route: String) {
    object Dashboard : Screen(Routes.DASHBOARD)
    object Recipe : Screen(Routes.RECIPE)
    object Training : Screen(Routes.TRAINING)
    object Report : Screen(Routes.REPORT)
    object Profile : Screen(Routes.PROFILE)
    object SetupGoal : Screen(Routes.SETUP_GOAL)
    object ProfileData : Screen(Routes.PROFILE_DATA)
    object RecipeDetail : Screen(Routes.RECIPE_DETAIL) {
        fun createRoute(recipeName: String) = "recipe_detail/$recipeName"
    }
}