package com.example.fitnessappcompose

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text

@Composable
fun ItemList(items: List<String>) {
    LazyColumn {
        items(items) { item ->
            Text(text = item)
        }
    }
}