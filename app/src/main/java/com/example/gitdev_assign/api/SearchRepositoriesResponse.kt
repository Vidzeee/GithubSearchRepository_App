package com.example.gitdev_assign.api

import com.example.gitdev_assign.models.Repository

data class SearchRepositoriesResponse(
    val total_count: String,            // Total number of results
    val incomplete_results: Boolean, // Whether results are incomplete
    val items: List<Repository>      // List of repositories
)
