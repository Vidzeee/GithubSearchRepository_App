package com.example.gitdev_assign.Data

import com.example.gitdev_assign.api.GitHubApiService
import com.example.gitdev_assign.models.Contributor
import com.example.gitdev_assign.models.UserRepository
import javax.inject.Inject

class GitSearchRepository @Inject constructor(
    private val apiService: GitHubApiService,
    private val repositoryDao: RepositoryDao
) {

    suspend fun getRepositories(query: String): List<RepositoryEntity> {
        // Attempt to fetch from the network
        val response = apiService.searchRepositories(query)

        return if (response.isSuccessful) {
            // Make sure response.body() is a list and handle null case
            val repositories = response.body()?.items ?: emptyList()

            // Take the first 15 items (if available)
            val limitedRepositories = repositories.take(15)

            // Map the repositories to RepositoryEntity and save them to the Room database
            repositoryDao.insertRepositories(limitedRepositories.map {
                RepositoryEntity(
                    id = it.id,
                    name = it.name,
                    description = it.description,
                    html_url = it.html_url,
                    owner = it.owner.login
                )
            })

            // Return the limited repositories
            limitedRepositories.map {
                RepositoryEntity(
                    id = it.id,
                    name = it.name,
                    description = it.description,
                    html_url = it.html_url,
                    owner = it.owner.login
                )
            }
        } else {
            // If network request fails, return data from Room database
            repositoryDao.getSavedRepositories().value ?: emptyList()
        }
    }

    suspend fun deleteRepository(repository: RepositoryEntity) {
        repositoryDao.deleteRepository(repository)
    }

    suspend fun deleteAllRepositories() {
        repositoryDao.deleteAllRepositories()
    }


    suspend fun fetchContributors(owner: String, repo: String): List<Contributor>? {
        return try {
            val response = apiService.getContributors(owner, repo)
            if (response.isSuccessful) response.body() else null
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun fetchUserRepositories(username: String): List<UserRepository>? {
        return try {
            val response = apiService.getUserRepositories(username)
            if (response.isSuccessful) response.body() else null
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
