package com.pmesa48.marvelheroes.repository.common

interface ListMapper<T : Any, U : Any> {
    fun map(data: List<T>) : List<U>

    companion object {
        const val QUALITY = "/standard_medium"
    }
}
