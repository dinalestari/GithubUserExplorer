package io.dina.githubuser.utility

import io.dina.githubuser.ResultWrapper
import retrofit2.Response

fun <T : Any> Response<T>.toResultWrapper(): ResultWrapper<T> {
    val data = body()
    return if (isSuccessful && data != null) {
        ResultWrapper.Success(data)
    } else {
        ResultWrapper.Error("User Not Found")
    }
}