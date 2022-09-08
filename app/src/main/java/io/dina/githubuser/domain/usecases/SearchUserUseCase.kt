package io.dina.githubuser.domain.usecases

import io.dina.githubuser.ResultWrapper
import io.dina.githubuser.data.services.GithubService
import io.dina.githubuser.domain.mapper.mapToDomain
import io.dina.githubuser.domain.models.User
import io.dina.githubuser.mapData
import io.dina.githubuser.utility.toResultWrapper

class SearchUserUseCase(private val githubService: GithubService) {
    suspend fun execute(query: String): ResultWrapper<List<User>> {
        return try {
            githubService.searchUser(query).toResultWrapper()
                .mapData { response ->
                    response.items?.map {
                        it.mapToDomain()
                    }.orEmpty()
                }
        } catch (e: Exception) {
            ResultWrapper.Error(e.message.orEmpty().ifEmpty { "Unknown" })
        }
    }
}