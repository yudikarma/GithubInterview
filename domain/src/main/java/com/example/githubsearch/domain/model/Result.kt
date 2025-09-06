package com.example.githubinterview.domain.model

/**
 * A sealed class to represent the state of an operation, such as a data fetch.
 * This provides a clean way to handle loading, success, and error states across the app.
 */
sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
    object Loading : Result<Nothing>()
}