package io.dina.githubuser.data.services

import io.dina.githubuser.data.models.SearchUserResponse
import io.dina.githubuser.data.models.UserDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubService {

    @GET("search/users")
    suspend fun searchUser(
        @Query("q") query: String
    ): Response<SearchUserResponse>

    @GET("users/{username}")
    suspend fun getUser(
        @Path("username") userName: String
    ): Response<UserDto>

    @GET("users/{username}/following")
    suspend fun getUserFollowing(
        @Path("username") userName: String
    ): Response<List<UserDto>>

    @GET("users/{username}/followers")
    suspend fun getUserFollowers(
        @Path("username") userName: String
    ): Response<List<UserDto>>

    @GET("users")
    suspend fun getUserList(
        @Query("per_page") perPage: Int
    ): Response<List<UserDto>>


}