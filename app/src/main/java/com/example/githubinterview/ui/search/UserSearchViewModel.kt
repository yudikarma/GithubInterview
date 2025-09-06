package com.example.githubsearch.presentation.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubinterview.domain.model.User
import com.example.githubinterview.domain.usecase.SearchUsersUseCase
import com.example.githubsearch.domain.usecase.GetUserDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.githubinterview.domain.model.Result

/**
 * ViewModel for the user search screen. It manages the UI state and interacts with the Use Case.
 * @HiltViewModel is used for dependency injection via Hilt.
 */
@HiltViewModel
open class UserSearchViewModel @Inject constructor(
    private val searchUsersUseCase: SearchUsersUseCase,
    private val getUserDetailsUseCase: GetUserDetailsUseCase
) : ViewModel() {

    // Represents the current UI state of the search screen
    private val _uiState = MutableStateFlow<SearchUiState>(SearchUiState.Idle)
    open val uiState: StateFlow<SearchUiState> = _uiState

    /**
     * Fetches users from the GitHub API based on the search query.
     * It uses a coroutine to handle the asynchronous network call.
     */
    open fun searchUsers(query: String) {
        viewModelScope.launch {
            _uiState.value = SearchUiState.Loading
            // Collect the Flow from the Use Case and update the UI state
            searchUsersUseCase(query).collect { result ->
                when (result) {
                    is Result.Success -> _uiState.value = SearchUiState.Success(result.data)
                    is Result.Error -> _uiState.value = SearchUiState.Error(result.exception.message ?: "Unknown error")
                    is Result.Loading -> _uiState.value = SearchUiState.Loading
                }
            }
        }
    }

    /**
     * Fetches details for a specific user.
     * It updates the UI state with the selected user's details.
     */
    open fun loadUserDetails(login: String?) {
        viewModelScope.launch {
            // First, get the current list of users to avoid losing them during the detail fetch.
            val currentUsers = (_uiState.value as? SearchUiState.Success)?.users ?: emptyList()

            // Emit a loading state to the UI.
            _uiState.value = SearchUiState.Loading

            // Fetch the user details.
            getUserDetailsUseCase(login).collect { result ->
                when (result) {
                    is Result.Success -> {
                        _uiState.value = SearchUiState.Success(
                            users = currentUsers,
                            selectedUser = result.data
                        )
                    }
                    is Result.Error -> {
                        _uiState.value = SearchUiState.Error(result.exception.message ?: "Unknown error")
                    }
                    is Result.Loading -> {
                        _uiState.value = SearchUiState.Loading
                    }
                }
            }
        }
    }
}

// Sealed class to represent the different states of the UI
sealed class SearchUiState {
    object Idle : SearchUiState()
    object Loading : SearchUiState()
    data class Success(
        val users: List<User>,
        val selectedUser: User? = null
    ) : SearchUiState()
    data class Error(val message: String) : SearchUiState()
}
