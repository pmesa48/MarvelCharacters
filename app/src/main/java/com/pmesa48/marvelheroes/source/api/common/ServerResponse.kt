package com.pmesa48.marvelheroes.source.api.common

sealed class ServerResponse<out T : Any> {
    data class Success<out T : Any>(val data: T) : ServerResponse<T>()
    data class Error(val message: String, val messageBackupResource: Int? = null) : ServerResponse<Nothing>()
}