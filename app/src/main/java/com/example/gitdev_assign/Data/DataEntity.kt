package com.example.gitdev_assign.Data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "repositories")
data class RepositoryEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val description: String?,
    val html_url: String,
    val owner: String
)