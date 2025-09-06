package com.example.githubinterview.data.model
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.githubinterview.domain.model.User
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass



@Entity(tableName = "users")
@JsonClass(generateAdapter = true)
data class GitHubUser(
    @PrimaryKey @field:Json(name = "id") val id: Int,
    @field:Json(name = "login") val login: String?,
    @field:Json(name = "avatar_url") val avatarUrl: String?,
    @field:Json(name = "name") val name: String?,
    @field:Json(name = "followers") val followers: Int?,
    @field:Json(name = "public_repos") val publicRepos: Int?
)
fun GitHubUser.toDomainUser(): User {
    return User(
        login = this.login,
        avatarUrl = this.avatarUrl,
        name = this.name,
        followers = this.followers,
        id = this.id,
        publicRepos = this.publicRepos
    )
}
