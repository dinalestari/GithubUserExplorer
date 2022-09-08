package io.dina.githubuser.domain.mapper

import io.dina.githubuser.data.models.UserDto
import io.dina.githubuser.domain.models.User

fun UserDto.mapToDomain(): User {
    return User(
        avatarUrl.orEmpty(),
        bio.orEmpty(),
        blog.orEmpty(),
        company.orEmpty(),
        email.orEmpty(),
        followers ?: 0,
        following ?: 0,
        id ?: 0,
        location.orEmpty(),
        login.orEmpty(),
        name.orEmpty(),
        publicGists ?: 0,
        publicRepos ?: 0
    )
}