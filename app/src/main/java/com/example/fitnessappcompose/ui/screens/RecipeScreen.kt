package com.example.fitnessappcompose.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.fitnessappcompose.R

data class Recipe(val imageResId: Int, val name: String, val description: String)

@Composable
fun RecipeScreen() {
    val sections = listOf(
        Triple("Breakfast Recipes", "Start your day with these delicious breakfast recipes", listOf(
            Recipe(R.drawable.breakfast_steak_eggs, "Steak and Eggs", "A hearty breakfast with steak and eggs."),
            Recipe(R.drawable.breakfast_grilled_chicken_quinoa, "Grilled Chicken with Quinoa", "Healthy grilled chicken with quinoa."),
            Recipe(R.drawable.breakfast_gluten_free_pancakes, "Gluten-Free Pancakes", "Fluffy gluten-free pancakes with almond or coconut flour."),
            Recipe(R.drawable.breakfast_egg_white_omelette, "Egg White Omelette", "Egg white omelette with spinach and mushrooms."),
            Recipe(R.drawable.breakfast_protein_smoothie, "Protein-Packed Smoothie", "A refreshing smoothie with protein powder, almond milk, and peanut butter."),
            Recipe(R.drawable.breakfast_tofu_scramble, "Tofu Scramble", "A vegan scramble made with tofu and veggies."),
            Recipe(R.drawable.breakfast_chickpea_salad_sandwich, "Chickpea Salad Sandwich", "A protein-packed vegan chickpea salad sandwich."),
            Recipe(R.drawable.breakfast_sweet_potato_hash, "Sweet Potato Breakfast Hash", "Sweet potatoes with spinach and eggs."),
            Recipe(R.drawable.breakfast_lentil_shepherds_pie, "Lentil Shepherd’s Pie", "A hearty lentil shepherd's pie."),
            Recipe(R.drawable.breakfast_quinoa_stuffed_peppers, "Quinoa-Stuffed Bell Peppers", "Peppers stuffed with quinoa and veggies."),
            Recipe(R.drawable.breakfast_buckwheat_pancakes, "Buckwheat Pancakes", "Delicious, gluten-free buckwheat pancakes.")
        )),

        Triple("Lunch Recipes", "Power through your day with these high-protein lunch options", listOf(
            Recipe(R.drawable.lunch_turkey_stir_fry, "Turkey and Vegetable Stir-Fry", "Lean turkey stir-fried with mixed vegetables."),
            Recipe(R.drawable.lunch_tuna_salad, "Tuna Salad", "High-protein tuna salad with avocado and veggies."),
            Recipe(R.drawable.lunch_zucchini_noodles_chicken, "Zucchini Noodles with Chicken", "Zucchini noodles with grilled chicken and pesto."),
            Recipe(R.drawable.lunch_lentil_quinoa_salad, "Lentil and Quinoa Salad", "A protein-rich salad with lentils, quinoa, and vegetables."),
            Recipe(R.drawable.lunch_grilled_tofu_skewers, "Grilled Tofu Skewers", "Marinated tofu skewers with vegetables."),
            Recipe(R.drawable.lunch_chickpea_curry, "Chickpea and Spinach Curry", "A protein-packed vegan curry with chickpeas and spinach."),
            Recipe(R.drawable.lunch_salmon_lentils, "Salmon with Lentils", "Baked salmon served with a bed of lentils."),
            Recipe(R.drawable.lunch_black_bean_burgers, "Black Bean Burgers", "Vegan black bean burgers with oats and spices."),
            Recipe(R.drawable.lunch_chicken_breast_brown_rice, "Chicken Breast with Brown Rice", "Simple grilled chicken with brown rice and broccoli."),
            Recipe(R.drawable.lunch_turkey_meatballs, "Turkey Meatballs", "Lean turkey meatballs with oats and marinara sauce."),
            Recipe(R.drawable.lunch_cauliflower_rice_stir_fry, "Cauliflower Rice Stir-Fry", "A stir-fry with cauliflower rice and lean protein."),
            Recipe(R.drawable.lunch_stuffed_sweet_potatoes, "Stuffed Sweet Potatoes", "Sweet potatoes stuffed with black beans and avocado.")
        )),
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        items(sections) { (title, description, recipes) ->
            Section(title = title, description = description, recipes = recipes)
        }
    }
}

@Composable
fun Section(title: String, description: String, recipes: List<Recipe>) {
    Column {
        Box(modifier = Modifier.fillMaxWidth()) {
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(recipes) { recipe ->
                RecipeCard(recipe = recipe)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun RecipeCard(recipe: Recipe) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = recipe.imageResId),
                contentDescription = "Recipe image",
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(recipe.name, style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(4.dp))
                Text(recipe.description, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}