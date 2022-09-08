package io.dina.githubuser.ui.userdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.dina.githubuser.ResultWrapper
import io.dina.githubuser.domain.models.User
import io.dina.githubuser.domain.usecases.FavoriteUserUseCase
import io.dina.githubuser.domain.usecases.GetUserDetailUseCase
import kotlinx.coroutines.launch

class UserDetailViewModel(
    private val getUserDetailUseCase: GetUserDetailUseCase,
    private val favoriteUserUseCase: FavoriteUserUseCase
) : ViewModel() {
    private val result = MutableLiveData<ResultWrapper<User>>()
    fun getResult(): LiveData<ResultWrapper<User>> = result

    fun getUser(userName: String) {
        viewModelScope.launch {
            result.value = ResultWrapper.Loading
            result.value = getUserDetailUseCase.execute(userName.trim())
        }
    }

    suspend fun isFavorited(userLogin: String): Boolean =
        favoriteUserUseCase.isFavorited(userLogin)

    fun insertFavoriteUser(user: User) {
        viewModelScope.launch {
            favoriteUserUseCase.insert(user)
        }
    }

    fun deleteFavoriteUser(user: User) {
        viewModelScope.launch {
            favoriteUserUseCase.remove(user)
        }
    }

}