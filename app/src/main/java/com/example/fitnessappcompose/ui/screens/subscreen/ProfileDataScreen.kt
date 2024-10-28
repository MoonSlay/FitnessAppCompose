package com.example.fitnessappcompose.ui.screens.subscreen

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
            "Height" to "$height m",
            "Weight" to "$weight kg"
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

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text(text = "Edit Profile Data", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(20.dp))
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium,
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedTextField(value = fullName, onValueChange = { fullName = it }, label = { Text("Full Name") }, singleLine = true)
                    OutlinedTextField(value = username, onValueChange = { username = it }, label = { Text("Username") }, singleLine = true)
                    OutlinedTextField(value = gender, onValueChange = { gender = it }, label = { Text("Gender") }, singleLine = true)
                    OutlinedTextField(
                        value = age,
                        onValueChange = { age = it },
                        label = { Text("Age") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = height,
                            onValueChange = { height = it },
                            label = { Text("Height") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.weight(1f),
                            singleLine = true
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "m", style = MaterialTheme.typography.bodyMedium)
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = weight,
                            onValueChange = { weight = it },
                            label = { Text("Weight") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.weight(1f),
                            singleLine = true
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "kg", style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }

        item {
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
}


fun saveProfileData(context: Context, fullName: String, username: String, gender: String, age: String, height: String, weight: String) {
    setFullName(context, fullName)
    setUsername(context, username)
    setGender(context, gender)
    setAge(context, age)
    setHeight(context, height)
    setWeight(context, weight)
}
