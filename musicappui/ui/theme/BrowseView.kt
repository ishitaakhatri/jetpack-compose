package com.example.musicappui.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.musicappui.R

@Composable
fun BrowseScreen() {
    val categories = listOf("Party", "Popular Radio", "Happy", "Mix", "Pop", "Indie")

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(categories) { category ->
            BrowseingItem(cat = category, drawable = R.drawable.baseline_apps_24)
        }
    }
}

@Composable
fun BrowseingItem(
    cat: String,
    drawable: Int
) {
    androidx.compose.material3.Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f),
        border = androidx.compose.foundation.BorderStroke(1.dp, androidx.compose.ui.graphics.Color.DarkGray),
        shape = MaterialTheme.shapes.medium,
        elevation = androidx.compose.material3.CardDefaults.cardElevation(6.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
        ) {
            androidx.compose.foundation.Image(
                painter = androidx.compose.ui.res.painterResource(id = drawable),
                contentDescription = cat,
                modifier = Modifier
                    .size(64.dp)
                    .padding(bottom = 12.dp)
            )
            androidx.compose.material3.Text(
                text = cat,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

