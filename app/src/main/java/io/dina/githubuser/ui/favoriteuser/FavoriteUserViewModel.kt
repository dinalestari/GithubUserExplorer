package io.dina.githubuser.ui.favoriteuser

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import io.dina.githubuser.domain.models.User
import io.dina.githubuser.domain.usecases.FavoriteUserUseCase

class FavoriteUserViewModel(private val favoriteUserUseCase: FavoriteUserUseCase) : ViewModel() {
    fun getAllFavoriteUser(): LiveData<List<User>> = favoriteUserUseCase.getAll().asLiveData()
}