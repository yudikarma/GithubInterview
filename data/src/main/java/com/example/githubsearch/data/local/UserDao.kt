package com.example.githubinterview.data.local
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.githubinterview.data.model.GitHubUser


@Dao
interface UserDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<GitHubUser>)



    @Query("SELECT * FROM users")
    fun getAllUsers(): List<GitHubUser>

    @Query("SELECT * FROM users WHERE id = :id")
    fun getUserByLogin(id: String): GitHubUser
}
