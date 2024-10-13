package com.example.fitnessappcompose.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fitnessappcompose.R

data class Artist(val icon: ImageVector, val name: String, val description: String)

@Composable
fun RecipeScreen() {
    val sections = listOf(
        "Rock Star Artist" to listOf(
            Artist(ImageVector.vectorResource(id = R.drawable.ic_baseline_add_a_photo_24), "Rock Artist 1", "Description 1"),
            Artist(ImageVector.vectorResource(id = R.drawable.ic_baseline_add_a_photo_24), "Rock Artist 2", "Description 2"),
            Artist(ImageVector.vectorResource(id = R.drawable.ic_baseline_add_a_photo_24), "Rock Artist 3", "Description 3"),
            Artist(ImageVector.vectorResource(id = R.drawable.ic_baseline_add_a_photo_24), "Rock Artist 4", "Description 4"),
            Artist(ImageVector.vectorResource(id = R.drawable.ic_baseline_add_a_photo_24), "Rock Artist 5", "Description 5")
        ),
        "Pop Star Artist" to listOf(
            Artist(ImageVector.vectorResource(id = R.drawable.ic_baseline_add_a_photo_24), "Pop Artist 1", "Description 1"),
            Artist(ImageVector.vectorResource(id = R.drawable.ic_baseline_add_a_photo_24), "Pop Artist 2", "Description 2"),
            Artist(ImageVector.vectorResource(id = R.drawable.ic_baseline_add_a_photo_24), "Pop Artist 3", "Description 3"),
            Artist(ImageVector.vectorResource(id = R.drawable.ic_baseline_add_a_photo_24), "Pop Artist 4", "Description 4"),
            Artist(ImageVector.vectorResource(id = R.drawable.ic_baseline_add_a_photo_24), "Pop Artist 5", "Description 5")
        ),
        "Jazz Star Artist" to listOf(
            Artist(ImageVector.vectorResource(id = R.drawable.ic_baseline_add_a_photo_24), "Jazz Artist 1", "Description 1"),
            Artist(ImageVector.vectorResource(id = R.drawable.ic_baseline_add_a_photo_24), "Jazz Artist 2", "Description 2"),
            Artist(ImageVector.vectorResource(id = R.drawable.ic_baseline_add_a_photo_24), "Jazz Artist 3", "Description 3"),
            Artist(ImageVector.vectorResource(id = R.drawable.ic_baseline_add_a_photo_24), "Jazz Artist 4", "Description 4"),
            Artist(ImageVector.vectorResource(id = R.drawable.ic_baseline_add_a_photo_24), "Jazz Artist 5", "Description 5")
        ),
        "Classical Star Artist" to listOf(
            Artist(ImageVector.vectorResource(id = R.drawable.ic_baseline_add_a_photo_24), "Classical Artist 1", "Description 1"),
            Artist(ImageVector.vectorResource(id = R.drawable.ic_baseline_add_a_photo_24), "Classical Artist 2", "Description 2"),
            Artist(ImageVector.vectorResource(id = R.drawable.ic_baseline_add_a_photo_24), "Classical Artist 3", "Description 3"),
            Artist(ImageVector.vectorResource(id = R.drawable.ic_baseline_add_a_photo_24), "Classical Artist 4", "Description 4"),
            Artist(ImageVector.vectorResource(id = R.drawable.ic_baseline_add_a_photo_24), "Classical Artist 5", "Description 5")
        ),
        "Hip Hop Star Artist" to listOf(
            Artist(ImageVector.vectorResource(id = R.drawable.ic_baseline_add_a_photo_24), "Hip Hop Artist 1", "Description 1"),
            Artist(ImageVector.vectorResource(id = R.drawable.ic_baseline_add_a_photo_24), "Hip Hop Artist 2", "Description 2"),
            Artist(ImageVector.vectorResource(id = R.drawable.ic_baseline_add_a_photo_24), "Hip Hop Artist 3", "Description 3"),
            Artist(ImageVector.vectorResource(id = R.drawable.ic_baseline_add_a_photo_24), "Hip Hop Artist 4", "Description 4"),
            Artist(ImageVector.vectorResource(id = R.drawable.ic_baseline_add_a_photo_24), "Hip Hop Artist 5", "Description 5")
        ),
        "Country Star Artist" to listOf(
            Artist(ImageVector.vectorResource(id = R.drawable.ic_baseline_add_a_photo_24), "Country Artist 1", "Description 1"),
            Artist(ImageVector.vectorResource(id = R.drawable.ic_baseline_add_a_photo_24), "Country Artist 2", "Description 2"),
            Artist(ImageVector.vectorResource(id = R.drawable.ic_baseline_add_a_photo_24), "Country Artist 3", "Description 3"),
            Artist(ImageVector.vectorResource(id = R.drawable.ic_baseline_add_a_photo_24), "Country Artist 4", "Description 4"),
            Artist(ImageVector.vectorResource(id = R.drawable.ic_baseline_add_a_photo_24), "Country Artist 5", "Description 5")
        ),
        "Blues Star Artist" to listOf(
            Artist(ImageVector.vectorResource(id = R.drawable.ic_baseline_add_a_photo_24), "Blues Artist 1", "Description 1"),
            Artist(ImageVector.vectorResource(id = R.drawable.ic_baseline_add_a_photo_24), "Blues Artist 2", "Description 2"),
            Artist(ImageVector.vectorResource(id = R.drawable.ic_baseline_add_a_photo_24), "Blues Artist 3", "Description 3"),
            Artist(ImageVector.vectorResource(id = R.drawable.ic_baseline_add_a_photo_24), "Blues Artist 4", "Description 4"),
            Artist(ImageVector.vectorResource(id = R.drawable.ic_baseline_add_a_photo_24), "Blues Artist 5", "Description 5")
        )
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        items(sections) { (title, artists) ->
            Section(title = title, artists = artists)
        }
    }
}

@Composable
fun Section(title: String, artists: List<Artist>) {
    Column {
        Box(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.align(Alignment.CenterStart)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(artists) { artist ->
                ArtistCard(artist = artist)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun ArtistCard(artist: Artist) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier.size(width = 140.dp, height = 100.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(imageVector = artist.icon, contentDescription = "Artist icon")
            Spacer(modifier = Modifier.height(8.dp))
            Text(artist.name, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(artist.description, style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecipeScreenPreview() {
    RecipeScreen()
}