// RecipeSaver.kt
package com.example.fitnessappcompose.ui.screens

import androidx.compose.runtime.saveable.Saver
import com.example.fitnessappcompose.ui.screens.Recipe

val RecipeSaver = Saver<Recipe, List<Any>>(
    save = { recipe ->
        listOf(
            recipe.imageResId,
            recipe.name,
            recipe.description,
            recipe.ingredients,
            recipe.instructions,
            recipe.nutritionalInfo
        )
    },
    restore = { list ->
        Recipe(
            imageResId = list[0] as Int,
            name = list[1] as String,
            description = list[2] as String,
            ingredients = list[3] as List<String>,
            instructions = list[4] as String,
            nutritionalInfo = list[5] as String
        )
    }
)