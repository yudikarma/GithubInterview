package com.example.githubinterview.domain.model

data class User(
    val id: Int?,
    val login: String?,
    val avatarUrl: String?,
    val name: String?,
    val followers: Int?,
    val publicRepos: Int?
)
