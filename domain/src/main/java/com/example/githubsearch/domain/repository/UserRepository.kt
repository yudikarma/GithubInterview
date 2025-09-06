package com.example.githubinterview.domain.repository

import com.example.githubinterview.domain.model.Result
import com.example.githubinterview.domain.model.User
import kotlinx.coroutines.flow.Flow


interface UserRepository {

    fun searchUsers(query: String?): Flow<Result<List<User>>>


    fun getUserDetails(username: String?): Flow<Result<User>>
}

