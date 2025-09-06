package com.example.githubinterview.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.githubinterview.data.model.GitHubUser


@Database(entities = [GitHubUser::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
