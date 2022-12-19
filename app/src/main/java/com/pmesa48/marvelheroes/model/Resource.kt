package com.pmesa48.marvelheroes.model

sealed class Resource<out T> {
    data class Found<T>(val data : T): Resource<T>()
    data class Error<T>(val message: String? = null, val data : T? = null): Resource<T>()
}
