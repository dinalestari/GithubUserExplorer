package io.dina.githubuser.di

import androidx.room.Room
import io.dina.githubuser.data.database.GithubUserDatabase
import org.koin.dsl.module

val LocalModule = module {
    single {
        Room.databaseBuilder(
            get(),
            GithubUserDatabase::class.java,
            "github-user-db"
        ).build()
    }

    single {
        get<GithubUserDatabase>().favoriteUserDao()
    }

}