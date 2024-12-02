package com.example.gitdev_assign.Navigation

sealed class Screen(val route: String) {
    object HomeScreen : Screen("home_screen")
    object RepoDetailsScreen : Screen("repo_details_screen")
}


