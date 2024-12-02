package com.example.gitdev_assign.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavHostController
import com.example.gitdev_assign.Data.RepositoryEntity
import com.example.gitdev_assign.ViewModel.GitSearchViewModel


@Composable
fun HomeScreen(navController: NavHostController, viewModel: GitSearchViewModel) {

    // Observing query from the ViewModel
    val query by viewModel.query.observeAsState("")

    // Observing repository list from the ViewModel
    val repositoryList by viewModel.repositories.observeAsState(emptyList())

    // Handling loading state (if needed)
    val isLoading by viewModel.loading.observeAsState(false)
    val errorMessage by viewModel.error.observeAsState("")

    // UI structure
    Column(modifier = Modifier.fillMaxSize()) {
        // Search Bar
        SearchBar(
            query = query,
            onQueryChanged = { viewModel.updateQuery(it) }, // Update the query as user types
            onSearchClicked = { viewModel.fetchRepositories(query) } // Trigger search with current query
        )

        // Show loading indicator while fetching data
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }

        // Show error message if there's an error
        errorMessage?.let {
            Text(text = it, color = Color.Red, modifier = Modifier.padding(16.dp))
        }

        // List of repositories (either fetched from the API or the database)
        // List of repositories from the database
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(repositoryList) { repo ->  // `repo` here is of type RepositoryEntity
                RepositoryItem(
                    repo = repo,
                    onClick = {
                        val owner = repo.owner // 'owner' is a string in RepositoryEntity
                        val repoName = repo.name
                        navController.navigate("repoDetails/$owner/$repoName")
                    },
                    onDelete = { viewModel.deleteRepository(repo.id) } // Delete repository by ID
                )
            }
        }
    }
}

@Composable
fun SearchBar(
    query: String,
    onQueryChanged: (String) -> Unit,
    onSearchClicked: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = query,
            onValueChange = onQueryChanged,
            placeholder = { Text(text = "Search repositories...") },
            modifier = Modifier
                .weight(1f)
                .height(56.dp),
            singleLine = true,
            maxLines = 1
        )
        Spacer(modifier = Modifier.width(8.dp))
        Button(
            onClick = onSearchClicked,
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
        ) {
            Text(text = "Search")
        }
    }
}

@Composable
fun RepositoryItem(repo: RepositoryEntity, onClick: (String) -> Unit, onDelete: (Int) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick(repo.name) },
        elevation = CardDefaults.elevatedCardElevation(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Repository Name
            Text(
                text = repo.name,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            // Repository Description
            Text(
                text = repo.description ?: "No description",
                style = MaterialTheme.typography.bodySmall,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            // Delete Button
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { onDelete(repo.id) }) {  // `repo.id` is an Int here
                Text(text = "Delete")
            }
        }
    }
}





