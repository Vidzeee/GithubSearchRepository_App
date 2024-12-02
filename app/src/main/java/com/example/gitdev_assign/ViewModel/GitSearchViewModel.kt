package com.example.gitdev_assign.ViewModel


import com.example.gitdev_assign.models.Contributor
import com.example.gitdev_assign.models.UserRepository
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import android.content.Context
import com.example.gitdev_assign.Data.GitSearchRepository
import com.example.gitdev_assign.Data.RepositoryDao
import com.example.gitdev_assign.Data.RepositoryEntity
import com.example.gitdev_assign.api.GitHubApiService
import com.example.gitdev_assign.models.toEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class GitSearchViewModel @Inject constructor(
    private val repository: GitSearchRepository, // Inject repository
    private val apiService: GitHubApiService,         // Inject ApiService
    private val repositoryDao: RepositoryDao    // Inject RepositoryDao (Room)
) : ViewModel() {

    private val _query = MutableLiveData("")

    val query: LiveData<String> get() = _query

    private val _repositoryList = MutableLiveData<List<RepositoryEntity>>()
    val repositoryList: LiveData<List<RepositoryEntity>> get() = _repositoryList

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private val _repositories = MutableLiveData<List<RepositoryEntity>>()
    val repositories: LiveData<List<RepositoryEntity>> = _repositories

    // Fetch repositories from network and store in Room, if network fetch is successful
    fun fetchRepositories(query: String) {
        viewModelScope.launch {
            val data = repository.getRepositories(query)  // Get repositories from the repository
            _repositories.postValue(data)  // Post data to LiveData
        }
    }

    // Delete a specific repository from Room
    fun deleteRepository(repositoryId: Int) {
        viewModelScope.launch {
            try {
                repositoryDao.deleteRepositoryById(repositoryId)
                // After deleting, refresh the repository list from Room
                _repositories.postValue(repositoryDao.getSavedRepositories().value)
            } catch (e: Exception) {
                _error.postValue("Error deleting repository: ${e.message}")
            }
        }
    }

    // Clear all repositories from Room
    fun clearRepositories() {
        viewModelScope.launch {
            try {
                repositoryDao.deleteAllRepositories()
                // After clearing, refresh the repository list from Room
                _repositories.postValue(emptyList())
            } catch (e: Exception) {
                _error.postValue("Error clearing repositories: ${e.message}")
            }
        }
    }

    // This method updates the query value
    fun updateQuery(newQuery: String) {
        _query.value = newQuery
    }


    private val _repoDetails = MutableLiveData<RepositoryEntity>()
    val repoDetails: LiveData<RepositoryEntity> get() = _repoDetails

    // Fetch repository details (from API)
    fun fetchRepoDetails(owner: String, repo: String) {
        viewModelScope.launch {
            _loading.value = true
            try {
                val repoResponse = apiService.getRepoDetails(owner, repo)
                if (repoResponse.isSuccessful) {
                    // Convert the API response (Repository) to RepositoryEntity
                    repoResponse.body()?.let {
                        _repoDetails.value = it.toEntity() // Convert to RepositoryEntity
                    }
                } else {
                    _error.value = "Error: ${repoResponse.message()}"
                }
                Log.d("API Response repoo", repoResponse.body().toString())
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _loading.value = false
            }
        }
    }

    private val _contributors = MutableLiveData<List<Pair<Contributor, List<UserRepository>>>>()
    val contributors: LiveData<List<Pair<Contributor, List<UserRepository>>>> = _contributors

    // Fetch contributors for a repo
    fun fetchContributorsWithRepos(owner: String, repo: String) {
        viewModelScope.launch {
            val contributorsList = repository.fetchContributors(owner, repo)
            if (contributorsList != null) {
                val contributorsWithRepos = contributorsList.map { contributor ->
                    val userRepos = repository.fetchUserRepositories(contributor.login) ?: emptyList()
                    contributor to userRepos
                }
                _contributors.postValue(contributorsWithRepos)
            }
        }
    }
}





