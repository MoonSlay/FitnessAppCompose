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

// Recipe data class
data class Recipe(
    val imageResId: Int,
    val name: String,
    val description: String,
    val ingredients: List<String>,
    val instructions: String,
    val nutritionalInfo: String
)

// Default recipe to be displayed initially
val defaultRecipe = Recipe(
    imageResId = R.drawable.lunch_tuna_salad,
    name = "Default Recipe",
    description = "Default description",
    ingredients = listOf("Default ingredient"),
    instructions = "Default instructions",
    nutritionalInfo = "Default nutritional info"
)

// Define how to save and restore the Recipe object
val RecipeSaver = Saver<Recipe, List<Any>>(
    save = { recipe -> // Function to save the recipe properties
        listOf(
            recipe.imageResId,
            recipe.name,
            recipe.description,
            recipe.ingredients,
            recipe.instructions,
            recipe.nutritionalInfo
        )
    },
    restore = { list -> // Function to restore the recipe properties
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

// Opt-in to use experimental APIs
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeScreen() {
    // Define sections of recipes (e.g., Breakfast Recipes)
    val sections = listOf(
        // Breakfast recipes
        Triple("Breakfast Recipes", "Start your day with these delicious breakfast recipes", listOf(
            Recipe(
                R.drawable.breakfast_steak_eggs,
                "Steak and Eggs",
                "A hearty breakfast with steak and eggs.",
                ingredients = listOf("200g Steak (sirloin or ribeye)", "2 large eggs", "Salt", "Pepper", "1 tablespoon olive oil or butter", "Fresh herbs (optional: parsley, thyme)"),
                instructions = "1. Season the steak: Rub both sides of the steak with salt and pepper. Let it rest for 5 minutes.\n2. Heat a pan over medium-high heat. Add olive oil or butter. Sear the steak for 3-4 minutes per side for medium-rare. Adjust time for desired doneness.\n3. Remove the steak from the pan and let it rest for 5 minutes before slicing.\n4. Cook the eggs: In the same pan, crack two eggs and cook to your preference (sunny-side-up or scrambled).\n5. Serve: Slice the steak and serve with the eggs. Optionally garnish with fresh herbs.",
                nutritionalInfo = "Protein: 38g\nFats: 32g\nCarbohydrates: 1g\nFiber: 0g\nCalories: 450 kcal\nSodium: 700 mg\nCholesterol: 370 mg\nVitamins: Vitamin A (400 IU), Vitamin D (0.8 µg), Vitamin B12 (2.5 µg)\nMinerals: Calcium (20 mg), Iron (4.5 mg), Magnesium (30 mg)"
            ),

            Recipe(
                R.drawable.breakfast_grilled_chicken_quinoa,
                "Grilled Chicken with Quinoa",
                "Healthy grilled chicken with quinoa.",
                ingredients = listOf("150g chicken breast", "½ cup quinoa", "1 cup water or chicken broth", "1 tablespoon olive oil", "Salt", "Pepper", "1 tablespoon lemon juice", "Fresh herbs for garnish (optional)"),
                instructions = "1. Cook quinoa: Rinse the quinoa under cold water. In a pot, bring the water or chicken broth to a boil. Add quinoa, reduce heat to low, and simmer for 15 minutes or until water is absorbed.\n2. Grill the chicken: Season the chicken breast with salt, pepper, and lemon juice. Heat a grill or grill pan over medium heat. Cook the chicken for 6-7 minutes per side until cooked through.\n3. Serve: Fluff the quinoa with a fork. Serve the grilled chicken over the quinoa and garnish with fresh herbs.",
                nutritionalInfo = "Protein: 35g\nFats: 8g\nCarbohydrates: 32g\nFiber: 5g\nCalories: 400 kcal\nSodium: 400 mg\nCholesterol: 80 mg\nVitamins: Vitamin A (40 IU), Vitamin C (2 mg), Vitamin B6 (0.6 mg)\nMinerals: Calcium (40 mg), Iron (2.5 mg), Magnesium (70 mg)"
            ),

            Recipe(
                R.drawable.breakfast_gluten_free_pancakes,
                "Gluten-Free Pancakes",
                "Delicious gluten-free pancakes made with almond flour.",
                ingredients = listOf("1 cup almond flour", "2 large eggs", "1 teaspoon baking powder", "¼ cup almond milk", "1 tablespoon honey or maple syrup", "1 teaspoon vanilla extract", "Butter or oil for cooking"),
                instructions = "1. Mix ingredients: In a bowl, whisk together almond flour, eggs, almond milk, honey, vanilla extract, and baking powder.\n2. Cook pancakes: Heat a non-stick pan over medium heat and grease it with butter or oil. Pour about ¼ cup of batter onto the pan and cook for 2-3 minutes per side, or until golden brown.\n3. Serve: Stack the pancakes and drizzle with honey or maple syrup if desired.",
                nutritionalInfo = "Protein: 12g\nFats: 18g\nCarbohydrates: 10g\nFiber: 2g\nCalories: 250 kcal\nSodium: 150 mg\nCholesterol: 180 mg\nVitamins: Vitamin A (100 IU), Vitamin E (3 mg), B Vitamins (varied)\nMinerals: Calcium (120 mg), Iron (1 mg), Magnesium (40 mg)"
            ),

            Recipe(
                R.drawable.breakfast_egg_white_omelette,
                "Egg White Omelette",
                "A protein-packed omelette made with egg whites and vegetables.",
                ingredients = listOf("4 large egg whites", "1 tablespoon olive oil", "½ cup bell peppers, diced", "¼ cup spinach", "Salt", "Pepper"),
                instructions = "1. Whisk the egg whites in a bowl and season with salt and pepper.\n2. Heat olive oil in a pan over medium heat. Add bell peppers and sauté until soft.\n3. Pour in the egg whites and cook until the edges start to set. Add spinach on top.\n4. Fold the omelette in half and cook for an additional minute until fully set.\n5. Serve warm with a side of fruit.",
                nutritionalInfo = "Protein: 18g\nFats: 7g\nCarbohydrates: 3g\nFiber: 1g\nCalories: 120 kcal\nSodium: 200 mg\nCholesterol: 0 mg\nVitamins: Vitamin A (900 IU), Vitamin D (0 IU), Vitamin B12 (0 µg)\nMinerals: Calcium (20 mg), Iron (0.5 mg), Magnesium (15 mg)"
            ),

            Recipe(
                R.drawable.breakfast_protein_smoothie,
                "Protein-Packed Smoothie",
                "A refreshing smoothie rich in protein and healthy fats.",
                ingredients = listOf("1 scoop protein powder", "1 banana", "1 tablespoon almond butter", "1 cup almond milk", "1 tablespoon chia seeds", "Ice cubes"),
                instructions = "1. In a blender, combine protein powder, banana, almond butter, almond milk, and chia seeds.\n2. Add ice cubes and blend until smooth.\n3. Pour into a glass and enjoy immediately.",
                nutritionalInfo = "Protein: 30g\nFats: 12g\nCarbohydrates: 36g\nFiber: 10g\nCalories: 350 kcal\nSodium: 200 mg\nCholesterol: 0 mg\nVitamins: Vitamin A (50 IU), Vitamin C (10 mg), Vitamin E (1 mg)\nMinerals: Calcium (300 mg), Iron (1 mg), Magnesium (60 mg)"
            ),

            Recipe(
                R.drawable.breakfast_tofu_scramble,
                "Tofu Scramble",
                "A flavorful tofu scramble with vegetables.",
                ingredients = listOf("200g firm tofu", "½ cup bell peppers, diced", "½ cup spinach", "1 tablespoon olive oil", "1 teaspoon turmeric", "Salt", "Pepper"),
                instructions = "1. Heat olive oil in a pan over medium heat. Add bell peppers and sauté until soft.\n2. Crumble the tofu into the pan and add turmeric, salt, and pepper. Cook for 5-7 minutes, stirring occasionally.\n3. Add spinach and cook until wilted.\n4. Serve warm with toast or as a filling for wraps.",
                nutritionalInfo = "Protein: 22g\nFats: 10g\nCarbohydrates: 8g\nFiber: 2g\nCalories: 250 kcal\nSodium: 300 mg\nCholesterol: 0 mg\nVitamins: Vitamin A (250 IU), Vitamin C (5 mg), Vitamin B12 (0 µg)\nMinerals: Calcium (250 mg), Iron (4 mg), Magnesium (50 mg)"
            ),

            Recipe(
                R.drawable.breakfast_chickpea_salad_sandwich,
                "Chickpea Salad Sandwich",
                "A healthy sandwich made with chickpea salad.",
                ingredients = listOf("1 can (400g) chickpeas, drained and rinsed", "2 tablespoons Greek yogurt", "1 tablespoon Dijon mustard", "1 celery stalk, chopped", "Salt", "Pepper", "Whole grain bread"),
                instructions = "1. In a bowl, mash the chickpeas with a fork until slightly chunky.\n2. Mix in Greek yogurt, mustard, celery, salt, and pepper until well combined.\n3. Serve on whole grain bread as a sandwich.",
                nutritionalInfo = "Protein: 15g\nFats: 4g\nCarbohydrates: 35g\nFiber: 8g\nCalories: 250 kcal\nSodium: 350 mg\nCholesterol: 5 mg\nVitamins: Vitamin A (10 IU), Vitamin C (5 mg), Vitamin B6 (0.1 mg)\nMinerals: Calcium (30 mg), Iron (2.5 mg), Magnesium (50 mg)"
            ),

            Recipe(
                R.drawable.breakfast_sweet_potato_hash,
                "Sweet Potato Breakfast Hash",
                "A savory breakfast hash made with sweet potatoes and vegetables.",
                ingredients = listOf("1 large sweet potato, diced", "1 bell pepper, diced", "1 onion, diced", "1 tablespoon olive oil", "Salt", "Pepper"),
                instructions = "1. Heat olive oil in a large skillet over medium heat. Add sweet potatoes and cook for 10 minutes.\n2. Add bell pepper and onion. Season with salt and pepper, and cook for another 5-7 minutes until everything is tender.\n3. Serve warm as a side or main dish.",
                nutritionalInfo = "Protein: 4g\nFats: 7g\nCarbohydrates: 40g\nFiber: 6g\nCalories: 200 kcal\nSodium: 300 mg\nCholesterol: 0 mg\nVitamins: Vitamin A (2000 IU), Vitamin C (20 mg), Vitamin B6 (0.2 mg)\nMinerals: Calcium (40 mg), Iron (0.6 mg), Magnesium (25 mg)"
            ),

            Recipe(
                R.drawable.breakfast_lentil_shepherds_pie,
                "Lentil Shepherd’s Pie",
                "A healthy twist on shepherd’s pie using lentils.",
                ingredients = listOf("1 cup cooked lentils", "1 large potato, peeled and diced", "1 cup mixed vegetables (carrots, peas, corn)", "1 tablespoon olive oil", "Salt", "Pepper"),
                instructions = "1. Preheat the oven to 400°F (200°C).\n2. Boil the diced potatoes until tender. Drain and mash with olive oil, salt, and pepper.\n3. In a baking dish, layer cooked lentils and mixed vegetables. Top with the mashed potatoes.\n4. Bake for 20 minutes until the top is golden brown.\n5. Serve warm.",
                nutritionalInfo = "Protein: 15g\nFats: 4g\nCarbohydrates: 35g\nFiber: 10g\nCalories: 250 kcal\nSodium: 400 mg\nCholesterol: 0 mg\nVitamins: Vitamin A (300 IU), Vitamin C (15 mg), Vitamin B6 (0.3 mg)\nMinerals: Calcium (30 mg), Iron (3.5 mg), Magnesium (40 mg)"
            ),

            Recipe(
                R.drawable.breakfast_quinoa_stuffed_peppers,
                "Quinoa-Stuffed Bell Peppers",
                "Colorful bell peppers stuffed with quinoa and vegetables.",
                ingredients = listOf("2 bell peppers", "1 cup cooked quinoa", "½ cup black beans, rinsed", "1 tablespoon olive oil", "1 teaspoon cumin", "Salt", "Pepper"),
                instructions = "1. Preheat the oven to 375°F (190°C).\n2. Cut the tops off the bell peppers and remove seeds.\n3. In a bowl, mix cooked quinoa, black beans, olive oil, cumin, salt, and pepper.\n4. Stuff the mixture into the bell peppers and place in a baking dish.\n5. Bake for 25-30 minutes until peppers are tender.\n6. Serve warm.",
                nutritionalInfo = "Protein: 10g\nFats: 7g\nCarbohydrates: 25g\nFiber: 6g\nCalories: 220 kcal\nSodium: 300 mg\nCholesterol: 0 mg\nVitamins: Vitamin A (200 IU), Vitamin C (60 mg), Vitamin B6 (0.2 mg)\nMinerals: Calcium (30 mg), Iron (2.5 mg), Magnesium (50 mg)"
            ),

            Recipe(
                R.drawable.breakfast_buckwheat_pancakes,
                "Buckwheat Pancakes",
                "Nutritious pancakes made with buckwheat flour.",
                ingredients = listOf("1 cup buckwheat flour", "1 tablespoon baking powder", "1 tablespoon honey", "1 cup almond milk", "1 egg", "Butter or oil for cooking"),
                instructions = "1. In a bowl, mix buckwheat flour and baking powder. In another bowl, whisk honey, almond milk, and egg.\n2. Combine wet and dry ingredients until just mixed.\n3. Heat a non-stick pan over medium heat and grease it. Pour about ¼ cup of batter onto the pan and cook until bubbles form. Flip and cook until golden.\n4. Serve with maple syrup or fresh fruit.",
                nutritionalInfo = "Protein: 12g\nFats: 8g\nCarbohydrates: 35g\nFiber: 4g\nCalories: 280 kcal\nSodium: 200 mg\nCholesterol: 40 mg\nVitamins: Vitamin A (100 IU), Vitamin C (0 mg), Vitamin E (1 mg)\nMinerals: Calcium (50 mg), Iron (2 mg), Magnesium (70 mg)"
            )

        )),

        // Lunch recipes
        Triple("Lunch Recipes", "Power through your day with these high-protein lunch options", listOf(
            Recipe(
                R.drawable.lunch_turkey_stir_fry,
                "Turkey and Vegetable Stir-Fry",
                "A quick and healthy stir-fry with turkey and mixed vegetables.",
                ingredients = listOf("200g turkey breast, sliced", "2 cups mixed vegetables (broccoli, bell pepper, carrots)", "1 tablespoon soy sauce", "1 tablespoon olive oil", "Salt", "Pepper"),
                instructions = "1. Heat olive oil in a pan over medium heat. Add turkey and cook until browned, about 5-7 minutes.\n2. Add mixed vegetables and stir-fry for another 5 minutes.\n3. Stir in soy sauce and cook for an additional minute. Season with salt and pepper to taste.\n4. Serve hot over rice or noodles.",
                nutritionalInfo = "Protein: 36g\nFats: 10g\nCarbohydrates: 12g\nFiber: 3g\nCalories: 300 kcal\nSodium: 500 mg\nCholesterol: 90 mg\nVitamins: Vitamin A (300 IU), Vitamin C (20 mg), Vitamin B6 (0.5 mg)\nMinerals: Calcium (50 mg), Iron (2 mg), Magnesium (40 mg)"
            ),

            Recipe(
                R.drawable.lunch_tuna_salad,
                "Tuna Salad",
                "A refreshing salad with tuna, avocado, and Greek yogurt.",
                ingredients = listOf("1 can (150g) tuna in water, drained", "2 tablespoons Greek yogurt", "1 tablespoon mustard", "½ avocado, diced", "1 tablespoon lemon juice", "1 stalk celery, chopped", "Salt", "Pepper", "Fresh greens for serving"),
                instructions = "1. Prepare the tuna: In a bowl, combine the tuna, Greek yogurt, mustard, lemon juice, avocado, and celery. Mix well.\n2. Season: Add salt and pepper to taste.\n3. Serve: Serve the tuna salad over a bed of fresh greens or in a sandwich.",
                nutritionalInfo = "Protein: 25g\nFats: 10g\nCarbohydrates: 5g\nFiber: 4g\nCalories: 220 kcal\nSodium: 300 mg\nCholesterol: 50 mg\nVitamins: Vitamin A (250 IU), Vitamin C (6 mg), Vitamin B12 (2 µg)\nMinerals: Calcium (30 mg), Iron (1.5 mg), Magnesium (30 mg)"
            ),

            Recipe(
                R.drawable.lunch_zucchini_noodles_chicken,
                "Zucchini Noodles with Chicken",
                "Low-carb zucchini noodles served with grilled chicken.",
                ingredients = listOf("2 medium zucchinis (spiralized)", "150g chicken breast, sliced", "1 tablespoon olive oil", "1 garlic clove, minced", "¼ cup pesto sauce (optional)", "Salt", "Pepper"),
                instructions = "1. Cook the chicken: Heat a pan over medium heat and add olive oil. Cook the chicken slices until browned on both sides, about 5-7 minutes. Set aside.\n2. Sauté zucchini noodles: In the same pan, add the garlic and sauté for 30 seconds. Add the zucchini noodles and cook for 2-3 minutes until slightly tender.\n3. Combine: Toss the zucchini noodles with the cooked chicken and pesto sauce. Season with salt and pepper.\n4. Serve: Plate the zucchini noodles with chicken and garnish with fresh herbs if desired.",
                nutritionalInfo = "Protein: 30g\nFats: 14g\nCarbohydrates: 8g\nFiber: 3g\nCalories: 280 kcal\nSodium: 400 mg\nCholesterol: 80 mg\nVitamins: Vitamin A (500 IU), Vitamin C (15 mg), Vitamin B6 (0.5 mg)\nMinerals: Calcium (40 mg), Iron (1.5 mg), Magnesium (25 mg)"
            ),

            Recipe(
                R.drawable.lunch_lentil_quinoa_salad,
                "Lentil and Quinoa Salad",
                "A nutritious salad packed with protein from lentils and quinoa.",
                ingredients = listOf("1 cup cooked lentils", "1 cup cooked quinoa", "½ cup cherry tomatoes, halved", "¼ cup red onion, diced", "1 tablespoon olive oil", "1 tablespoon lemon juice", "Salt", "Pepper"),
                instructions = "1. In a bowl, combine cooked lentils, cooked quinoa, cherry tomatoes, and red onion.\n2. Drizzle with olive oil and lemon juice. Season with salt and pepper.\n3. Toss gently to combine and serve.",
                nutritionalInfo = "Protein: 18g\nFats: 7g\nCarbohydrates: 45g\nFiber: 12g\nCalories: 350 kcal\nSodium: 200 mg\nCholesterol: 0 mg\nVitamins: Vitamin A (200 IU), Vitamin C (15 mg), Vitamin B6 (0.3 mg)\nMinerals: Calcium (40 mg), Iron (3 mg), Magnesium (70 mg)"
            ),

            Recipe(
                R.drawable.lunch_grilled_tofu_skewers,
                "Grilled Tofu Skewers",
                "Delicious grilled skewers made with tofu and vegetables.",
                ingredients = listOf("200g firm tofu, cubed", "1 bell pepper, chopped", "1 zucchini, sliced", "1 tablespoon soy sauce", "1 tablespoon olive oil", "Skewers"),
                instructions = "1. Preheat the grill. In a bowl, mix tofu, bell pepper, zucchini, soy sauce, and olive oil. Marinate for 15 minutes.\n2. Thread the marinated tofu and vegetables onto skewers.\n3. Grill skewers for 10-15 minutes, turning occasionally until charred and cooked through.\n4. Serve warm with a dipping sauce of choice.",
                nutritionalInfo = "Protein: 18g\nFats: 9g\nCarbohydrates: 10g\nFiber: 4g\nCalories: 220 kcal\nSodium: 400 mg\nCholesterol: 0 mg\nVitamins: Vitamin A (200 IU), Vitamin C (15 mg), Vitamin B6 (0.2 mg)\nMinerals: Calcium (250 mg), Iron (2.5 mg), Magnesium (50 mg)"
            ),

            Recipe(
                R.drawable.lunch_chickpea_curry,
                "Chickpea and Spinach Curry",
                "A hearty and flavorful chickpea curry with spinach.",
                ingredients = listOf("1 can (400g) chickpeas, drained and rinsed", "2 cups fresh spinach", "1 onion, chopped", "1 garlic clove, minced", "1 tablespoon curry powder", "1 tablespoon olive oil", "Salt", "Pepper"),
                instructions = "1. Heat olive oil in a pot over medium heat. Add onion and garlic, and sauté until translucent.\n2. Stir in curry powder and cook for another minute.\n3. Add chickpeas and spinach. Cook until the spinach wilts and everything is heated through.\n4. Season with salt and pepper, and serve warm with rice.",
                nutritionalInfo = "Protein: 12g\nFats: 5g\nCarbohydrates: 30g\nFiber: 8g\nCalories: 220 kcal\nSodium: 300 mg\nCholesterol: 0 mg\nVitamins: Vitamin A (500 IU), Vitamin C (10 mg), Vitamin B6 (0.4 mg)\nMinerals: Calcium (80 mg), Iron (3 mg), Magnesium (40 mg)"
            ),

            Recipe(
                R.drawable.lunch_salmon_lentils,
                "Salmon with Lentils",
                "Pan-seared salmon served over a bed of lentils.",
                ingredients = listOf("150g salmon fillet", "1 cup cooked lentils", "1 tablespoon olive oil", "Salt", "Pepper", "Fresh herbs for garnish (optional)"),
                instructions = "1. Season the salmon with salt and pepper. Heat olive oil in a skillet over medium-high heat.\n2. Cook the salmon for 4-5 minutes per side until cooked through.\n3. Serve the salmon over cooked lentils and garnish with fresh herbs if desired.",
                nutritionalInfo = "Protein: 30g\nFats: 15g\nCarbohydrates: 10g\nFiber: 5g\nCalories: 350 kcal\nSodium: 300 mg\nCholesterol: 80 mg\nVitamins: Vitamin A (150 IU), Vitamin C (0 mg), Vitamin B12 (2.5 µg)\nMinerals: Calcium (20 mg), Iron (2 mg), Magnesium (40 mg)"
            ),

            Recipe(
                R.drawable.lunch_black_bean_burgers,
                "Black Bean Burgers",
                "Delicious and hearty black bean burgers.",
                ingredients = listOf("1 can (400g) black beans, drained and rinsed", "½ cup breadcrumbs", "1 small onion, chopped", "1 teaspoon cumin", "Salt", "Pepper", "Buns for serving"),
                instructions = "1. In a bowl, mash the black beans until slightly chunky.\n2. Add breadcrumbs, onion, cumin, salt, and pepper. Mix until combined.\n3. Form patties and cook on a skillet over medium heat for 5-6 minutes per side until golden brown.\n4. Serve on buns with your favorite toppings.",
                nutritionalInfo = "Protein: 12g\nFats: 5g\nCarbohydrates: 30g\nFiber: 10g\nCalories: 250 kcal\nSodium: 300 mg\nCholesterol: 0 mg\nVitamins: Vitamin A (10 IU), Vitamin C (2 mg), Vitamin B6 (0.3 mg)\nMinerals: Calcium (50 mg), Iron (3 mg), Magnesium (40 mg)"
            ),

            Recipe(
                R.drawable.lunch_chicken_breast_brown_rice,
                "Chicken Breast with Brown Rice",
                "Simple and healthy chicken breast served with brown rice.",
                ingredients = listOf("200g chicken breast", "1 cup cooked brown rice", "1 tablespoon olive oil", "Salt", "Pepper", "Your choice of veggies"),
                instructions = "1. Season the chicken breast with salt and pepper. Heat olive oil in a pan over medium heat.\n2. Cook the chicken for 6-7 minutes per side until cooked through.\n3. Serve the chicken over cooked brown rice with steamed vegetables.",
                nutritionalInfo = "Protein: 30g\nFats: 10g\nCarbohydrates: 40g\nFiber: 4g\nCalories: 350 kcal\nSodium: 300 mg\nCholesterol: 80 mg\nVitamins: Vitamin A (100 IU), Vitamin C (0 mg), Vitamin B6 (0.5 mg)\nMinerals: Calcium (20 mg), Iron (1.5 mg), Magnesium (40 mg)"
            ),

            Recipe(
                R.drawable.lunch_turkey_meatballs,
                "Turkey Meatballs",
                "Healthy turkey meatballs served with sauce.",
                ingredients = listOf("200g ground turkey", "1 egg", "½ cup breadcrumbs", "1 tablespoon Italian seasoning", "Salt", "Pepper", "Tomato sauce for serving"),
                instructions = "1. Preheat the oven to 400°F (200°C). In a bowl, mix ground turkey, egg, breadcrumbs, Italian seasoning, salt, and pepper.\n2. Form into meatballs and place on a baking sheet.\n3. Bake for 20 minutes until cooked through.\n4. Serve with tomato sauce over pasta or on their own.",
                nutritionalInfo = "Protein: 28g\nFats: 10g\nCarbohydrates: 20g\nFiber: 1g\nCalories: 300 kcal\nSodium: 400 mg\nCholesterol: 90 mg\nVitamins: Vitamin A (150 IU), Vitamin C (2 mg), Vitamin B6 (0.5 mg)\nMinerals: Calcium (30 mg), Iron (2 mg), Magnesium (20 mg)"
            ),

            Recipe(
                R.drawable.lunch_cauliflower_rice_stir_fry,
                "Cauliflower Rice Stir-Fry",
                "A low-carb stir-fry using cauliflower rice.",
                ingredients = listOf("1 head of cauliflower (riced)", "2 cups mixed vegetables (carrots, peas, bell peppers)", "1 tablespoon soy sauce", "1 tablespoon olive oil", "Salt", "Pepper"),
                instructions = "1. Rice the cauliflower: Use a food processor or grater to make cauliflower rice.\n2. Heat olive oil in a pan over medium heat. Add mixed vegetables and sauté for 5 minutes.\n3. Stir in cauliflower rice and soy sauce. Cook for another 5-7 minutes until tender.\n4. Season with salt and pepper and serve warm.",
                nutritionalInfo = "Protein: 5g\nFats: 5g\nCarbohydrates: 18g\nFiber: 5g\nCalories: 150 kcal\nSodium: 300 mg\nCholesterol: 0 mg\nVitamins: Vitamin A (300 IU), Vitamin C (20 mg), Vitamin B6 (0.2 mg)\nMinerals: Calcium (40 mg), Iron (0.8 mg), Magnesium (30 mg)"
            ),

            Recipe(
                R.drawable.lunch_stuffed_sweet_potatoes,
                "Stuffed Sweet Potatoes",
                "Baked sweet potatoes stuffed with healthy fillings.",
                ingredients = listOf("2 medium sweet potatoes", "1 cup black beans, drained", "½ cup corn", "1 avocado, diced", "Salt", "Pepper", "Cilantro for garnish"),
                instructions = "1. Preheat the oven to 400°F (200°C). Pierce sweet potatoes with a fork and bake for 45 minutes until soft.\n2. Remove from oven, slice open, and fluff the insides with a fork.\n3. Stuff with black beans, corn, and avocado. Season with salt and pepper.\n4. Garnish with cilantro and serve warm.",
                nutritionalInfo = "Protein: 10g\nFats: 6g\nCarbohydrates: 40g\nFiber: 10g\nCalories: 300 kcal\nSodium: 200 mg\nCholesterol: 0 mg\nVitamins: Vitamin A (12000 IU), Vitamin C (10 mg), Vitamin B6 (0.2 mg)\nMinerals: Calcium (50 mg), Iron (2 mg), Magnesium (50 mg)"
            )

        )),


        // Dinner recipes
        Triple("Dinner Recipes", "End your day with these wholesome dinner options", listOf(
            Recipe(
                R.drawable.dinner_whole_wheat_fettuccine,
                "Whole Wheat Fettuccine",
                "A healthy pasta option with whole wheat fettuccine and a rich, hearty sauce.",
                ingredients = listOf("200g whole wheat fettuccine", "1 cup tomato sauce", "1 garlic clove, minced", "1 tablespoon olive oil", "Salt", "Pepper"),
                instructions = "1. Cook fettuccine according to package instructions.\n2. In a pan, heat olive oil and sauté garlic until fragrant.\n3. Add tomato sauce, salt, and pepper, and simmer for 5 minutes.\n4. Toss with fettuccine and serve warm.",
                nutritionalInfo = "Protein: 10g\nFats: 5g\nCarbohydrates: 40g\nFiber: 6g\nCalories: 250 kcal\nSodium: 200 mg\nCholesterol: 0 mg\nVitamins: Vitamin A (5000 IU), Vitamin C (8 mg), Vitamin B6 (0.3 mg)\nMinerals: Calcium (40 mg), Iron (3 mg), Magnesium (45 mg)"
            ),
            Recipe(
                R.drawable.dinner_thal_curry_veggle,
                "Thai Curry Veggie",
                "A creamy and aromatic Thai curry with a mix of fresh vegetables.",
                ingredients = listOf("1 cup mixed vegetables", "1 tablespoon Thai curry paste", "1 cup coconut milk", "1 tablespoon olive oil", "Salt", "Pepper"),
                instructions = "1. Heat oil in a pot and cook curry paste for 1 minute.\n2. Add vegetables and coconut milk. Simmer for 10 minutes.\n3. Season with salt and pepper and serve with rice.",
                nutritionalInfo = "Protein: 5g\nFats: 10g\nCarbohydrates: 20g\nFiber: 4g\nCalories: 200 kcal\nSodium: 300 mg\nCholesterol: 0 mg\nVitamins: Vitamin A (1000 IU), Vitamin C (15 mg), Vitamin B6 (0.1 mg)\nMinerals: Calcium (30 mg), Iron (1 mg), Magnesium (30 mg)"
            ),
            Recipe(
                R.drawable.dinner_sweet___tangy_chicken_burgers,
                "Sweet & Tangy Chicken Burgers",
                "Juicy chicken burgers with a sweet and tangy sauce.",
                ingredients = listOf("200g ground chicken", "1 tablespoon BBQ sauce", "1 tablespoon honey", "Buns for serving", "Salt", "Pepper"),
                instructions = "1. Combine chicken with salt, pepper, BBQ sauce, and honey.\n2. Form patties and cook on a skillet for 5 minutes each side.\n3. Serve on buns with your favorite toppings.",
                nutritionalInfo = "Protein: 20g\nFats: 8g\nCarbohydrates: 25g\nFiber: 2g\nCalories: 300 kcal\nSodium: 450 mg\nCholesterol: 60 mg\nVitamins: Vitamin A (200 IU), Vitamin C (2 mg), Vitamin B6 (0.4 mg)\nMinerals: Calcium (20 mg), Iron (1 mg), Magnesium (20 mg)"
            ),
            Recipe(
                R.drawable.dinner_roasted_salmon,
                "Roasted Salmon",
                "Flavorful and nutritious roasted salmon seasoned to perfection.",
                ingredients = listOf("150g salmon fillet", "1 tablespoon olive oil", "Salt", "Pepper", "Fresh herbs for garnish"),
                instructions = "1. Preheat oven to 400°F. Season salmon with salt and pepper.\n2. Drizzle with olive oil and bake for 12-15 minutes.\n3. Garnish with fresh herbs and serve.",
                nutritionalInfo = "Protein: 30g\nFats: 15g\nCarbohydrates: 0g\nFiber: 0g\nCalories: 250 kcal\nSodium: 80 mg\nCholesterol: 70 mg\nVitamins: Vitamin D (600 IU), Vitamin B12 (3 mcg)\nMinerals: Calcium (20 mg), Iron (0.5 mg), Magnesium (25 mg)"
            ),
            Recipe(
                R.drawable.dinner_ribbony_shrimp,
                "Ribbony Shrimp",
                "Delicious shrimp served with ribbon-like vegetables for a unique texture.",
                ingredients = listOf("200g shrimp", "1 zucchini, peeled into ribbons", "1 carrot, peeled into ribbons", "1 tablespoon olive oil", "Salt", "Pepper"),
                instructions = "1. Heat oil in a pan, cook shrimp for 2-3 minutes.\n2. Add vegetable ribbons, salt, and pepper, cook until tender.\n3. Serve warm with a sprinkle of herbs.",
                nutritionalInfo = "Protein: 25g\nFats: 6g\nCarbohydrates: 5g\nFiber: 2g\nCalories: 180 kcal\nSodium: 240 mg\nCholesterol: 125 mg\nVitamins: Vitamin A (1200 IU), Vitamin C (5 mg), Vitamin B6 (0.1 mg)\nMinerals: Calcium (40 mg), Iron (1 mg), Magnesium (40 mg)"
            ),
            Recipe(
                R.drawable.dinner_puttanesca,
                "Puttanesca",
                "A classic Italian pasta dish with a tangy tomato-based sauce.",
                ingredients = listOf("200g pasta", "1 cup tomato sauce", "1/4 cup olives", "1 tablespoon capers", "1 garlic clove, minced", "Salt", "Pepper"),
                instructions = "1. Cook pasta according to package instructions.\n2. In a pan, heat garlic, add tomato sauce, olives, and capers.\n3. Toss with pasta and serve hot.",
                nutritionalInfo = "Protein: 12g\nFats: 8g\nCarbohydrates: 45g\nFiber: 6g\nCalories: 300 kcal\nSodium: 400 mg\nCholesterol: 0 mg\nVitamins: Vitamin A (500 IU), Vitamin C (8 mg), Vitamin B6 (0.2 mg)\nMinerals: Calcium (30 mg), Iron (2 mg), Magnesium (40 mg)"
            ),
            Recipe(
                R.drawable.dinner_pork_tenderloin,
                "Pork Tenderloin",
                "Juicy pork tenderloin with a flavorful seasoning.",
                ingredients = listOf("200g pork tenderloin", "1 tablespoon olive oil", "Salt", "Pepper", "Fresh herbs"),
                instructions = "1. Preheat oven to 375°F. Season pork with salt and pepper.\n2. Heat oil in a skillet and sear pork on all sides.\n3. Transfer to oven and roast for 20 minutes. Slice and serve.",
                nutritionalInfo = "Protein: 25g\nFats: 12g\nCarbohydrates: 0g\nFiber: 0g\nCalories: 200 kcal\nSodium: 60 mg\nCholesterol: 75 mg\nVitamins: Vitamin B6 (0.4 mg), Vitamin B12 (1 mcg)\nMinerals: Calcium (15 mg), Iron (1 mg), Magnesium (20 mg)"
            ),
            Recipe(
                R.drawable.dinner_pan_seared_salmon,
                "Pan Seared Salmon",
                "Crispy on the outside, tender on the inside pan-seared salmon.",
                ingredients = listOf("150g salmon", "1 tablespoon olive oil", "Salt", "Pepper"),
                instructions = "1. Season salmon with salt and pepper.\n2. Heat oil in a pan over medium-high heat, sear salmon for 4-5 minutes per side.\n3. Serve with lemon wedges.",
                nutritionalInfo = "Protein: 30g\nFats: 15g\nCarbohydrates: 0g\nFiber: 0g\nCalories: 250 kcal\nSodium: 90 mg\nCholesterol: 70 mg\nVitamins: Vitamin D (500 IU), Vitamin B12 (3 mcg)\nMinerals: Calcium (25 mg), Iron (0.6 mg), Magnesium (27 mg)"
            ),
            Recipe(
                R.drawable.dinner_kale_and_artlchoke_chicken_casserole,
                "Kale and Artichoke Chicken Casserole",
                "A comforting casserole with kale, artichokes, and chicken.",
                ingredients = listOf("200g chicken breast", "1 cup kale, chopped", "½ cup artichoke hearts", "1 cup cream", "Salt", "Pepper"),
                instructions = "1. Preheat oven to 350°F. Combine ingredients in a casserole dish.\n2. Bake for 30 minutes until bubbly and golden.\n3. Serve warm.",
                nutritionalInfo = "Protein: 25g\nFats: 18g\nCarbohydrates: 10g\nFiber: 5g\nCalories: 320 kcal\nSodium: 400 mg\nCholesterol: 85 mg\nVitamins: Vitamin A (12000 IU), Vitamin C (10 mg), Vitamin B6 (0.3 mg)\nMinerals: Calcium (50 mg), Iron (2 mg), Magnesium (60 mg)"
            ),
            Recipe(
                R.drawable.dinner_honey_soy_grilled_salmon_wtih_edamame,
                "Honey Soy Grilled Salmon with Edamame",
                "Grilled salmon with a sweet and savory honey soy glaze, served with edamame.",
                ingredients = listOf("150g salmon", "1 tablespoon honey", "1 tablespoon soy sauce", "1/2 cup edamame", "Salt", "Pepper"),
                instructions = "1. Marinate salmon in honey and soy sauce for 10 minutes.\n2. Grill for 4-5 minutes per side.\n3. Serve with steamed edamame.",
                nutritionalInfo = "Protein: 32g\nFats: 12g\nCarbohydrates: 10g\nFiber: 3g\nCalories: 280 kcal\nSodium: 320 mg\nCholesterol: 65 mg\nVitamins: Vitamin B12 (2 mcg), Vitamin D (500 IU)\nMinerals: Calcium (30 mg), Iron (1 mg), Magnesium (35 mg)"
            ),
            Recipe(
                R.drawable.dinner_healthy_tuna_grain_with_turmeric_sweet_potatoes_and_spicy_yogurt,
                "Healthy Tuna Grain with Turmeric Sweet Potatoes and Spicy Yogurt",
                "A nutritious grain bowl topped with tuna, turmeric sweet potatoes, and spicy yogurt.",
                ingredients = listOf("1 can tuna", "1 cup cooked quinoa", "1 medium sweet potato", "1 tablespoon turmeric", "1/2 cup yogurt", "Chili powder", "Salt", "Pepper"),
                instructions = "1. Peel and dice sweet potato, then toss with turmeric, salt, and pepper. Roast at 400°F for 25 minutes.\n2. In a bowl, combine quinoa and drained tuna.\n3. Top with roasted sweet potatoes and a dollop of yogurt mixed with chili powder.",
                nutritionalInfo = "Protein: 28g\nFats: 8g\nCarbohydrates: 45g\nFiber: 6g\nCalories: 380 kcal\nSodium: 400 mg\nCholesterol: 40 mg\nVitamins: Vitamin A (12000 IU), Vitamin C (15 mg), Vitamin B6 (0.4 mg)\nMinerals: Calcium (50 mg), Iron (3 mg), Magnesium (70 mg)"
            ),
            Recipe(
                R.drawable.dinner_garlic_shrimp_withchorlzo,
                "Garlic Shrimp with Chorizo",
                "Sizzling shrimp paired with spicy chorizo and garlic.",
                ingredients = listOf("200g shrimp", "100g chorizo, sliced", "3 garlic cloves, minced", "1 tablespoon olive oil", "Salt", "Pepper"),
                instructions = "1. Heat oil in a pan, add chorizo and cook for 3-4 minutes.\n2. Add garlic and shrimp, season with salt and pepper, and cook until shrimp are pink.\n3. Serve warm with crusty bread.",
                nutritionalInfo = "Protein: 25g\nFats: 18g\nCarbohydrates: 2g\nFiber: 0g\nCalories: 280 kcal\nSodium: 600 mg\nCholesterol: 150 mg\nVitamins: Vitamin A (1000 IU), Vitamin C (3 mg), Vitamin B6 (0.2 mg)\nMinerals: Calcium (25 mg), Iron (1 mg), Magnesium (20 mg)"
            ),
            Recipe(
                R.drawable.dinner_coconut_crusted_shrimp_with_pineapple_chili_sauce,
                "Coconut Crusted Shrimp With Pineapple Chili Sauce",
                "Crispy shrimp coated in coconut flakes served with a tangy pineapple sauce.",
                ingredients = listOf("200g shrimp", "1/2 cup shredded coconut", "1/2 cup breadcrumbs", "1 egg", "1/2 cup pineapple, diced", "1 tablespoon chili sauce", "Salt", "Pepper"),
                instructions = "1. Preheat oven to 375°F. Dip shrimp in egg, then coat with a mixture of coconut and breadcrumbs.\n2. Bake for 15-20 minutes until golden.\n3. For sauce, combine pineapple and chili sauce in a bowl. Serve with shrimp.",
                nutritionalInfo = "Protein: 24g\nFats: 14g\nCarbohydrates: 18g\nFiber: 2g\nCalories: 320 kcal\nSodium: 300 mg\nCholesterol: 150 mg\nVitamins: Vitamin A (300 IU), Vitamin C (10 mg), Vitamin B6 (0.3 mg)\nMinerals: Calcium (20 mg), Iron (2 mg), Magnesium (25 mg)"
            ),
            Recipe(
                R.drawable.dinner_chili_chicken,
                "Chili Chicken",
                "Spicy chicken stir-fried with bell peppers and onions.",
                ingredients = listOf("200g chicken breast, sliced", "1 bell pepper, sliced", "1 onion, sliced", "2 tablespoons soy sauce", "1 tablespoon chili sauce", "1 tablespoon olive oil", "Salt", "Pepper"),
                instructions = "1. Heat oil in a pan, add chicken, and cook until browned.\n2. Add bell pepper and onion, stir-fry for 5 minutes.\n3. Add soy sauce and chili sauce, cook for another 2 minutes. Serve warm.",
                nutritionalInfo = "Protein: 30g\nFats: 10g\nCarbohydrates: 12g\nFiber: 2g\nCalories: 280 kcal\nSodium: 450 mg\nCholesterol: 75 mg\nVitamins: Vitamin A (800 IU), Vitamin C (20 mg), Vitamin B6 (0.5 mg)\nMinerals: Calcium (20 mg), Iron (2 mg), Magnesium (30 mg)"
            ),
            Recipe(
                R.drawable.dinner_chicken_with_lemon_herb_sauce,
                "Chicken With Lemon Herb Sauce",
                "Grilled chicken topped with a zesty lemon herb sauce.",
                ingredients = listOf("200g chicken breast", "1 lemon, juiced", "1 tablespoon olive oil", "Mixed herbs (parsley, thyme)", "Salt", "Pepper"),
                instructions = "1. Season chicken with salt and pepper, grill for 6-7 minutes on each side.\n2. In a bowl, mix lemon juice, olive oil, and herbs.\n3. Drizzle sauce over grilled chicken before serving.",
                nutritionalInfo = "Protein: 32g\nFats: 9g\nCarbohydrates: 1g\nFiber: 0g\nCalories: 220 kcal\nSodium: 70 mg\nCholesterol: 85 mg\nVitamins: Vitamin C (15 mg), Vitamin B6 (0.4 mg)\nMinerals: Calcium (10 mg), Iron (1 mg), Magnesium (20 mg)"
            ),
            Recipe(
                R.drawable.dinner_chicken_katsu_with_ginger_rice,
                "Chicken Katsu With Ginger Rice",
                "Breaded chicken cutlet served with fragrant ginger rice.",
                ingredients = listOf("200g chicken breast", "1 cup panko breadcrumbs", "1 cup cooked rice", "1 teaspoon ginger, grated", "1 egg", "Salt", "Pepper"),
                instructions = "1. Season chicken with salt and pepper, dip in egg and coat with panko.\n2. Heat oil in a pan and fry chicken until golden.\n3. Mix ginger with cooked rice, serve chicken on top.",
                nutritionalInfo = "Protein: 30g\nFats: 15g\nCarbohydrates: 40g\nFiber: 2g\nCalories: 400 kcal\nSodium: 350 mg\nCholesterol: 70 mg\nVitamins: Vitamin A (200 IU), Vitamin C (2 mg), Vitamin B6 (0.5 mg)\nMinerals: Calcium (30 mg), Iron (2 mg), Magnesium (40 mg)"
            ),
            Recipe(
                R.drawable.dinner_cajun_cabbage_skillet,
                "Cajun Cabbage Skillet",
                "A spicy and flavorful skillet dish with cabbage and sausage.",
                ingredients = listOf("1/2 head cabbage, chopped", "100g sausage, sliced", "1 onion, sliced", "1 tablespoon Cajun seasoning", "1 tablespoon olive oil", "Salt", "Pepper"),
                instructions = "1. Heat oil in a skillet, add sausage and cook until browned.\n2. Add onion and cabbage, sprinkle with Cajun seasoning, and stir-fry until cabbage is tender.\n3. Serve warm.",
                nutritionalInfo = "Protein: 15g\nFats: 12g\nCarbohydrates: 10g\nFiber: 4g\nCalories: 220 kcal\nSodium: 600 mg\nCholesterol: 30 mg\nVitamins: Vitamin A (500 IU), Vitamin C (20 mg), Vitamin B6 (0.2 mg)\nMinerals: Calcium (50 mg), Iron (2 mg), Magnesium (25 mg)"
            ),
            Recipe(
                R.drawable.dinner_beef_stir_fry,
                "Beef Stir Fry",
                "Tender beef stir-fried with vegetables in a savory sauce.",
                ingredients = listOf("200g beef, sliced", "1 cup mixed vegetables", "2 tablespoons soy sauce", "1 tablespoon olive oil", "1 garlic clove, minced", "Salt", "Pepper"),
                instructions = "1. Heat oil in a pan, add garlic and beef, and cook until browned.\n2. Add vegetables and soy sauce, stir-fry until vegetables are tender.\n3. Serve hot over rice.",
                nutritionalInfo = "Protein: 28g\nFats: 15g\nCarbohydrates: 15g\nFiber: 3g\nCalories: 350 kcal\nSodium: 700 mg\nCholesterol: 80 mg\nVitamins: Vitamin A (300 IU), Vitamin C (15 mg), Vitamin B6 (0.4 mg)\nMinerals: Calcium (20 mg), Iron (3 mg), Magnesium (35 mg)"
            )
        ))
    )

    // Initialize BottomSheetScaffold state and coroutine scope
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    // State to hold the currently selected recipe, using rememberSaveable for state persistence
    var selectedRecipe by rememberSaveable(stateSaver = RecipeSaver) { mutableStateOf(defaultRecipe) }

    // Create a BottomSheetScaffold to display the recipe details
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
            // Iterate through each section and display them
            sections.forEach { (title, description, recipes) ->
                item {
                    Section(title, description, recipes) { recipe ->
                        // Launch a coroutine to expand the bottom sheet with the selected recipe
                        coroutineScope.launch {
                            selectedRecipe = recipe // Update the selected recipe
                            bottomSheetScaffoldState.bottomSheetState.expand() // Expand the bottom sheet
                        }
                    }
                }
            }
        }
    }
}

// Composable function to display the content of the bottom sheet
@Composable
fun BottomSheetContent(recipe: Recipe) {
    val configuration = LocalConfiguration.current // Get current configuration
    val screenHeight = configuration.screenHeightDp.dp

    // Display recipe details in a LazyColumn
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = screenHeight * 0.8f) // Set max height to 80% of screen height
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
                    .clip(CircleShape) // Clip the image to a circle shape
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Ingredients", style = MaterialTheme.typography.headlineSmall) // Ingredients section header
            recipe.ingredients.forEach { ingredient -> // List all ingredients
                Text(text = ingredient, style = MaterialTheme.typography.bodyMedium)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Instructions", style = MaterialTheme.typography.headlineSmall) // Instructions section header
            Text(text = recipe.instructions, style = MaterialTheme.typography.bodyMedium) // Display cooking instructions
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Nutritional Information", style = MaterialTheme.typography.headlineSmall) // Nutritional info section header
            Text(text = recipe.nutritionalInfo, style = MaterialTheme.typography.bodyMedium) // Display nutritional information
        }
    }
}

// Composable function to display a section of recipes
@Composable
fun Section(title: String, description: String, recipes: List<Recipe>, onRecipeClick: (Recipe) -> Unit) {
    Column {
        Text(text = title, style = MaterialTheme.typography.headlineSmall)
        Text(text = description, style = MaterialTheme.typography.bodySmall)
        Spacer(modifier = Modifier.height(16.dp))

        // Display recipes in a horizontally scrollable LazyRow
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(recipes) { recipe -> // Iterate over each recipe in the list
                RecipeCard(recipe, onRecipeClick) // Display each recipe as a card
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

// Composable function to display an individual recipe card
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
