// RecipeScreen.kt
package com.example.fitnessappcompose.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.fitnessappcompose.R
import kotlinx.coroutines.launch
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.saveable.Saver

data class Recipe(
    val imageResId: Int,
    val name: String,
    val description: String,
    val ingredients: List<String>,
    val instructions: String,
    val nutritionalInfo: String
)

val defaultRecipe = Recipe(
    imageResId = R.drawable.lunch_tuna_salad,
    name = "Default Recipe",
    description = "Default description",
    ingredients = listOf("Default ingredient"),
    instructions = "Default instructions",
    nutritionalInfo = "Default nutritional info"
)

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeScreen() {
    val sections = listOf(
        Triple("Breakfast Recipes", "Start your day with these delicious breakfast recipes", listOf(
            Recipe(
                R.drawable.breakfast_steak_eggs,
                "Steak and Eggs",
                "A hearty breakfast with steak and eggs.",
                ingredients = listOf("200g Steak (sirloin or ribeye)", "2 large eggs", "Salt", "Pepper", "1 tablespoon olive oil or butter", "Fresh herbs (optional: parsley, thyme)"),
                instructions = "1. Season the steak: Rub both sides of the steak with salt and pepper. Let it rest for 5 minutes.\n2. Heat a pan over medium-high heat. Add olive oil or butter. Sear the steak for 3-4 minutes per side for medium-rare. Adjust time for desired doneness.\n3. Remove the steak from the pan and let it rest for 5 minutes before slicing.\n4. Cook the eggs: In the same pan, crack two eggs and cook to your preference (sunny-side-up or scrambled).\n5. Serve: Slice the steak and serve with the eggs. Optionally garnish with fresh herbs.",
                nutritionalInfo = "Protein: 38g\nFats: 32g\nCarbohydrates: 1g\nFiber: 0g\nCalories: 450 kcal\nSodium: 700 mg\nCholesterol: 370 mg\nVitamins: Vitamin A (400 IU), Vitamin D (0.8 µg), Vitamin B12 (2.5 µg)\nMinerals: Calcium (20 mg), Iron (4.5 mg), Magnesium (30 mg)"
            ),
            // Add more recipes here...
        )),
        // Add more sections here...
    )

    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    var selectedRecipe by rememberSaveable(stateSaver = RecipeSaver) { mutableStateOf(defaultRecipe) }

    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetContent = {
            BottomSheetContent(selectedRecipe)
        },
        sheetPeekHeight = 0.dp
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            sections.forEach { (title, description, recipes) ->
                item {
                    Section(title, description, recipes) { recipe ->
                        coroutineScope.launch {
                            selectedRecipe = recipe
                            bottomSheetScaffoldState.bottomSheetState.expand()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BottomSheetContent(recipe: Recipe) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = screenHeight * 0.8f)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text(text = recipe.name, style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = recipe.description, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(16.dp))
            Image(
                painter = painterResource(id = recipe.imageResId),
                contentDescription = "Recipe image",
                modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Ingredients", style = MaterialTheme.typography.headlineSmall)
            recipe.ingredients.forEach { ingredient ->
                Text(text = ingredient, style = MaterialTheme.typography.bodyMedium)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Instructions", style = MaterialTheme.typography.headlineSmall)
            Text(text = recipe.instructions, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Nutritional Information", style = MaterialTheme.typography.headlineSmall)
            Text(text = recipe.nutritionalInfo, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun Section(title: String, description: String, recipes: List<Recipe>, onRecipeClick: (Recipe) -> Unit) {
    Column {
        Text(text = title, style = MaterialTheme.typography.headlineSmall)
        Text(text = description, style = MaterialTheme.typography.bodySmall)
        Spacer(modifier = Modifier.height(16.dp))
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(recipes) { recipe ->
                RecipeCard(recipe, onRecipeClick)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun RecipeCard(recipe: Recipe, onRecipeClick: (Recipe) -> Unit) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier
            .width(250.dp)
            .height(125.dp)
            .clickable { onRecipeClick(recipe) }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(8.dp)
        ) {
            Image(
                painter = painterResource(id = recipe.imageResId),
                contentDescription = "Recipe image",
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxHeight()
            ) {
                Text(text = recipe.name, style = MaterialTheme.typography.bodyLarge)
                Text(text = recipe.description, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}