package com.pmesa48.marvelheroes.source.cache.common

import com.pmesa48.marvelheroes.repository.common.PaginatedParam

abstract class InMemoryPaginatedCache<M: Any, P: PaginatedParam>() : PaginatedCache<M, P> {

    protected val data: MutableList<M> = mutableListOf()
    private var totalResults: Int = 0
    private var currentCharacterId: String? = null
    override fun size() = data.size
    override fun all() = data.toList()
    override fun load(data: List<M>, total: Int) {
        totalResults = total
        this.data.addAll(data)
    }

    override fun needNextPage(param: P): Boolean {
        if(currentCharacterId != param.filterId) {
            data.clear()
            currentCharacterId = param.filterId
            return true
        }
        return lastVisibleThreshold(param) || currentCharacterId != param.filterId
    }

    private fun lastVisibleThreshold(param: P) =
        param.lastVisibleItem >= size() - threshold() && totalResults > size()

    abstract fun threshold(): Int
}