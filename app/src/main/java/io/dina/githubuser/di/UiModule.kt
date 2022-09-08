package io.dina.githubuser.di

import io.dina.githubuser.ui.favoriteuser.FavoriteUserViewModel
import io.dina.githubuser.ui.searchuser.SearchViewModel
import io.dina.githubuser.ui.settings.SettingsViewModel
import io.dina.githubuser.ui.userdetail.FollowersViewModel
import io.dina.githubuser.ui.userdetail.FollowingViewModel
import io.dina.githubuser.ui.userdetail.UserDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val UiModule = module {
    viewModel {
        SearchViewModel(get(), get())
    }
    viewModel {
        UserDetailViewModel(get(), get())
    }
    viewModel {
        FollowingViewModel(get())
    }
    viewModel {
        FollowersViewModel(get())
    }
    viewModel {
        FavoriteUserViewModel(get())
    }
    viewModel {
        SettingsViewModel(get())
    }

}