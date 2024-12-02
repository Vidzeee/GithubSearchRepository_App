package com.example.gitdev_assign.api

import com.example.gitdev_assign.api.SearchRepositoriesResponse
import com.example.gitdev_assign.models.Contributor
import com.example.gitdev_assign.models.Repository
import com.example.gitdev_assign.models.UserRepository
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubApiService {

    @GET("search/repositories")
    suspend fun searchRepositories(
        @Query("q") query: String,
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 10
    ): Response<SearchRepositoriesResponse>

    @GET("repos/{owner}/{repo}")
    suspend fun getRepoDetails(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): Response<Repository>

        @GET("repos/{owner}/{repo}/contributors")
        suspend fun getContributors(
            @Path("owner") owner: String,
            @Path("repo") repo: String
        ): Response<List<Contributor>>

        @GET("users/{username}/repos")
        suspend fun getUserRepositories(
            @Path("username") username: String
        ): Response<List<UserRepository>>
}


