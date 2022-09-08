package io.dina.githubuser.ui.userdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.dina.githubuser.ResultWrapper
import io.dina.githubuser.domain.models.User
import io.dina.githubuser.domain.usecases.GetUserFollowersUseCase
import kotlinx.coroutines.launch

class FollowersViewModel(
    private val getUserFollowersUseCase: GetUserFollowersUseCase
) : ViewModel() {
    private val results = MutableLiveData<ResultWrapper<List<User>>>()
    fun getResult(): LiveData<ResultWrapper<List<User>>> = results

    fun getUserFollowers(userName: String) {
        viewModelScope.launch {
            results.value = ResultWrapper.Loading
            results.value = getUserFollowersUseCase.execute(userName.trim())
        }
    }
}