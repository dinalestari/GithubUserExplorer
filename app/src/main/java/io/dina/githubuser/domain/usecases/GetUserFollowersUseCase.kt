package io.dina.githubuser.domain.usecases

import io.dina.githubuser.ResultWrapper
import io.dina.githubuser.data.services.GithubService
import io.dina.githubuser.domain.mapper.mapToDomain
import io.dina.githubuser.domain.models.User
import io.dina.githubuser.mapData
import io.dina.githubuser.utility.toResultWrapper

class GetUserFollowersUseCase(private val githubService: GithubService) {
    suspend fun execute(userName: String): ResultWrapper<List<User>> {
        return try {
            githubService.getUserFollowers(userName).toResultWrapper()
                .mapData { response ->
                    response.map {
                        it.mapToDomain()
                    }
                }
        } catch (e: Exception) {
            ResultWrapper.Error(e.message.orEmpty().ifEmpty { "Unknown" })
        }
    }
}