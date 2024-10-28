package com.example.fitnessappcompose.ui.screens.subscreen

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
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
        horizontalAlignment = Alignment.Start
    ) {
        Text(text = "Profile Data", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(20.dp))

        // List of profile fields
        listOf(
            "Full Name" to fullName,
            "Username" to username,
            "Gender" to gender,
            "Age" to age,
            "Height" to height,
            "Weight" to weight
        ).forEach { (label, value) ->
            ProfileDataRow(label = label, value = value)
            Spacer(modifier = Modifier.height(10.dp))
        }

        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = onEditClick, modifier = Modifier.fillMaxWidth()) {
            Text("Edit")
        }
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = { navController.navigateUp() }, modifier = Modifier.fillMaxWidth()) {
            Text("Back")
        }
    }
}

@Composable
fun ProfileDataRow(label: String, value: String) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 2.dp,
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "$label:", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = value, style = MaterialTheme.typography.bodyMedium)
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
        OutlinedTextField(
            value = age,
            onValueChange = { age = it },
            label = { Text("Age") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = height,
            onValueChange = { height = it },
            label = { Text("Height") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = weight,
            onValueChange = { weight = it },
            label = { Text("Weight") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = {
            saveProfileData(context, fullName, username, gender, age, height, weight)
            onSaveClick()
        }, modifier = Modifier.fillMaxWidth()) {
            Text("Save")
        }
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = { navController.navigateUp() }, modifier = Modifier.fillMaxWidth()) {
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

