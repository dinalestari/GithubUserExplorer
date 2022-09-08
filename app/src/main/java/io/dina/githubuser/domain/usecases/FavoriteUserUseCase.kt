package io.dina.githubuser.domain.usecases

import io.dina.githubuser.data.database.FavoriteUsersDao
import io.dina.githubuser.data.database.UserEntity
import io.dina.githubuser.domain.models.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map

class FavoriteUserUseCase(private val favoriteUsersDao: FavoriteUsersDao) {
    suspend fun insert(user: User) {
        try {
            favoriteUsersDao.insert(user.toEntity())
        } catch (e: Exception) {

        }
    }

    suspend fun remove(user: User) {
        try {
            favoriteUsersDao.remove(user.toEntity())
        } catch (e: Exception) {

        }
    }

    fun getAll(): Flow<List<User>> {
        return try {
            favoriteUsersDao.getAll().map {
                it.map { userEntity ->
                    userEntity.toUser()
                }
            }
        } catch (e: Exception) {
            emptyFlow()
        }
    }

    suspend fun isFavorited(userLogin: String): Boolean {
        return try {
            favoriteUsersDao.isFavorited(userLogin).map {
                it.toUser()
            }.isNotEmpty()
        } catch (e: Exception) {
            false
        }
    }

    private fun User.toEntity(): UserEntity {
        return UserEntity(
            login, avatarUrl, bio,
            blog, company, email, followers, following, id,
            location, name, publicGists, publicRepos
        )
    }

    private fun UserEntity.toUser(): User {
        return User(
            avatarUrl, bio,
            blog, company, email, followers, following, id,
            location, login, name, publicGists, publicRepos
        )
    }
}