package io.dina.githubuser

sealed class ResultWrapper<out T> {
    data class Success<T : Any>(val data: T) : ResultWrapper<T>() {
        fun <R : Any> mapResult(mapper: (inData: T) -> R): ResultWrapper<R> {
            return Success(mapper(data))
        }
    }

    data class Error(val message: String) : ResultWrapper<Nothing>()

    object Loading : ResultWrapper<Nothing>()
}

fun <T, O : Any> ResultWrapper<T>.mapData(mapper: (inData: T) -> O): ResultWrapper<O> {
    return when (this) {
        is ResultWrapper.Success -> {
            this.mapResult {
                mapper(it)
            }
        }
        is ResultWrapper.Error -> this
        ResultWrapper.Loading -> ResultWrapper.Loading
    }
}