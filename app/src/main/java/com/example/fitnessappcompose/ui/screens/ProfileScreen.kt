package com.example.fitnessappcompose.ui.screens

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.fitnessappcompose.R
import com.example.fitnessappcompose.utils.getFullName

private const val PREFS_NAME = "profile_prefs"
private const val KEY_SELECTED_AVATAR = "selected_avatar"

@Composable
fun ProfileScreen(
    navController: NavController,
    toggleDarkMode: (Boolean) -> Unit,
    isDarkMode: Boolean
) {
    val context = LocalContext.current
    val fullName = getFullName(context)
    val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    var selectedAvatar by remember { mutableIntStateOf(sharedPreferences.getInt(KEY_SELECTED_AVATAR, R.drawable.avatar_default_guy)) } // Default avatar
    val imageUri by remember { mutableStateOf<Uri?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    // List of avatar options with custom names
    val avatarOptions = listOf(
        R.drawable.avatar_leon to "Leon",
        R.drawable.avatar_kurama to "Kurama",
        R.drawable.avatar_mr_known to "Unknown",
        R.drawable.avatar_chill_guy to "Chill Guy",
        R.drawable.avatar_dark_side to "Dark Side",
        R.drawable.avatar_bored_monkey to "Bored Monkey",
        R.drawable.avatar_last_bender to "Last Bender",
        R.drawable.avatar_stylist_fox to "Stylist Fox",
        R.drawable.avatar_default_guy to "Guy"
    )

// Dialog for avatar selection
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Choose an Avatar") },
            text = {
                Column {
                    avatarOptions.forEach { (avatarId, avatarName) ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    selectedAvatar = avatarId // Update selected avatar
                                    showDialog = false // Close dialog
                                    sharedPreferences.edit().putInt(KEY_SELECTED_AVATAR, avatarId).apply() // Save to SharedPreferences
                                }
                                .padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = avatarId),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = avatarName) // Display avatar name
                        }
                    }
                }
            },
            confirmButton = {},
            dismissButton = {}
        )
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Circular Image that allows user to click to change
        imageUri?.let {
            val inputStream = context.contentResolver.openInputStream(it)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = null,
                modifier = Modifier
                    .size(150.dp) // Increased size of the image
                    .clip(CircleShape)
                    .clickable(onClick = { showDialog = true }) // Show dialog on click
            )
        } ?: Image(
            painter = painterResource(id = selectedAvatar), // Display the selected avatar
            contentDescription = "Profile Avatar",
            modifier = Modifier
                .size(150.dp) // Increased size of the image
                .clip(CircleShape) // Make it circular
                .clickable { showDialog = true } // Show dialog on click
        )

        Spacer(modifier = Modifier.height(16.dp)) // Space between image and text

        // Full Name Text
        Text(text = fullName, style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(50.dp))

        // Navigation Buttons with fixed width
        val buttonModifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)

        Button(onClick = { navController.navigate("profile_data") }, modifier = buttonModifier) {
            Text("Profile")
        }
        Spacer(modifier = Modifier.height(15.dp))
        Button(onClick = { navController.navigate("goal_data") }, modifier = buttonModifier) {
            Text("Goals")
        }
        Spacer(modifier = Modifier.height(15.dp))
        Button(onClick = { navController.navigate("about_us") }, modifier = buttonModifier) {
            Text("About Us")
        }

        Spacer(modifier = Modifier.height(50.dp))
        Switch(
            checked = isDarkMode,
            onCheckedChange = { isChecked ->
                toggleDarkMode(isChecked)
            }
        )
        Spacer(modifier = Modifier.height(15.dp))
        Text(text = "Dark Mode", style = MaterialTheme.typography.bodyMedium)

    }
}