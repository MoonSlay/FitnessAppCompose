package com.example.fitnessappcompose.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavController
import com.example.fitnessappcompose.R

@Composable
fun BottomNavigationBar(navController: NavController) {
    NavigationBar {
        val items = listOf(
            NavigationItem("Dashboard", Icons.Filled.Home),
            NavigationItem("Recipe", ImageVector.vectorResource(id = R.drawable.ic_recipe)),
            NavigationItem("Training", ImageVector.vectorResource(id = R.drawable.ic_fitness_center)),
            NavigationItem("Report", ImageVector.vectorResource(id = R.drawable.ic_report)),
            NavigationItem("Profile", Icons.Filled.Person),
        )
        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                selected = false, // Update this based on the current route
                onClick = {
                    navController.navigate(item.label)
                }
            )
        }
    }
}

data class NavigationItem(val label: String, val icon: ImageVector)