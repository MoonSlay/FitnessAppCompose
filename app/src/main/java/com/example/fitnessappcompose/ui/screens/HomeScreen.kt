package com.example.fitnessappcompose.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Preview
@Composable
fun HomeScreen() {
    val quotes = listOf(
        "The only bad workout is the one that didn't happen.",
        "Push yourself, because no one else is going to do it for you.",
        "Success starts with self-discipline.",
        "The body achieves what the mind believes.",
        "Don't limit your challenges, challenge your limits."
    )

    var currentQuote by remember { mutableStateOf(quotes[0]) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(5000)
            currentQuote = quotes.random()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Welcome \n to your \n fitness \n journey",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 50.dp)
        )
        Text(
            text = currentQuote,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = "Welcome \n to your \n fitness \n journey",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 50.dp)
        )
        Text(
            text = currentQuote,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = "Welcome \n to your \n fitness \n journey",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 50.dp)
        )
        Text(
            text = currentQuote,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = "Welcome \n to your \n fitness \n journey",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 50.dp)
        )
        Text(
            text = currentQuote,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium
        )

    }
}