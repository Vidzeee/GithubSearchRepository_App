package com.example.gitdev_assign.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.gitdev_assign.ViewModel.GitSearchViewModel
import com.example.gitdev_assign.api.WebViewScreen
import com.example.gitdev_assign.presentation.HomeScreen
import com.example.gitdev_assign.presentation.RepoDetailsScreen

@Composable
fun NavGraph(navController: NavHostController, viewModel: GitSearchViewModel) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(viewModel = viewModel, navController = navController)
        }
        composable("repoDetails/{owner}/{repo}") { backStackEntry ->
            val owner = backStackEntry.arguments?.getString("owner") ?: ""
            val repo = backStackEntry.arguments?.getString("repo") ?: ""

            RepoDetailsScreen(
                owner = owner,
                repo = repo,
                viewModel = viewModel,
                navController = navController
            )
        }

        composable("webView/{url}") { backStackEntry ->
            val url = backStackEntry.arguments?.getString("url") ?: ""
            WebViewScreen(url = url)
        }
    }
}

