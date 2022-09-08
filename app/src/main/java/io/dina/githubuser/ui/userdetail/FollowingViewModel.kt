package io.dina.githubuser.ui.userdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.dina.githubuser.ResultWrapper
import io.dina.githubuser.domain.models.User
import io.dina.githubuser.domain.usecases.GetUserFollowingUseCase
import kotlinx.coroutines.launch

class FollowingViewModel(
    private val getUserFollowingUseCase: GetUserFollowingUseCase
) : ViewModel() {
    private val results = MutableLiveData<ResultWrapper<List<User>>>()
    fun getResult(): LiveData<ResultWrapper<List<User>>> = results

    fun getUserFollowing(userName: String) {
        viewModelScope.launch {
            results.value = ResultWrapper.Loading
            results.value = getUserFollowingUseCase.execute(userName.trim())
        }
    }
}