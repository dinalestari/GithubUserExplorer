package io.dina.githubuser.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val avatarUrl: String,
    val bio: String,
    val blog: String,
    val company: String,
    val email: String,
    val followers: Int,
    val following: Int,
    val id: Int,
    val location: String,
    val login: String,
    val name: String,
    val publicGists: Int,
    val publicRepos: Int
) : Parcelable