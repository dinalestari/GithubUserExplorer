package io.dina.githubuser.ui.searchuser

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.dina.githubuser.ResultWrapper
import io.dina.githubuser.domain.models.User
import io.dina.githubuser.domain.usecases.GetUserListUseCase
import io.dina.githubuser.domain.usecases.SearchUserUseCase
import kotlinx.coroutines.launch

class SearchViewModel(
    private val searchUserUseCase: SearchUserUseCase,
    private val getUserListUseCase: GetUserListUseCase
) : ViewModel() {
    private val results = MutableLiveData<ResultWrapper<List<User>>>()
    fun getResult(): LiveData<ResultWrapper<List<User>>> = results

    fun searchUser(query: String) {
        viewModelScope.launch {
            results.value = ResultWrapper.Loading
            results.value = searchUserUseCase.execute(query.trim())
        }
    }

    fun getUserList() {
        viewModelScope.launch {
            results.value = ResultWrapper.Loading
            results.value = getUserListUseCase.execute()
        }
    }
}