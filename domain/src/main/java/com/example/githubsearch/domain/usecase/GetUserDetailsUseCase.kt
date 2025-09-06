package com.example.githubsearch.domain.usecase


import com.example.githubinterview.domain.model.Result
import com.example.githubinterview.domain.model.User
import com.example.githubinterview.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserDetailsUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    operator fun invoke(username: String?): Flow<Result<User>> {
        return userRepository.getUserDetails(username)
    }
}
