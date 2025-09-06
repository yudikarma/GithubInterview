package com.example.githubinterview.domain.usecase

import com.example.githubinterview.domain.model.Result
import com.example.githubinterview.domain.model.User
import com.example.githubinterview.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class SearchUsersUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    operator fun invoke(query: String?): Flow<Result<List<User>>> {
        return userRepository.searchUsers(query)
    }
}
