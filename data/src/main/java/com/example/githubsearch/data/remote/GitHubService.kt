package com.example.githubinterview.data.remote

import com.example.githubinterview.data.model.GitHubUser
import com.example.githubsearch.data.model.SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/*
 * A Retrofit service interface for interacting with the GitHub API.
 * Defines the network endpoints and HTTP methods.
 */

interface GitHubService {

    @GET("search/users")
    suspend fun searchUsers(@Query("q") query: String?): Response<SearchResponse>


    @GET("users/{username}")
    suspend fun getUserDetails(@Path("username") username: String?): Response<GitHubUser>
}
