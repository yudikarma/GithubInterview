package com.example.githubinterview.data.repository

import android.net.http.HttpException
import com.example.githubinterview.data.local.UserDao
import com.example.githubinterview.data.model.toDomainUser
import com.example.githubinterview.data.remote.GitHubService
import com.example.githubinterview.domain.model.Result
import com.example.githubinterview.domain.model.User
import com.example.githubinterview.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject


class UserRepositoryImpl @Inject constructor(
    private val gitHubService: GitHubService,
    private val userDao: UserDao
) : UserRepository {

    /**
     * Searches for users by making a network call to the GitHub API.
     * The implementation handles network calls and local data persistence.
     * It emits states (Loading, Success, Error) through a Flow.
     * @param query The search query.
     * @return A Flow of Result containing a list of users.
     */
    override fun searchUsers(query: String?): Flow<Result<List<User>>> = flow {
        // First, emit a loading state to the UI.
        emit(Result.Loading)
        try {
            // Make the network call and check for a successful response.
            val response = gitHubService.searchUsers(query)
            if (response.isSuccessful) {
                val searchResponse = response.body()
                if (searchResponse != null) {
                    // Map the network DTOs to domain-level models.
                    val users = searchResponse.items.map { it.toDomainUser() }
                    // Store the fetched data in the local database for caching.
                    userDao.insertAll(searchResponse.items)
                    // On success, emit the list of domain users to the UI.
                    emit(Result.Success(users))
                } else {
                    // The response body was empty.
                    emit(Result.Success(emptyList()))
                }
            } else {
                // The network request failed.
                emit(Result.Error(Exception("Network request failed with code: ${response.code()}")))
            }
        } catch (e: Exception) {
            // On failure, emit an error state to the UI.
            emit(Result.Error(e))
        }
    }

    override fun getUserDetails(username: String?): Flow<Result<User>> = flow {
        emit(Result.Loading)
        try {
            // Make the network call.
            val response = gitHubService.getUserDetails(username)
            if (response.isSuccessful) {
                val gitHubUser = response.body()
                if (gitHubUser != null) {
                    val user = gitHubUser.toDomainUser()
                    // On success, emit the user details to the UI.
                    emit(Result.Success(user))
                } else {
                    emit(Result.Error(Exception("User not found or response was empty.")))
                }
            } else {
                emit(Result.Error(Exception("Network request failed with code: ${response.code()}")))
            }
        } catch (e: IOException) {
            emit(Result.Error(e))
        }  catch (e: Exception) {
            emit(Result.Error(e))
        }
    }

}
