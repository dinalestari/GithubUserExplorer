package io.dina.githubuser.di

import io.dina.githubuser.domain.usecases.*
import org.koin.dsl.module

val DomainModule = module {
    single {
        SearchUserUseCase(get())
    }

    single {
        GetUserDetailUseCase(get())
    }

    single {
        GetUserFollowingUseCase(get())
    }

    single {
        GetUserFollowersUseCase(get())
    }

    single {
        GetUserListUseCase(get())
    }

    single {
        FavoriteUserUseCase(get())
    }
}