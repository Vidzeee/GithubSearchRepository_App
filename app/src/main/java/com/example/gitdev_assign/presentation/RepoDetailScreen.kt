package com.example.gitdev_assign.presentation

import android.webkit.WebView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialogDefaults.containerColor
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItemDefaults.contentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gitdev_assign.ViewModel.GitSearchViewModel
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepoDetailsScreen(
    owner: String,
    repo: String,
    viewModel: GitSearchViewModel,
    navController: NavController
) {
    // Observing state changes
    val repoDetails by viewModel.repoDetails.observeAsState() // Default is null
    val contributors by viewModel.contributors.observeAsState(emptyList()) // Default is empty list
    val loading by viewModel.loading.observeAsState(false) // Default is false
    val error by viewModel.error.observeAsState() // Default is null



    // Trigger loading of data once composable is loaded
    LaunchedEffect(Unit) {
        viewModel.fetchRepoDetails(owner, repo)
        viewModel.fetchContributorsWithRepos(owner, repo)
    }

    // UI layout with Scaffold
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(repo) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
               )

        }, containerColor = Color.Black,
          contentColor = Color.White
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            when {
                loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                error != null -> {
                    Text("Error: $error", modifier = Modifier.align(Alignment.Center))
                }
                else -> {
                    LazyColumn {
                        // Repository Details
                        item {
                            repoDetails?.let {
                                Text("Repository: ${it.name}", style = MaterialTheme.typography.titleMedium)
                                Text("Description: ${it.description ?: "No description available"}",
                                    style = MaterialTheme.typography.titleMedium)
                                TextButton(onClick = {
                                    val encodedUrl = URLEncoder.encode(it.html_url, StandardCharsets.UTF_8.toString())
                                    navController.navigate("webView/$encodedUrl")
                                }) {
                                    Text("View on GitHub")
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            // Contributors Header
                            Text(
                                text = "Contributors with their Contributions",
                                style = MaterialTheme.typography.headlineMedium
                            )

                            // List of contributors and their repositories
                            ContributorsList(contributors = contributors)
                        }
                    }
                }
            }
        }
    }
}






