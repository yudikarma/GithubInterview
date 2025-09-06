package com.example.githubsearch.domain.usecase

import com.example.githubinterview.domain.model.User
import com.example.githubinterview.domain.repository.UserRepository
import com.example.githubinterview.domain.usecase.SearchUsersUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import com.example.githubinterview.domain.model.Result

class SearchUsersUseCaseTest {

    // Declare the mock repository with lateinit
    private lateinit var mockUserRepository: UserRepository
    private lateinit var searchUsersUseCase: SearchUsersUseCase


    @Before
    fun setUp() {
        // Initialize the mock object
        mockUserRepository = mockk()
        // Initialize the use case with the mock repository
        searchUsersUseCase = SearchUsersUseCase(mockUserRepository)
    }

    @Test
    fun `invoke should emit success result with users from repository`() = runTest {
        // Given
        val mockUsers = listOf(User(1,"test_user_1", "url1","uwefwe",23,43), User(2,"test_user_2", "url2","karma",23,43))
        coEvery { mockUserRepository.searchUsers(any()) } returns flowOf(Result.Success(mockUsers))

        // When
        val resultFlow = searchUsersUseCase.invoke("test")

        // Then
        resultFlow.collect { result ->
            if (result is Result.Success) {
                assertEquals(mockUsers, result.data)
            }
        }
    }

    @Test
    fun `invoke should emit error result when repository throws exception`() = runTest {
        // Given
        val exception = RuntimeException("Network error")
        coEvery { mockUserRepository.searchUsers(any()) } returns flowOf(Result.Error(exception))

        // When
        val resultFlow = searchUsersUseCase.invoke("test")

        // Then
        resultFlow.collect { result ->
            if (result is Result.Error) {
                assertEquals(exception, result.exception)
            }
        }
    }
}
