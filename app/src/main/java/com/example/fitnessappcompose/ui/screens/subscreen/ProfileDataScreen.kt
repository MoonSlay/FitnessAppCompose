package com.example.fitnessappcompose.ui.screens.subscreen

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.fitnessappcompose.utils.*

@Composable
fun ProfileDataScreen(navController: NavController) {
    val context = LocalContext.current
    var isEditing by remember { mutableStateOf(false) }

    if (isEditing) {
        EditProfileDataScreen(navController, context) { isEditing = false }
    } else {
        DisplayProfileDataScreen(navController) { isEditing = true }
    }
}

@Composable
fun DisplayProfileDataScreen(navController: NavController, onEditClick: () -> Unit) {
    val context = LocalContext.current
    val fullName = getFullName(context)
    val username = getUsername(context)
    val gender = getGender(context)
    val age = getAge(context)
    val height = getHeight(context)
    val weight = getWeight(context)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Profile Data", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "Full Name: $fullName")
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Username: $username")
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Gender: $gender")
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Age: $age")
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Height: $height")
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Weight: $weight")
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = onEditClick) {
            Text("Edit")
        }
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = { navController.navigateUp() }) {
            Text("Back")
        }
    }
}

@Composable
fun EditProfileDataScreen(navController: NavController, context: Context, onSaveClick: () -> Unit) {
    var fullName by remember { mutableStateOf(getFullName(context)) }
    var username by remember { mutableStateOf(getUsername(context)) }
    var gender by remember { mutableStateOf(getGender(context)) }
    var age by remember { mutableStateOf(getAge(context)) }
    var height by remember { mutableStateOf(getHeight(context)) }
    var weight by remember { mutableStateOf(getWeight(context)) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Edit Profile Data", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(value = fullName, onValueChange = { fullName = it }, label = { Text("Full Name") })
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(value = username, onValueChange = { username = it }, label = { Text("Username") })
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(value = gender, onValueChange = { gender = it }, label = { Text("Gender") })
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(value = age, onValueChange = { age = it }, label = { Text("Age") })
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(value = height, onValueChange = { height = it }, label = { Text("Height") })
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(value = weight, onValueChange = { weight = it }, label = { Text("Weight") })
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = {
            saveProfileData(context, fullName, username, gender, age, height, weight)
            onSaveClick()
        }) {
            Text("Save")
        }
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = { navController.navigateUp() }) {
            Text("Cancel")
        }
    }
}

fun saveProfileData(context: Context, fullName: String, username: String, gender: String, age: String, height: String, weight: String) {
    setFullName(context, fullName)
    setUsername(context, username)
    setGender(context, gender)
    setAge(context, age)
    setHeight(context, height)
    setWeight(context, weight)
}