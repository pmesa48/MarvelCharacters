package com.pmesa48.marvelheroes.source.cache.common

import com.pmesa48.marvelheroes.repository.common.Param

interface PaginatedCache<M: Any, P : Param> {
    fun size() : Int
    fun all() : List<M>
    fun limit() : Int
    fun load(data: List<M>, total: Int)
    fun needNextPage(param: P) : Boolean
}