package com.example.gitdev_assign.models

import com.example.gitdev_assign.Data.RepositoryEntity


data class Repository(
    val id: Int,
    val name: String,
    val full_name: String,
    val description: String?,
    val html_url: String,
    val owner: Owner
)

data class Owner(
    val login: String,   // This is the owner's username
    val avatar_url: String,
    val html_url: String
)

data class Contributor(
    val login: String,
    val avatar_url: String,
    val contributions: Int
)

data class UserRepository(
    val id: Int,
    val name: String,
    val full_name: String,
    val description: String?,
    val html_url: String
)


fun Repository.toEntity(): RepositoryEntity {
    return RepositoryEntity(
        id = this.id,
        name = this.name,
        description = this.description,
        html_url = this.html_url,
        owner = this.owner.login // Assuming 'owner' is an object with 'login'
    )
}






