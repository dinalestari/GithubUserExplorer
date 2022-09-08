package io.dina.githubuser.data.models


import com.google.gson.annotations.SerializedName

data class SearchUserResponse(
    @SerializedName("incomplete_results")
    val incompleteResults: Boolean?,
    @SerializedName("items")
    val items: List<UserDto>?,
    @SerializedName("total_count")
    val totalCount: Int?
)