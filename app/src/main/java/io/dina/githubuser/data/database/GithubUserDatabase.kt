package io.dina.githubuser.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [UserEntity::class], version = 1)
abstract class GithubUserDatabase : RoomDatabase() {
    abstract fun favoriteUserDao(): FavoriteUsersDao
}