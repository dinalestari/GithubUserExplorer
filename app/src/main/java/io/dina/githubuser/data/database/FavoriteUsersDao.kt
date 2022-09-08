package io.dina.githubuser.data.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteUsersDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: UserEntity)

    @Delete
    suspend fun remove(user: UserEntity)

    @Query("SELECT * FROM UserEntity order by login asc")
    fun getAll(): Flow<List<UserEntity>>

    @Query("SELECT * FROM UserEntity where login = :userLogin")
    suspend fun isFavorited(userLogin: String): List<UserEntity>
}