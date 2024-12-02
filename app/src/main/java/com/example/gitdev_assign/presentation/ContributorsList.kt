package com.example.gitdev_assign.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.gitdev_assign.models.Contributor
import com.example.gitdev_assign.models.UserRepository

@Composable
fun ContributorsList(contributors: List<Pair<Contributor, List<UserRepository>>>) {
    Column {
        contributors.forEach { (contributor, userRepositories) ->
            // ContributorItem can display individual contributor details
            ContributorItem(contributor = contributor)

            // LazyRow for the user's repositories
            LazyRow(modifier = Modifier.fillMaxWidth()) {
                items(userRepositories) { repo ->
                    Text(text = repo.name, modifier = Modifier.padding(8.dp)) // Example, modify as per your requirement
                }
            }
        }
    }
}

@Composable
fun ContributorItem(contributor: Contributor) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Image(
            painter = rememberImagePainter(contributor.avatar_url),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(text = contributor.login, style = MaterialTheme.typography.headlineLarge)
            Text(text = "Contributions: ${contributor.contributions}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}
