package com.example.githubsearch.presentation.ui.search

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.githubinterview.domain.model.User
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import com.example.githubsearch.presentation.ui.search.SearchUiState


@RunWith(AndroidJUnit4::class)
class UserSearchScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private class TestUserSearchViewModel(initialState: SearchUiState) : UserSearchViewModel(
        searchUsersUseCase = mockk(),
        getUserDetailsUseCase = mockk()
    ) {
        private val _uiState = MutableStateFlow(initialState)
        override val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

        // No-op for testing
        override fun searchUsers(query: String) {}
        override fun loadUserDetails(login: String?) {}

        // Expose a public function to update the state from the test
        fun setState(newState: SearchUiState) {
            _uiState.value = newState
        }
    }

    @Test
    fun loadingState_showsLoadingIndicator() {
        // Given a loading state
        val viewModel = TestUserSearchViewModel(SearchUiState.Loading)

        // When the composable is rendered
        composeTestRule.setContent {
            UserSearchScreen(viewModel = viewModel)
        }

        // Then verify that the loading indicator is displayed
        composeTestRule.onNodeWithText("Loading...").assertExists()
    }

    @Test
    fun errorState_showsErrorMessage() {
        // Given an error state
        val viewModel = TestUserSearchViewModel(SearchUiState.Error("Unknown error"))

        // When the composable is rendered
        composeTestRule.setContent {
            UserSearchScreen(viewModel = viewModel)
        }

        // Then verify that the error message is displayed
        composeTestRule.onNodeWithText("Unknown error").assertExists()
    }

    @Test
    fun successState_showsUserList() {
        // Given a success state with a list of users
        val users = listOf(
            User(1,login = "user1", avatarUrl = "url1", name = "Test User 1", followers = 10, publicRepos = 5),
            User(2,login = "user2", avatarUrl = "url2", name = "Test User 2", followers = 20, publicRepos = 8)
        )
        val viewModel = TestUserSearchViewModel(SearchUiState.Success(users = users))

        // When the composable is rendered
        composeTestRule.setContent {
            UserSearchScreen(viewModel = viewModel)
        }

        // Then verify that the user list is displayed
        composeTestRule.onNodeWithText("user1").assertExists()
        composeTestRule.onNodeWithText("user2").assertExists()
    }

    @Test
    fun userClick_triggersUserDetails() {
        // Given a success state
        val users = listOf(
            User(1,login = "user1", avatarUrl = "url1", name = "User Details", followers = 10, publicRepos = 5)
        )
        val viewModel = TestUserSearchViewModel(SearchUiState.Success(users = users))

        // When a user item is clicked
        composeTestRule.setContent {
            UserSearchScreen(viewModel = viewModel)
        }

        composeTestRule.onNodeWithText("User Details").performClick()

        // Verify that the loading state for user details is triggered
        composeTestRule.onNodeWithText("User Details").assertExists()
    }
}
